/**
 * 
 */
package org.flywind2.easybio;

import java.io.File;

import com.google.common.primitives.Bytes;

import htsjdk.samtools.fastq.FastqConstants;
import htsjdk.samtools.fastq.FastqRecord;
import htsjdk.samtools.fastq.FastqWriter;
import htsjdk.samtools.fastq.FastqWriterFactory;
import htsjdk.samtools.reference.FastaSequenceFile;
import htsjdk.samtools.reference.ReferenceSequence;
import htsjdk.samtools.util.StringUtil;

/**
 * @author sufeng
 *
 */
public class Demo1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		final String s1 = "CCACTNTAAGTAAATATCATATTAAAATGAACAAAAAGCAAAATGAATCTTAAGAGATGCAAGGCTGAGCTGTGAGGATCAGGTAGAGACAGGAAGAGTTTACCACACATTGCAAGAGGTGGTAGAAATACTAATTAACTAGTTGGATGTC";
		
		final String s2 = "CTGATATGATGACATCCNNNNNNNNAATTNNNATTTCTACCACCTCTTGCAATGGGTGGTAAACTCTTCCTGTCTCTACCTGATCCTCACAGCTCAGCCTTTCATCTCTTAAGATTCATTTTGCTTTTTGTTCATTTTAATATGATATTTA";

		String s2_r = StringUtil.reverseString(s2);
		
		System.out.println(s1);
		System.out.println(s2_r);
		
		
		FastaSequenceFile fasta = new  FastaSequenceFile(
				new File("G:\\superbio\\项目\\210基因\\20180108\\MET.fa"), false);
		ReferenceSequence rfs;
		FastqWriter output = new FastqWriterFactory().newWriter(new File("G:\\superbio\\项目\\210基因\\20180108\\MET.fq"));
	    while((rfs = fasta.nextSequence())!=null) {
	    	
	    	byte[] baseQualities = new byte[rfs.getBases().length];
	    	for(int i=0;i<rfs.getBases().length;i++) {
	    		baseQualities[i] = '@';
	    	}
	    	FastqRecord rec = new FastqRecord(rfs.getName(), rfs.getBases(), FastqConstants.QUALITY_HEADER, baseQualities);
	    	output.write(rec);
	    }
	    
	    fasta.close();
	    
	   
		
		
		
		
	}

}
