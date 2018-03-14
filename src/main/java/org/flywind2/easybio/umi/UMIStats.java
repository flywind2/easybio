/**
 * 
 */
package org.flywind2.easybio.umi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.broadinstitute.barclay.argparser.Argument;
import org.broadinstitute.barclay.argparser.CommandLineProgramProperties;
import org.flywind2.easybio.programgroup.EasyQC;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.AtomicLongMap;

import htsjdk.samtools.fastq.FastqReader;
import htsjdk.samtools.fastq.FastqRecord;
import htsjdk.samtools.util.IOUtil;
import htsjdk.samtools.util.Interval;
import htsjdk.samtools.util.StringUtil;
import picard.cmdline.CommandLineProgram;

/**
 * 
 * @author flywind2.su@gmail.com
 * @date 2018年3月14日
 * @version 1.0
 */
@CommandLineProgramProperties(summary = "Collect UMI raw sequencing reads.", oneLineSummary = "Collect UMI raw sequencing reads.", programGroup = EasyQC.class)
public class UMIStats extends CommandLineProgram {

	@Argument(shortName = "I", doc = "input fastq file contain umi")
	public File INPUT;

	@Argument(shortName = "O", doc = "output collector file")
	public File OUTPUT;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// args = new String[] {
		// "I=G:\\superbio\\项目\\Swift-MID测试\\56896_ATCACG_S1_L1+2_R2ieI2.fq.gz",
		// "O=G:\\superbio\\项目\\Swift-MID测试\\umi.stats.txt" };
		// new UMIStats().instanceMain(args);
		File file = new File("G:\\superbio\\项目\\Swift-MID测试\\umi.stats.txt");
		System.out.println(file.getParent());
		System.out.println(file.getName());
	}

	@Override
	protected int doWork() {
		// TODO Auto-generated method stub
		IOUtil.assertFileIsReadable(INPUT);
		IOUtil.assertFileIsWritable(OUTPUT);
		AtomicLongMap<String> countMap = AtomicLongMap.create();
		FastqReader reader = new FastqReader(INPUT);
		for (FastqRecord rec : reader) {
			countMap.getAndIncrement(rec.getReadString());
		}

		// write umi frequency into disk
		Map<String, Long> frequencyMap = countMap.asMap();
		List<String> nList = Lists.newArrayListWithCapacity(Short.MAX_VALUE);
		String dir = OUTPUT.getParent() == null ? "./" : OUTPUT.getParent();
		String prefix = IOUtil.basename(OUTPUT);
		try (BufferedWriter output = IOUtil.openFileForBufferedUtf8Writing(OUTPUT);
				BufferedWriter summary = IOUtil
						.openFileForBufferedUtf8Writing(new File(dir + "/" + prefix + "_summary.txt"))) {
			frequencyMap.forEach((key, value) -> {
				try {
					output.write(key + "\t" + value);
					output.newLine();
					if (key.contains("N")) {
						nList.add(key);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});

			output.flush();

			final String[] columns = { "sample", "umi_size", "reads_size", "avg_reads", "n_size" };
			summary.write(StringUtil.join("\t", Arrays.asList(columns)));
			summary.newLine();
			summary.write(prefix + "\t" + countMap.size() + "\t" + countMap.sum() + "\t"
					+ Math.round((double) countMap.sum() / countMap.size()) + "\t" + nList.size());
			summary.newLine();
			summary.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

		reader.close();

		Interval intv = new Interval("chr1", 100, 1000);
		
		
		return 0;
	}
	
	/**
     * @return Version stored in the manifest of the jarfile.
     */
    public String getVersion() {
        return "easybio-1.0";
    }

}
