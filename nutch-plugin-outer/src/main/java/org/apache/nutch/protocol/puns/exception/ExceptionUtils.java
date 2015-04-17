package org.apache.nutch.protocol.puns.exception;

public class ExceptionUtils {

	public static void checkNumber(String[] cells, int expect)
			throws ParamWrongNumberException {
		if (cells.length != expect) {
			throw new ParamWrongNumberException(expect, cells.length, cells);
		}
	}

	public static void checkNumberMoreThan(String[] cells, int expectMoreThan)
			throws ParamWrongNumberException {
		if (cells.length <= expectMoreThan) {
			throw new ParamWrongNumberException(expectMoreThan, cells.length,
					cells);
		}
	}

}
