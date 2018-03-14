/**
 * 
 */
package org.flywind2.easybio.converter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import org.broadinstitute.barclay.argparser.Argument;
import org.broadinstitute.barclay.argparser.CommandLineProgramProperties;
import org.flywind2.easybio.programgroup.Converter;
import org.jsoup.helper.StringUtil;

import htsjdk.samtools.util.CloserUtil;
import htsjdk.samtools.util.IOUtil;
import htsjdk.samtools.util.IterableOnceIterator;
import picard.cmdline.CommandLineProgram;

/**
 * @author sufeng
 *
 */
@CommandLineProgramProperties(summary = "Converter blast -m 8 format to bed", oneLineSummary = "Converter blast -m 8 format to bed", programGroup = Converter.class)
public class BlastToBed extends CommandLineProgram {

	@Argument(shortName = "I", doc = "input blast -m 8 file")
	public File INPUT;

	@Argument(shortName = "O", doc = "output bed file")
	public File OUTPUT;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		args = new String[] {"I=G:\\superbio\\产品设计\\IDT\\210基因\\2017-11-21\\fusion\\fusion.blast.txt",
				"O=G:\\superbio\\产品设计\\IDT\\210基因\\2017-11-21\\fusion\\fusion.blast.bed"};
		new BlastToBed().instanceMain(args);
	}

	@Override
	protected int doWork() {
		// TODO Auto-generated method stub
		IOUtil.assertFileIsReadable(INPUT);
		IOUtil.assertFileIsWritable(OUTPUT);

		try (BufferedWriter writer = IOUtil.openFileForBufferedUtf8Writing(OUTPUT)) {
			IterableOnceIterator<String> lineIterator = IOUtil.readLines(INPUT);

			while (lineIterator.hasNext()) {
				final String line = lineIterator.next();
				final String[] array = line.split("\t", -1);
				final List<String> cols = new ArrayList<>();
				// chromosome
				cols.add(array[1]);
				// start and end
				final int s = Integer.parseInt(array[8]);
				final int e = Integer.parseInt(array[9]);

				if (s <= e) {
					cols.add((s-1)+"");
					cols.add(array[9]);
				} else {
					cols.add((e-1)+"");
					cols.add(array[8]);
				}

				// name
				cols.add(array[0]);

				// identify
				cols.add(array[2]);
				// mapped length
				cols.add(array[3]);

				writer.write(StringUtil.join(cols, "\t"));
				writer.newLine();

			}

			CloserUtil.close(lineIterator);
			CloserUtil.close(writer);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return 0;
	}

}
