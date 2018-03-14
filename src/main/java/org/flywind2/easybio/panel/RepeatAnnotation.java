/**
 * 
 */
package org.flywind2.easybio.panel;

import htsjdk.samtools.util.CloserUtil;
import htsjdk.samtools.util.CoordMath;
import htsjdk.samtools.util.IOUtil;
import htsjdk.tribble.AbstractFeatureReader;
import htsjdk.tribble.CloseableTribbleIterator;
import htsjdk.tribble.bed.BEDCodec;
import htsjdk.tribble.bed.BEDCodec.StartOffset;
import htsjdk.tribble.bed.BEDFeature;
import htsjdk.tribble.readers.LineIterator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.broadinstitute.barclay.argparser.Argument;
import org.broadinstitute.barclay.argparser.CommandLineProgramProperties;
import org.flywind2.easybio.programgroup.EasyPanel;

import picard.cmdline.CommandLineProgram;
import ucsc.RepeatMasker;

/**
 * @author flywind2.su@gmail.com
 * @date 2018年3月14日
 * @version 1.0
 */
@CommandLineProgramProperties(oneLineSummary = "Annotation BED with Repeat Region for filteration", programGroup = EasyPanel.class, summary = "Annotation BED with Repeat Region for filteration")
public class RepeatAnnotation extends CommandLineProgram {

    @Argument(shortName = "I", doc = "input BED file for annotation")
    public File INPUT;
    
    @Argument(shortName = "O", doc = "annotation output file")
    public File OUTPUT;

    /*
     * (non-Javadoc)
     * 
     * @see picard.cmdline.CommandLineProgram#doWork()
     */
    @Override
    protected int doWork() {
        // TODO Auto-generated method stub
        
        IOUtil.assertFileIsReadable(INPUT);
        
        IOUtil.assertFileIsWritable(OUTPUT);
        
        final BEDCodec bedCodec = new BEDCodec(StartOffset.ZERO);
        AbstractFeatureReader<BEDFeature, LineIterator> reader = AbstractFeatureReader
                .getFeatureReader(INPUT.getAbsolutePath(), bedCodec, false);
        CloseableTribbleIterator<BEDFeature> iterator = null;

        // 1.获得Factory
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA");
        
        
        // 3.获得事务，并开启uiwu
        //EntityTransaction transaction = manager.getTransaction();

        //transaction.begin();

        try(BufferedWriter output = IOUtil.openFileForBufferedUtf8Writing(OUTPUT)) {
            iterator = reader.iterator();

            while (iterator.hasNext()) {
                final BEDFeature feature = iterator.next();
                //Interval interval = new Interval(feature.getContig(), feature.getStart(), feature.getEnd());
                // 2.获取Manager
                EntityManager manager = factory.createEntityManager();
                Query query = manager.createNativeQuery("select * from rmsk t  where t.genoName = ?" + "and ("
                        + "(t.genoStart BETWEEN ? and ?)" + "or" + "(t.genoEnd BETWEEN ? and ?)" + ")",
                        RepeatMasker.class);

                query.setParameter(1, feature.getContig());
                query.setParameter(2, feature.getStart());
                query.setParameter(3, feature.getEnd());
                query.setParameter(4, feature.getStart());
                query.setParameter(5, feature.getEnd());

                List result = query.getResultList();
                
                if(result.isEmpty()) {
                    output.write(feature.getName()+"\t"+feature.getContig()+":"+ feature.getStart()+"-"+feature.getEnd() + "\t" + "-" +"\t"+"-"+"\t"+"-"+"\t"+0);
                    output.newLine();
                }else {
                    for (int i=0;i<result.size();i++) {
                        final RepeatMasker rmsk = (RepeatMasker) result.get(i);
                        int overlapSize = CoordMath.getOverlap(feature.getStart(), feature.getEnd(), rmsk.getGenoStart(), rmsk.getGenoEnd());
                        output.write(feature.getName()+"\t"+feature.getContig()+":"+ feature.getStart()+"-"+feature.getEnd() + "\t" + rmsk.getGenoStart() +"\t"+ rmsk.getGenoEnd()+"\t"+(rmsk.getRepName()+":"+rmsk.getRepClass())+"\t"+overlapSize);
                        output.newLine();
                    }
                }

                manager.close();
                output.flush();
            }
            
            output.flush();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            // transaction.commit();
            
            factory.close();
        }

        CloserUtil.close(iterator);
        return 0;
    }

    

}
