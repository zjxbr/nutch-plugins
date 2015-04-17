package org.apache.nutch.protocol.puns.fetcher;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.hadoop.conf.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.puns.common.httpclient.bean.Page;
import com.puns.common.httpclient.bean.ProxyInfo;
import com.puns.common.httpclient.bean.RequestInfo;
import com.puns.common.httpclient.config.HttpConfiguration;
import com.puns.common.httpclient.fetcher.Fetcher;

@Component("PunsFetcher")
public class PunsFetcher {

	@Autowired
	private Configuration hadoopConf;

	private Fetcher fetcher;

	@PostConstruct
	private void init() {
		ProxyInfo proxyInfo = new ProxyInfo();
		proxyInfo.setHost(hadoopConf.get("http.proxy.host"));
		proxyInfo.setPort(hadoopConf.getInt("http.proxy.port", -1));

		RequestInfo requestInfo = new RequestInfo();
		requestInfo.setUserAgent(hadoopConf.get("http.default.userAgent"));
		requestInfo.setSoTimeout(hadoopConf.getInt("http.default.soTimeout",
				60000));
		requestInfo.setConnTimeout(hadoopConf.getInt(
				"http.default.connTimeout", 3000));
		requestInfo
				.setRetryCnt(hadoopConf.getInt("http.default.retryCount", 1));
		requestInfo.setRetryInterval(hadoopConf.getInt(
				"http.default.retryFrequence", 3000));
		requestInfo.setConnidleTime(hadoopConf.getInt(
				"http.connection.pool.idleTime", 30));
		requestInfo.setConnPoolMaxTotal(hadoopConf.getInt(
				"http.connection.pool.maxTotal", 5000));
		requestInfo.setConnPoolMaxPerRoute(hadoopConf.getInt(
				"http.connection.pool.defaultMaxPerRoute", 1000));

		requestInfo.setRetryable(hadoopConf.getBoolean(
				"http.connection.pool.retryable", true));

		HttpConfiguration config = new HttpConfiguration();
		config.setProxyInfo(proxyInfo);
		config.setRequestInfo(requestInfo);
		
		System.err.println(proxyInfo.getHost() + "," + proxyInfo.getPort());
		fetcher = new Fetcher(config);
	}

	/**
	 * Get 请求
	 * 
	 * @param url
	 * @param proxyInfo
	 *            如果不需要设为null
	 * @param requestHeaders
	 * @return
	 * @throws IOException
	 */
	public Page requestGet(String url, ProxyInfo proxyInfo,
			Map<String, String> requestHeaders) throws IOException {
		return fetcher.requestGet(url, proxyInfo, requestHeaders);
	}

	/**
	 * Post 请求
	 * 
	 * @param url
	 * @param proxyInfo
	 *            如果不需要设为null
	 * @param requestHeaders
	 * @param postParamas
	 * @return
	 * @throws IOException
	 */
	public Page requestPost(String url, ProxyInfo proxyInfo,
			Map<String, String> requestHeaders, Map<String, String> postParamas)
			throws IOException {
		return fetcher.requestPost(url, proxyInfo, requestHeaders, postParamas);
	}

}
