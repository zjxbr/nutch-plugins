package org.apache.nutch.protocol.puns.exception;

import org.apache.commons.lang.StringUtils;
import org.apache.nutch.protocol.puns.util.CommonParams;

public class ParamWrongNumberException extends ParamIllegalException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParamWrongNumberException(int expectNo, int realNo, String line) {
		super("Expect number is :" + expectNo + ", but realNo is " + realNo
				+ ", in line :" + line);
	}

	public ParamWrongNumberException(int expectNo, int realNo, String[] line) {
		this(expectNo, realNo, StringUtils.join(line,CommonParams.SPLITER));
	}
}
