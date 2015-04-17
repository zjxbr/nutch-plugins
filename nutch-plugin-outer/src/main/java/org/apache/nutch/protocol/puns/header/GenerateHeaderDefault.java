package org.apache.nutch.protocol.puns.header;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("GenerateHeaderDefault")
public class GenerateHeaderDefault implements GenerateHeader {

	private static final String PROXY_CONNECTION = "Proxy-Connection";
	private static final String HOST = "Host";
	private static final String CACHE_CONTROL = "Cache-Control";
	private static final String ACCEPT_LANGUAGE = "Accept-Language";
	private static final String ACCEPT_ENCODING = "Accept-Encoding";
	private static final String ACCEPT = "Accept";
	
	
	private static final String VAL_PROXY_CONNECTION = "keep-alive";
//	private static final String VAL_PROXY_CONNECTION = "close";
	
	private static final String VAL_HOST = "www.amazon.co.jp";
	private static final String VAL_CACHE_CONTROL = "max-age=0";
	private static final String VAL_ACCEPT_LANG = "zh-CN,zh;q=0.8";
	private static final String VAL_ACCEPT_ENCODING = "gzip, deflate, sdch";
	private static final String VAL_ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8";

	@Override
	public Map<String, String> generateHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put(ACCEPT,
				VAL_ACCEPT);
		headers.put(ACCEPT_ENCODING, VAL_ACCEPT_ENCODING);
		headers.put(ACCEPT_LANGUAGE, VAL_ACCEPT_LANG);
		headers.put(CACHE_CONTROL, VAL_CACHE_CONTROL);
		headers.put(HOST, VAL_HOST);
		headers.put(PROXY_CONNECTION, VAL_PROXY_CONNECTION);
		return headers;
	}
}
