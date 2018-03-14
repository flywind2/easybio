/**
 * 
 */
package org.flywind2.easybio;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.broadinstitute.hellbender.utils.bwa.BwaMemAligner;
import org.broadinstitute.hellbender.utils.bwa.BwaMemIndex;

import htsjdk.samtools.fastq.FastqReader;
import htsjdk.samtools.fastq.FastqRecord;
import htsjdk.samtools.util.StringUtil;

/**
 * 
 * @author flywind2.su@gmail.com
 * @date 2018年3月14日
 * @version 1.0
 */
public class Demo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String f1 = "G:\\superbio\\validate\\1-MID_S4_R1_001_tag.fastq";
		//String f2 = "G:\\superbio\\validate\\1-MID_S4_R3_001_tag.fastq";
		FastqReader left = new FastqReader(new File(f1));
		
		//FastqReader right = new FastqReader(new File(f2));
		Map<String,List<FastqRecord>> map = new HashMap<>();
		
		while(left.hasNext()) {
			final FastqRecord r1 = left.next();
			final String[] readNameArray = new String[2];
			//System.out.println(r1.getReadName());
			StringUtil.split(r1.getReadName(), readNameArray, '|');
			
			
			if(map.containsKey(readNameArray[1])) {
				
				map.get(readNameArray[1]).add(r1);
				if(map.get(readNameArray[1]).size()>3) {
					make(map.get(readNameArray[1]));
				}
				
				
			}else {
				List<FastqRecord> list = new ArrayList<FastqRecord>();
				list.add(r1);
				map.put(readNameArray[1],list);
			}
			
		}
		
		left.close();

		
     //BwaMemAligner aligner = new BwaMemAligner(new BwaMemIndex(""));

	}

	private static void make(List<FastqRecord> list) {
		// TODO Auto-generated method stub
		for(int i=0;i<list.size();i++) {
			for(int j=i+1;j<list.size();j++) {
				FastqRecord r1 = list.get(i);
				FastqRecord r2 = list.get(j);
				int n = distance(r1.getReadString(),r2.getReadString());
				if(n<3) {
					System.out.println("==========="+n);
					System.out.println(r1.getReadString());
					System.out.println(r2.getReadString());
					
				}
				
			}
		}
	}

	/**
	 * 
	 * @param s1
	 * @param s2
	 * @return hanmming distance
	 */
	public static int distance(String s1, String s2) {
		final StringBuffer sb1 = new StringBuffer(s1);

		final StringBuffer sb2 = new StringBuffer(s2);
		if (sb1.length() > sb2.length()) {
			int a = sb1.length() - sb2.length();

			append(sb2, a);
		}

		if (sb1.length() < sb2.length()) {
			int a = sb2.length() - sb1.length();
			append(sb1, a);
		}

		return StringUtil.hammingDistance(sb1.toString(), sb2.toString());

	}

	/**
	 * 
	 * @param buffer
	 * @param length
	 */
	public static void append(StringBuffer buffer, int length) {
		for (int i = 0; i < length; i++) {
			buffer.append("-");
		}
	}

}
