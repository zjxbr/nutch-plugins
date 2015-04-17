package org.apache.nutch.protocol.puns.urlhtml;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @author zjx
 * @function 需要资源接口 实现该接口的类，是需要在执行时候获取资源，执行之后释放资源的类。
 *           目前在urlGenator中会有判断，抓取前获取resource，抓取之后release
 */
public interface IResourceble {
	/**
	 * 获取resource
	 * 
	 * @return 成功返回true，失败返回false
	 */
	public boolean getResource() throws InvalidProtocolBufferException;

	/**
	 * 释放resource
	 */
	public void releaseResource();
}
