package org.apache.nutch.protocol.puns.dao.redis;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.google.protobuf.InvalidProtocolBufferException;

public abstract class RedisResource<T extends com.google.protobuf.GeneratedMessage> {

	private static final Logger LOG = LoggerFactory
			.getLogger(RedisResource.class);

	@Autowired
	private JedisPool jedisPool;

	/**
	 * @function 存入redis对象的名称
	 * @return
	 */
	protected abstract byte[] getRedisObjName();

	/**
	 * @function 初始化
	 * @throws IOException
	 */
	abstract protected void init() throws IOException;

	/**
	 * @function 获取剩余资源数
	 * @return
	 */
	public long getRemainCnt() {
		Jedis jedis = jedisPool.getResource();
		try {
			return jedis.llen(getRedisObjName());
		} finally {
			jedisPool.returnResource(jedis);
		}

	}

	/**
	 * 从从redis 获取resource
	 * 
	 * @return
	 * @throws InvalidProtocolBufferException
	 */
	public T getResource() throws InvalidProtocolBufferException {
		byte[] bs;
		Jedis jedis = jedisPool.getResource();
		try {
			bs = jedis.lpop(getRedisObjName());
		} finally {
			jedisPool.returnResource(jedis);
		}

		if (bs != null && bs.length > 0) {
			T t = getParser().parseFrom(bs);
			// 子类实现一些特殊处理，返回的是子类实现的protobuf，而不再是目前这个方法中的对象
			return getCanUseResource(t);
		} else {
			return null;
		}
	}

	/**
	 * @function 获取能用的resource
	 * @param t
	 * @return
	 */
	protected T getCanUseResource(T t) {
		// 休眠时间= 需要等待时间 - （ 当前时间-上次访问时间)
		// 如果休眠时间大于0,则进入休眠,会损失一些性能，但是会减少redis 负担
		long sleepTime = getWaitTime()
				- (System.currentTimeMillis() - getLastVisitTime(t));
		// 时间>0，设置为不可用，并且休息time时间后继续处理
		if (sleepTime > 0) {
			try {
				LOG.info("amazon category API访问过快，休息毫秒后再访问," + sleepTime);
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return t;
	}

	/**
	 * @function 获取上次访问时间
	 * @param t
	 * @return
	 */
	abstract protected long getLastVisitTime(T t);

	/**
	 * @function 获取需要等待时间
	 * @return
	 */
	abstract protected long getWaitTime();

	/**
	 * @function 获取set and get time
	 * @param t
	 * @return
	 */
	abstract protected T setAndGetVisitTime(T t);

	/**
	 * @function 获得 protobuf 的Parser，子类实现
	 * @return
	 */
	abstract protected com.google.protobuf.Parser<T> getParser();

	/**
	 * @function 归还资源,放回队列,需要更新访问时间
	 * 
	 * @param
	 */
	public void returnBean(T t) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.lpush(getRedisObjName(), setAndGetVisitTime(t).toByteArray());
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

}
