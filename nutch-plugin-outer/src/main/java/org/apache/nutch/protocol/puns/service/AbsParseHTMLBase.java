package org.apache.nutch.protocol.puns.service;

import java.io.IOException;
import java.util.Map;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.nutch.crawl.CrawlDatum;
import org.apache.nutch.protocol.ProtocolOutput;
import org.apache.nutch.protocol.puns.exception.ParamIllegalException;
import org.apache.nutch.protocol.puns.service.fail.FailProcess;
import org.apache.nutch.protocol.puns.urlhtml.AURLHTMLResource;

public abstract class AbsParseHTMLBase<T extends AURLHTMLResource> {
	public static final String SUCCESS_FOLDER = "success/success";
	public static final String FAIL_FOLDER = "fail/fail";
	public static final String FAIL_LOG_FOLDER = "faillog/faillog";

	protected static final Text RESPONSE_TIME = new Text("_rs_");
	protected static final byte[] EMPTY_CONTENT = new byte[0];

	/**
	 * 抓取
	 * 
	 * @param url
	 * @param crawlDatum
	 * @return
	 */
	public abstract ProtocolOutput doBusiness(String url, CrawlDatum crawlDatum);

	/**
	 * 初始化入口URL
	 * 
	 * @param cells
	 * @return
	 */
	abstract public AURLHTMLResource generateEntrance(String[] cells)
			throws ParamIllegalException;

	/**
	 * 获取请求HEADER
	 * 
	 * @return
	 */
	public abstract Map<String, String> getHeader();

	/**
	 * 失败处理
	 * 
	 * @step1 输出到失败文件夹，里面的内容，是可以再次重启的
	 * @step2 输出到失败的log文件夹，里面记录了每个url失败次数以及每次失败原因
	 * @param urlHTMLBase
	 * @param mos
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void doFail(T t, MultipleOutputs<Text, NullWritable> mos)
			throws IOException, InterruptedException {
		FailProcess failProcess = getFailProcess();
		// 写入到失败路径，该路径下文件可以重启下次任务
		mos.write(failProcess.getFailKeyOut(t), failProcess.getFailValOut(t),
				FAIL_FOLDER);

		// 写入到失败log目录，该路径下文件用来记录失败日志
		mos.write(failProcess.getFailLogKeyOut(t),
				failProcess.getFailValOut(t), FAIL_LOG_FOLDER);
	}

	/**
	 * @function 每个子类的失败处理
	 * @return
	 */
	protected abstract FailProcess getFailProcess();

}
