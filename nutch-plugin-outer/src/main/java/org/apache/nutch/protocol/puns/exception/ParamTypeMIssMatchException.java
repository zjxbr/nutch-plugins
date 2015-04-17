package org.apache.nutch.protocol.puns.exception;

import org.apache.commons.lang.StringUtils;
import org.apache.nutch.protocol.puns.util.CommonParams;

public class ParamTypeMIssMatchException extends ParamIllegalException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParamTypeMIssMatchException(String line, String msg) {
		super("FormatErr in line :" + line + System.lineSeparator() + msg);
	}

	public ParamTypeMIssMatchException(String[] line, String msg) {
		this(StringUtils.join(line, CommonParams.SPLITER), msg);
	}

}
