/**
 * 
 */
package org.flywind2.easybio;

import java.io.File;

import htsjdk.samtools.fastq.FastqReader;
import htsjdk.samtools.fastq.FastqRecord;
import htsjdk.samtools.util.SequenceUtil;

/**
 * @author sufeng
 *
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastqReader left = new FastqReader(new File("E:\\BaiduNetdiskDownload\\B1234\\1711\\1711_I11_S7_R1_001.fastq.gz"));
		FastqReader right = new FastqReader(new File("E:\\BaiduNetdiskDownload\\B1234\\1711\\1711_I11_S7_R3_001.fastq.gz"));
		//TPNB500103:79:HJ2Y3AFXX:1:11101:11235:1109
		
		while(left.hasNext()&&right.hasNext()) {
			final FastqRecord r1 = left.next();
			final FastqRecord r2 = right.next();
			if(r1.getReadName().contains("TPNB500103:79:HJ2Y3AFXX:1:11101:11235:1109 1:N:0:GGCTACAT")) {
				System.out.println(r1.getReadName()+"\t"+r1.getReadString());
				System.out.println(r2.getReadName()+"\t"+r2.getReadString());
				System.out.println(r2.getReadName()+"\t"+SequenceUtil.reverseComplement(r2.getReadString()));
				
				
			}
			
			
		}
	}

}
