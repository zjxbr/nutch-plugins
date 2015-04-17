package org.apache.nutch.protocol.puns.util;

import java.io.IOException;

import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.CounterGroup;
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mapreduce.Job;

public class CounterUtil {
	
	private CounterUtil() {
		
	}

	/**
	 * 打印所有counter
	 * 
	 * @param job
	 * @throws IOException
	 */
	public static void printCounters(Job job) throws IOException {
		Counters counters = job.getCounters();

		for (CounterGroup group : counters) {
			System.out
					.println("==========================================================");
			System.out.println("* Counter Group: " + group.getDisplayName()
					+ " (" + group.getName() + ")");
			System.out.println("  number of counters in this group: "
					+ group.size());
			for (Counter counter : group) {
				System.out.println("  ++++ " + counter.getDisplayName() + ": "
						+ counter.getName() + ": " + counter.getValue());
			}
		}
	}

	/**
	 * 根据名称获得组
	 * 
	 * @param job
	 * @param groupName
	 * @return
	 * @throws IOException
	 */
	public static CounterGroup getGroup(Job job, String groupName)
			throws IOException {
		return job.getCounters().getGroup(groupName);
	}

	/**
	 * 判断某个组所有counter 加起来，是否等于预期
	 * 
	 * @param job
	 * @param groupName
	 * @param expectNumber
	 * @return
	 * @throws IOException
	 */
	public static boolean isGroupCounterEqual(Job job, String groupName,
			long expectNumber) throws IOException {

		return expectNumber == getCounterGroupTotalNum(job, groupName);
	}

	/**
	 * 获取某一个组的counter number
	 * 
	 * @param job
	 * @param groupName
	 * @return
	 * @throws IOException
	 */
	public static long getCounterGroupTotalNum(Job job, String groupName)
			throws IOException {
		CounterGroup group = getGroup(job, groupName);

		long sum = 0;
		for (Counter counter : group) {
			sum += counter.getValue();
		}
		return sum;
	}

	/**
	 * @param job
	 * @param groupName
	 * @return
	 * @throws IOException
	 */
	public static long getCounterNumber(Job job, String groupName,String counterName)
			throws IOException {
		CounterGroup group = getGroup(job, groupName);
		for (Counter counter : group) {

			if (counter.getName().equals(counterName)) {
				System.out.println(counter.getName());
				return counter.getValue();
			}
		}

		return 0l;
	}
}
