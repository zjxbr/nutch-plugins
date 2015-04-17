package org.apache.nutch.protocol.puns.urlhtml;

import java.util.Arrays;

import org.apache.hadoop.io.Text;
import org.apache.nutch.protocol.puns.exception.ExceptionUtils;
import org.apache.nutch.protocol.puns.exception.ParamIllegalException;
import org.apache.nutch.protocol.puns.util.CommonUtil;

/**
 * @author zjx
 * @function 不需要Resource 的基类
 */
public class AURLHTMLResourceLessComm extends AURLHTMLResourceLess {

	private static final int expectArgLen = 1;

	private String key;

	/**
	 * 
	 * @param 小于Expect
	 *            lens 就报错
	 * @return
	 * @throws ParamIllegalException
	 */
	public static AURLHTMLResourceLessComm getInstance(String[] cells)
			throws ParamIllegalException {

		// 验证长度大于1
		ExceptionUtils.checkNumberMoreThan(cells, expectArgLen);
		String valueKey;
		if (cells.length == 2) {
			valueKey = null;
		} else {
			String[] newCells = Arrays.copyOfRange(cells, 2, cells.length);
			valueKey = CommonUtil.constructOutputSTr(newCells);
		}
		return new AURLHTMLResourceLessComm(cells[1], cells[0], valueKey);
	}

	public AURLHTMLResourceLessComm(String url, String nextSprintBeanName,
			String valuekey) {
		super(url, nextSprintBeanName);
		this.key = valuekey;
	}

	/**
	 * @function 如果key是空，则只输出html，否则输出key和html
	 * @return
	 */
	public Text outputToMos() {
		return key == null ? new Text(html) : CommonUtil.constructOutput(key,
				html);
	}

	@Override
	public String getUrl() {
		return url;
	}

}
