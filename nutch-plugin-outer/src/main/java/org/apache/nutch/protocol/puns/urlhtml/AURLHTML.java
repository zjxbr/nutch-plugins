package org.apache.nutch.protocol.puns.urlhtml;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.util.StringUtils;
import org.apache.http.Header;
import org.apache.http.NoHttpResponseException;
import org.apache.nutch.protocol.puns.util.CrawlURLUtil;
import org.apache.nutch.protocol.puns.util.EnumHttpRtnCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.puns.common.httpclient.bean.Page;
import com.puns.common.httpclient.fetcher.Fetcher;
import com.puns.crawling.dataExtractor.DataExtractorUtil;

public abstract class AURLHTML {
	private static final Logger LOG = LoggerFactory.getLogger(AURLHTML.class);

	// url,不能更改
	protected String url = "";
	// html ,抓取后更新
	protected String html = "";
	// 抓取返回值
	protected final List<ResponseBean> responseBeans = new ArrayList<>();
	// 下一步行动，写了就不能便
	protected final String nextSpringBeanName;

	public AURLHTML(String url, String nextSprintBeanName) {
		super();
		this.url = url;
		this.nextSpringBeanName = nextSprintBeanName;
	}

	public AURLHTML(String nextSprintBeanName) {
		super();
		this.nextSpringBeanName = nextSprintBeanName;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getNextSpringBeanName() {
		return nextSpringBeanName;
	}

	public void addRtnCode(ResponseBean responseBean) {
		responseBeans.add(responseBean);
	}

	public Iterable<ResponseBean> getRtnCodes() {
		return responseBeans;
	}

	public ResponseBean getLastVisitRtnCode() {
		if (responseBeans.size() > 0) {
			return responseBeans.get(responseBeans.size() - 1);
		}
		return ResponseBean.NOT_FETCH_YET;
	}

	/**
	 * @function 获取url
	 * @return
	 * @throws Exception
	 */
	public abstract String getUrl() throws Exception;

	/**
	 * @function 抓取,使用了子类的getURL和
	 * @param fetcher
	 * @return
	 * @throws Exception
	 */
	final public void fetch(Fetcher fetcher, Map<String, String> headers) {
		Page page = null;
		EnumHttpRtnCode enumHttpRtnCode;
		String proxy = "";
		// 如果子类实现IPostRequest接口，则进行POST处理
		try {
			if (this instanceof IPostRequest) {
				// POST 请求处理

				page = fetcher.requestPost(getUrl(), null, headers,
						((IPostRequest) this).providePostParams());
			} else {
				// GET 请求
				page = fetcher.requestGet(getUrl(), null, headers);
			}
			// 设置HTML 和RTN CODE
//			String extractData = DataExtractorUtil.extractHtml(
//					page.getContent(), false);
			String extractData = page.getContent();
			this.setHtml(extractData);

			// 获取rtn code
			enumHttpRtnCode = CrawlURLUtil.getHttpRtnCode(page.getStatusCode());
			Header header = page.getResponse().getFirstHeader("ProxyPuns");
			if (header != null) {
				proxy = header.getValue();
			}
			if (enumHttpRtnCode == EnumHttpRtnCode.OTHERS) {
				LOG.error("出现新return code:" + page.getStatusCode());
				System.err.println("出现新return code:" + page.getStatusCode());
			}

		} catch (NoHttpResponseException e) {
//			System.out.println(this.toString());
//			e.printStackTrace();
			System.out.println("page is " + page);
//			System.out.println(page.getResponse().getStatusLine());
			System.err.println("NoHttpResponseException 异常");
			// TODO
			enumHttpRtnCode = EnumHttpRtnCode.Exception;
		}catch (SocketTimeoutException e) {
			System.out.println("page is " + page);
			enumHttpRtnCode = EnumHttpRtnCode.Exception;
			System.err.println("SocketTimeoutException 异常");
		}
		catch (Exception e) {
			LOG.warn("异常:" + StringUtils.stringifyException(e));
			enumHttpRtnCode = EnumHttpRtnCode.Exception;
		}
		this.addRtnCode(new ResponseBean(enumHttpRtnCode, proxy));

		// this.addRtnCode(enumHttpRtnCode);
	}

//	@Override
//	public String toString() {
//		return "AURLHTML [url=" + url + ", html=" + html + ", responseBeans="
//				+ responseBeans + ", nextSprintBeanName=" + nextSpringBeanName
//				+ "]";
//	}

}
