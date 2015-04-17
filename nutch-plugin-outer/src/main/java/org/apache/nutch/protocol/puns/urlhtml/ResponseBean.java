package org.apache.nutch.protocol.puns.urlhtml;

import org.apache.nutch.protocol.puns.util.EnumHttpRtnCode;

public class ResponseBean {
	// http返回值
	private final EnumHttpRtnCode enumHttpRtnCode;
	// 代理
	private final String proxy;

	// 没有抓取
	public static final ResponseBean NOT_FETCH_YET;

	// mapreudce写错误
	public static final ResponseBean MR_WRITE_ERROR;
	static {
		NOT_FETCH_YET = new ResponseBean(EnumHttpRtnCode.NOFETCHYET, "");
		MR_WRITE_ERROR = new ResponseBean(EnumHttpRtnCode.MRWriteException, "");
	}

	public ResponseBean(EnumHttpRtnCode enumHttpRtnCode, String proxy) {
		super();
		this.enumHttpRtnCode = enumHttpRtnCode;
		this.proxy = proxy;
	}

	public EnumHttpRtnCode getEnumHttpRtnCode() {
		return enumHttpRtnCode;
	}

	public String getProxy() {
		return proxy;
	}

	@Override
	public String toString() {
		return "[" + enumHttpRtnCode + ", " + proxy + "]";
	}

}
