package org.apache.nutch.protocol.puns.service;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.http.Header;
import org.apache.nutch.crawl.CrawlDatum;
import org.apache.nutch.metadata.Metadata;
import org.apache.nutch.protocol.Content;
import org.apache.nutch.protocol.ProtocolOutput;
import org.apache.nutch.protocol.ProtocolStatus;
import org.apache.nutch.protocol.puns.exception.ParamIllegalException;
import org.apache.nutch.protocol.puns.fetcher.PunsFetcher;
import org.apache.nutch.protocol.puns.header.GenerateHeader;
import org.apache.nutch.protocol.puns.service.fail.FailProcess;
import org.apache.nutch.protocol.puns.urlhtml.AURLHTMLResourceLessComm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.puns.common.httpclient.bean.Page;

@Component("ParseCommon")
public class ParseCommon extends AbsParseHTMLBase<AURLHTMLResourceLessComm> {
	private static final Logger LOG = LoggerFactory
			.getLogger(ParseCommon.class);

	public static final String BEAN_NAME;

	// BEAN的名字，依赖于
	static {
		Component component = ParseCommon.class.getAnnotation(Component.class);
		BEAN_NAME = component.value();
	}

	@Autowired
	@Qualifier("FailProcessDefault")
	private FailProcess failProcessDefault;

	@Autowired
	@Qualifier("GenerateHeaderDefault")
	private GenerateHeader generateHeaderDefault;

	@Autowired
	@Qualifier("PunsFetcher")
	private PunsFetcher punsFetcher;

	@Autowired
	private Configuration hadoopConf;

	@Override
	public Map<String, String> getHeader() {
		return generateHeaderDefault.generateHeader();
	}

	@Override
	public ProtocolOutput doBusiness(String url, CrawlDatum datum) {
		try {
			URL u = new URL(url);

			long startTime = System.currentTimeMillis();

			Page page = punsFetcher.requestGet(url, null,
					generateHeaderDefault.generateHeader());
			int elapsedTime = (int) (System.currentTimeMillis() - startTime);
			datum.getMetaData()
					.put(RESPONSE_TIME, new IntWritable(elapsedTime));
			int code = page.getStatusCode();
			byte[] content = page.getContent().getBytes();
			Header contentTypeHeader = page.getResponse().getFirstHeader(
					"Content-Type");
			String contentType = "";
			if (contentTypeHeader != null) {
				contentType = contentTypeHeader.getValue();
			}
			Content c = new Content(u.toString(), u.toString(),
					(content == null ? EMPTY_CONTENT : content), contentType,
					new Metadata(), hadoopConf);
			if (code == 200) { // got a good response
				return new ProtocolOutput(c); // return it

			} else {
				return new ProtocolOutput(c, new ProtocolStatus(
						ProtocolStatus.EXCEPTION, "Http code=" + code
								+ ", url=" + u));
			}
		} catch (IOException e) {
			LOG.error("Failed to get protocol output", e);
			return new ProtocolOutput(null, new ProtocolStatus(e));
		}
	}

	@Override
	public AURLHTMLResourceLessComm generateEntrance(String[] cells)
			throws ParamIllegalException {

		return AURLHTMLResourceLessComm.getInstance(cells);
	}

	@Override
	protected FailProcess getFailProcess() {
		return failProcessDefault;
	}

}
