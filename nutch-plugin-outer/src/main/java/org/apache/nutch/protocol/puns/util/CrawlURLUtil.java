package org.apache.nutch.protocol.puns.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrawlURLUtil {

	public static final Logger LOG = LoggerFactory
			.getLogger(CrawlURLUtil.class);

	private CrawlURLUtil() {
	}

	/**
	 * 
	 * @param code
	 *            http返回的code
	 * @return
	 */
	public static EnumHttpRtnCode getHttpRtnCode(int code) {

		if (code == 200) { // OK
			return EnumHttpRtnCode.OK;
		} else if (code == 988) { // 自定义错误代码，表示没有连接上代理服务器
			return EnumHttpRtnCode.FAILCONNPROXY;
		} else if (code == 987) { // 自定义错误代码，表示没有连接上代理服务器
			return EnumHttpRtnCode.MAXCONN;
		} else if (code == 410) { // page is gone
			return EnumHttpRtnCode.PAGEISGONE;
		} else if (code >= 300 && code < 400) {
			// 重定向
			switch (code) {
			case 300: // multiple choices, preferred value in Location
				return EnumHttpRtnCode.REDIRECT;
			case 301: // moved permanently
				return EnumHttpRtnCode.REDIRECT;
			case 302: // found (temporarily moved)
				return EnumHttpRtnCode.REDIRECT;
			case 303: // see other (redirect after POST)
				return EnumHttpRtnCode.REDIRECT;
			case 304: // not modified
				return EnumHttpRtnCode.REDIRECT;
			case 305: // use proxy (Location is URL of proxy)
				return EnumHttpRtnCode.REDIRECT;
			case 307: // temporary redirect
				return EnumHttpRtnCode.REDIRECT;
			default:
				return EnumHttpRtnCode.REDIRECT;
			}
		} else if (code == 400) { // bad request
			return EnumHttpRtnCode.BADREQUEST;
		} else if (code == 401) { // 需要认证
			return EnumHttpRtnCode.NOAUTH;
		} else if (code == 404) { // NOT FOUND
			return EnumHttpRtnCode.NOTFOUND;
		} else if (code == 500) { // 内部错误
			return EnumHttpRtnCode.INTERNALERROR;
		} else if (code == 501 || code == 503 || code == 403) { // 服务不可用，可能是访问过频繁
			return EnumHttpRtnCode.NOTAVALAIBLE;
		} else {
			return EnumHttpRtnCode.OTHERS;
		}
	}
}