package org.apache.nutch.protocol.puns;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.util.StringUtils;
import org.apache.nutch.crawl.CrawlDatum;
import org.apache.nutch.protocol.Protocol;
import org.apache.nutch.protocol.ProtocolOutput;
import org.apache.nutch.protocol.puns.service.AbsParseHTMLBase;
import org.apache.nutch.protocol.puns.urlhtml.AURLHTMLResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import crawlercommons.robots.BaseRobotRules;

public class PunsHttpGet implements Protocol {

	private final static Logger LOG = LoggerFactory
			.getLogger(PunsHttpGet.class);

	private static AnnotationConfigApplicationContext springContext;

	private static final String HADOOP_CONF_BEAN_NAME = "hadoopConf";

	private Configuration conf;


	private static final Text getBean = new Text("bean");

	private static final String CONF_USER_CONF_PATH = "user.conf.path";

	/**
	 * fake robot rule , allow all the urls.
	 */
	private BaseRobotRules robots = new BaseRobotRules() {
		@Override
		public boolean isAllowed(String arg0) {
			return true;
		}

		@Override
		public boolean isAllowNone() {
			return true;
		}

		@Override
		public boolean isAllowAll() {
			return true;
		}
	};

	@Override
	public Configuration getConf() {
		return conf;
	}

	@Override
	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	public static synchronized void initSpring(Configuration sysConf) throws IOException {
		if (springContext == null) {
			Configuration confForSpring = new Configuration();
			// 初始化配置文件
			String[] userPaths = sysConf.getStrings(CONF_USER_CONF_PATH);
			if (userPaths != null) {
				for (String userPath : userPaths) {
					try {
						if (userPath != null) {
							userPath = userPath.trim().replaceAll("\n|\r|\n\n",
									"");
							Path path = new Path(userPath);
							confForSpring.addResource(path.getFileSystem(confForSpring)
									.open(path));
							LOG.info("读取HDFS配置文件成功: " + userPath);
						}
					} catch (FileNotFoundException e) {
						// 本地执行，肯定不存在该文件
						LOG.error("文件不存在:" + userPath);
						LOG.error(StringUtils.stringifyException(e));
						throw e;
					}
				}
			} else {
				LOG.info("没有HDFS配置文件需要读取.");
			}

			// 初始化spring
			LOG.info("INIT Spring");
			springContext = new AnnotationConfigApplicationContext();
			springContext.getBeanFactory().registerSingleton(
					HADOOP_CONF_BEAN_NAME, confForSpring);
			springContext.scan("org.apache.nutch.protocol.puns");
			springContext.refresh();
			LOG.info("INIT Spring end");
		}
	}

	@Override
	public ProtocolOutput getProtocolOutput(Text url, CrawlDatum datum) {
		System.out.println("hi there : " + url.toString());

		// 如果spring是空则初始化spring
		if (springContext == null) {
			try {
				initSpring(conf);
			} catch (IOException e) {
				// TODO FATAL
				e.printStackTrace();
			}
		}

		String urlString = url.toString();

		Writable bean = datum.getMetaData().get(getBean);
		if (bean != null) {
			System.err.println(bean.toString());
			AbsParseHTMLBase<AURLHTMLResource> service = (AbsParseHTMLBase<AURLHTMLResource>) springContext
					.getBean(bean.toString());
			return service.doBusiness(urlString, datum);
		} else {
			System.err.println("bean is null");
			// TODO
			return null;
		}

	}

	@Override
	public BaseRobotRules getRobotRules(Text url, CrawlDatum datum) {
		return robots;
	}

}
