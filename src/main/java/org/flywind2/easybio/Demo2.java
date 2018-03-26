/**
 * 
 */
package org.flywind2.easybio;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.text.similarity.EditDistance;
import org.apache.commons.text.similarity.HammingDistance;

import htsjdk.samtools.SAMRecord;
import htsjdk.samtools.SAMRecordUtil;
import htsjdk.samtools.util.SequenceUtil;
import htsjdk.samtools.util.StringUtil;

/**
 * @author flywind2.su@gmail.com
 * @date 2018年3月20日
 * @version 1.0
 */
public class Demo2 {
    
    private static EditDistance<Integer> editDistance = new HammingDistance();
    
    public static void main(String...strings){
        int size = 4;
        String s1 = "ACGTCCTCC";
        String s2 = "CTCCTCACG";
        int index = 0;
        int mismatch = 0;
        
        List<String> k1 = new ArrayList<String>();
        for(int i=0;i<s1.length()-size+1;i++){
            String kmer = s1.substring(i, size + i);
            k1.add(kmer);
        }
        
        for(int j=0;j<s2.length()-size+1;j++){
            String kmer = s2.substring(j, size + j);
            for(int i=0;i<k1.size();i++){
                int d = editDistance.apply(kmer, k1.get(i));
                if(d<1){
                    System.out.println(j+"\t"+kmer+"\t"+k1.get(i));
                }
            }
        }
        
        String x = SequenceUtil.reverseComplement("CTGCGTCAGATTAAAACACTGAACTGACAATTAACAGCCCAATATCT");
        System.out.println(x);
        
       
    }
}
