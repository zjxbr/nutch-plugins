package org.apache.nutch.protocol.puns.service.fail;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.util.StringUtils;
import org.apache.nutch.protocol.puns.urlhtml.AURLHTMLResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("FailProcessDefault")
public class FailProcessDefault implements FailProcess {

	private static final Logger LOG = LoggerFactory
			.getLogger(FailProcessDefault.class);

	@Override
	public Text getFailKeyOut(AURLHTMLResource t) {
		try {
			return new Text(t.getUrl());
		} catch (Exception e) {
			LOG.error(StringUtils.stringifyException(e));
			return new Text("");
		}
	}

	@Override
	public NullWritable getFailValOut(AURLHTMLResource t) {
		return NullWritable.get();
	}

	@Override
	public Text getFailLogKeyOut(AURLHTMLResource t)  {
		try {
			return new Text(t.getRtnCodes() + "|" + t.getUrl());
		} catch (Exception e) {
			LOG.error(StringUtils.stringifyException(e));
			return new Text("");
		}
	}

	@Override
	public NullWritable getFailLogValOut(AURLHTMLResource t) {
		return NullWritable.get();
	}
}
