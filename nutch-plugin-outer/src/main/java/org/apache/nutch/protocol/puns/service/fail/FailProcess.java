package org.apache.nutch.protocol.puns.service.fail;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.nutch.protocol.puns.urlhtml.AURLHTMLResource;

public interface FailProcess {

	public Text getFailKeyOut(AURLHTMLResource t) ;

	public NullWritable getFailValOut(AURLHTMLResource t);

	public Text getFailLogKeyOut(AURLHTMLResource t) ;

	public NullWritable getFailLogValOut(AURLHTMLResource t);
}
