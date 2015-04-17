package org.apache.nutch.protocol.puns.urlhtml;

import org.apache.nutch.protocol.puns.exception.ExceptionUtils;
import org.apache.nutch.protocol.puns.exception.ParamIllegalException;

/**
 * @author zjx
 * @function 不需要Resource 的基类
 */
public class AURLHTMLResourceLess extends AURLHTMLResource {

	private static final int expectArgLen = 2;

	/**
	 * 
	 * @param 小于Expect lens 就报错
	 * @return
	 * @throws ParamIllegalException
	 */
	public static AURLHTMLResourceLess getInstance(String[] cells)
			throws ParamIllegalException {
		
		ExceptionUtils.checkNumber(cells, expectArgLen);
		
		return new AURLHTMLResourceLess(cells[1], cells[0]);
	}

	public AURLHTMLResourceLess(String url, String nextSprintBeanName) {
		super(url, nextSprintBeanName);
	}

	public AURLHTMLResourceLess(String nextSprintBeanName) {
		super(nextSprintBeanName);
	}

	@Override
	public boolean getResource() {
		return true;
	}

	@Override
	public void releaseResource() {
		// DO NOTHING 因为不需要释放RESOURCE
	}

	@Override
	public String getUrl() {
		return url;
	}

}
