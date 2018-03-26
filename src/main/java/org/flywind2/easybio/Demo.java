/**
 * 
 */
package org.flywind2.easybio;

import htsjdk.samtools.fastq.FastqReader;
import htsjdk.samtools.fastq.FastqRecord;
import htsjdk.samtools.util.SequenceUtil;
import htsjdk.samtools.util.StringUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.text.similarity.EditDistance;
import org.apache.commons.text.similarity.EditDistanceFrom;
import org.apache.commons.text.similarity.HammingDistance;

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

		String f1 = "data/R1.fq";
		String f2 = "data/R2.fq";
		FastqReader left = new FastqReader(new File(f1));
		
		FastqReader right = new FastqReader(new File(f2));
		Map<String,List<FastqRecord>> map = new HashMap<>();
		int size = 10;
		while(left.hasNext()) {
			final FastqRecord r1 = left.next();
			final FastqRecord r2 = right.next();
			
			String seq1 = r1.getReadString();
			
			String seq2 = SequenceUtil.reverseComplement(r2.getReadString());
			
			for(int i=0;i<seq2.length()-size;i++){
			    String t1;
			    if(i+size<seq2.length()){
			        t1 = seq2.substring(i,size);
			    }else{
			        t1 = seq2.substring(i,seq2.length());
			    }
			    compare(t1,seq1);
			}
			
		}
		
		left.close();
		right.close();
		
		
		String ssString = "TGTCCCACGTCTCTTTGCTTTCCAACTTTCTAATTGCCAACACTTATCTTCTGTGCTTTGGTGGACTCCAGACAGCAAGTGTCCCTGCTAGCCCACAGGCTCTCGGGGGGAACTAGCAGGGCACTGCAGAACCATGTCGCAGCTGAGAGTG";
		System.out.println(SequenceUtil.reverseComplement(ssString));
		//BwaMemAligner aligner = new BwaMemAligner(new BwaMemIndex(""));

	}

	/**
     * @param t1
     * @param seq1
     */
    private static void compare(String q, String target) {
        // TODO Auto-generated method stub
        EditDistance<Integer> editDistance = new HammingDistance();
        for(int i=0;i<target.length()-q.length();i++){
            final String tmp = target.substring(0, q.length());
            int distance = editDistance.apply(q, tmp);
            System.out.println(distance);
        }
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
