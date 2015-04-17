package org.apache.nutch.protocol.puns.util;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.Text;

public class CommonUtil {
	
	private CommonUtil() {
	}
	
	/**
	 * @function gou zao shu chu , yi \001 fen ge
	 * @param cells
	 * @return
	 */
	public static Text constructOutput(String... cells) {
		String rtn = StringUtils.join(cells, CommonParams.SPLIT);
		return new Text(rtn);
	}
	
	public static String constructOutputSTr(String... cells) {
		String rtn = StringUtils.join(cells, CommonParams.SPLIT);
		return rtn;
	}
}
