/**
 * 
 */
package org.flywind2.easybio.panel;

import htsjdk.samtools.reference.ReferenceSequence;
import htsjdk.samtools.reference.ReferenceSequenceFile;
import htsjdk.samtools.reference.ReferenceSequenceFileFactory;
import htsjdk.samtools.util.IOUtil;
import htsjdk.samtools.util.StringUtil;
import htsjdk.tribble.AbstractFeatureReader;
import htsjdk.tribble.CloseableTribbleIterator;
import htsjdk.tribble.bed.BEDCodec;
import htsjdk.tribble.bed.BEDCodec.StartOffset;
import htsjdk.tribble.bed.BEDFeature;
import htsjdk.tribble.readers.LineIterator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sufeng
 *
 */
public class RepeatRegionExcluder {

    /**
     * @param args
     */
    public static void main(String[] args) {

        File file = new File("G:\\BIO-DATA\\data\\UCSC\\ucsc.hg19.fa");
        ReferenceSequenceFile rfs = ReferenceSequenceFileFactory
                .getReferenceSequenceFile(file);

        File bedFile = new File(
                "F:\\superbio\\产品设计\\IDT\\11基因重新设计\\20180124\\fusion-targets.bed");
        final BEDCodec bedCodec = new BEDCodec(StartOffset.ZERO);
        AbstractFeatureReader<BEDFeature, LineIterator> reader = AbstractFeatureReader
                .getFeatureReader(bedFile.getAbsolutePath(), bedCodec, false);
        
        File outputFile = new File("F:\\superbio\\产品设计\\IDT\\11基因重新设计\\20180124\\fusion-targets-rm.fasta");
        File outputBedFile = new File("F:\\superbio\\产品设计\\IDT\\11基因重新设计\\20180124\\fusion-targets-rm.bed");
        
        try(BufferedWriter output =  IOUtil.openFileForBufferedUtf8Writing(outputFile);BufferedWriter bedOutput =  IOUtil.openFileForBufferedUtf8Writing(outputBedFile)) {
            CloseableTribbleIterator<BEDFeature> iterator = reader.iterator();
            while (iterator.hasNext()) {
                final BEDFeature feat = iterator.next();

                ReferenceSequence sequence = rfs.getSubsequenceAt(
                        feat.getChr(), feat.getStart(), feat.getEnd());
                
                //System.out.println(StringUtil.bytesToString(sequence.getBases()));

                byte[] bases = sequence.getBases();
                int s = feat.getStart();
                List<Byte> buffer = new ArrayList<Byte>();
                for (int i = 0; i < bases.length; i++) {
                    final byte b = bases[i];
                    if (b == 'A' || b == 'C' || b == 'G' || b == 'T') {
                        buffer.add(b);
                    } else {
                        if (buffer.size() > 0) {
                            //+"|"+feat.getName().replaceAll("\\:", "_")
                            output.write(">" + sequence.getName() + "_"
                                    + s + "_" + (s + i));
                            output.newLine();
                            byte[] bb = new byte[buffer.size()];
                            for(int j=0;j<bb.length;j++){
                                bb[j] = buffer.get(j);
                            }
                            output.write(StringUtil.bytesToString(bb));
                            output.newLine();
                            
                            
                            bedOutput.write(sequence.getName()+"\t"+s+"\t"+(s+bb.length)+"\t"+(sequence.getName()+":"+s+"-"+(s+bb.length)));
                            bedOutput.newLine();
                            
                            buffer.clear();
                            
                            System.out.println("s="+s+"\t"+(s+bb.length));
                        }else{
                            buffer.clear();
                        }

                        s = feat.getStart() + i;
                        

                        // System.out.println(StringUtil.bytesToString(sequence.getBases()));
                    }
                }
                
                if(buffer.size()>0){
                    //+"|"+feat.getName().replaceAll("\\:", "_")
                    
                    byte[] bb = new byte[buffer.size()];
                    for(int j=0;j<bb.length;j++){
                        bb[j] = buffer.get(j);
                    }
                    output.write(">" + sequence.getName() + "_"
                            + s + "_" + (s+bb.length));
                    output.newLine();
                    output.write(StringUtil.bytesToString(bb));
                    output.newLine();
                    
                    
                    bedOutput.write(sequence.getName()+"\t"+s+"\t"+(s+bb.length)+"\t"+(sequence.getName()+":"+s+"-"+(s+bb.length)));
                    bedOutput.newLine();
                    
                   buffer.clear();
                }
                
            }
            
            output.flush();
            bedOutput.flush();
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        ReferenceSequence s = rfs.getSubsequenceAt("chr7",140488248,140488329);
        
        System.out.println(StringUtil.bytesToString(s.getBases()));

    }

}
