package org.apache.nutch.protocol.puns.urlhtml;

/**
 * @author zjx
 * @function 需要Resource 的基类
 */
public abstract class AURLHTMLResource extends AURLHTML implements IResourceble {

	public AURLHTMLResource(String url, String nextSprintBeanName) {
		super(url, nextSprintBeanName);
	}

	public AURLHTMLResource(String nextSprintBeanName) {
		super(nextSprintBeanName);
	}

}
