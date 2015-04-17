package org.apache.nutch.protocol.puns.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegUtil {
	
	private RegUtil(){}
	
	public static String getStringReg(String reg, String txt, String returnValue) {
		Pattern pattern = Pattern.compile(reg, Pattern.DOTALL);
		Matcher matcher = pattern.matcher(txt);
		StringBuilder buffer = new StringBuilder();
		if (matcher.find()) {
			buffer.append(matcher.group(matcher.groupCount()));
			return buffer.toString();
		} else {

			return returnValue;

		}
	}
}
