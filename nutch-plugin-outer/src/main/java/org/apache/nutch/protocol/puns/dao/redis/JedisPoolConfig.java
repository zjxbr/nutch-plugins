package org.apache.nutch.protocol.puns.dao.redis;

import javax.annotation.PostConstruct;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.hadoop.conf.Configuration;
import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zjx
 *
 */
@Component("jedisPoolConfig")
public class JedisPoolConfig extends GenericObjectPoolConfig {

	@Autowired
	private Configuration hadoopConf;

	// 最大连接数
	private int maxTotal;
	// 最大空闲数
	private int maxIdle;
	// 最小空闲数
	private int minIdle;
	// 主机名
	private String hostName;
	// 端口
	private int hostPort;
	// 超时时间
	private int timeOut;
	// 借用，归还时候，是否要验证，是
	private static final boolean testOnReturn = true;
	private static final boolean testWhileIdle = true;
	private static final boolean testOnBorrow = true;

	// 最大等待时间
	private int maxWaitMillis;

	@PostConstruct
	private void init() {
		maxTotal = hadoopConf.getInt("redis.max.total", 200);
		maxIdle = hadoopConf.getInt("redis.max.Idle", 50);
		minIdle = hadoopConf.getInt("redis.min.Idle", 10);
		hostName = hadoopConf.get("redis.host.name", "localhost");
		hostPort = hadoopConf.getInt("redis.host.port", 6379);
		timeOut = hadoopConf.getInt("redis.request.timeout", 10000);
		maxWaitMillis = hadoopConf.getInt("redis.request.maxwaittime", 1000);
		Log.info("JedisPoolConfig Finished : " + this.toString());
	}

	@Override
	public int getMaxTotal() {
		return maxTotal;
	}

	@Override
	public int getMaxIdle() {
		return maxIdle;
	}

	@Override
	public int getMinIdle() {
		return minIdle;
	}

	@Override
	public boolean getTestOnReturn() {
		return testOnReturn;
	}

	@Override
	public boolean getTestWhileIdle() {
		return testWhileIdle;
	}

	@Override
	public boolean getTestOnBorrow() {
		return testOnBorrow;
	}

	public String getHostName() {
		return hostName;
	}

	public int getHostPort() {
		return hostPort;
	}

	public int getTimeOut() {
		return timeOut;
	}

	@Override
	public long getMaxWaitMillis() {
		return maxWaitMillis;
	}

	@Override
	public String toString() {
		return "JedisPoolConfig [maxTotal=" + maxTotal + ", maxIdle=" + maxIdle
				+ ", minIdle=" + minIdle + ", hostName=" + hostName
				+ ", hostPort=" + hostPort + ", timeOut=" + timeOut
				+ ", maxWaitMillis=" + maxWaitMillis + "]";
	}

}
