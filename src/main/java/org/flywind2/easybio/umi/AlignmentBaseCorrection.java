/**
 * 
 */
package org.flywind2.easybio.umi;

import htsjdk.samtools.SAMFileWriter;
import htsjdk.samtools.SAMFileWriterFactory;
import htsjdk.samtools.SAMRecord;
import htsjdk.samtools.SamReader;
import htsjdk.samtools.SamReaderFactory;
import htsjdk.samtools.util.CloserUtil;
import htsjdk.samtools.util.CoordMath;
import htsjdk.samtools.util.IOUtil;
import htsjdk.samtools.util.Log;
import htsjdk.samtools.util.PeekableIterator;
import htsjdk.samtools.util.ProgressLogger;
import htsjdk.samtools.util.SequenceUtil;
import htsjdk.samtools.util.StringUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.broadinstitute.barclay.argparser.Argument;
import org.broadinstitute.barclay.argparser.CommandLineProgramProperties;
import org.flywind2.easybio.programgroup.EasyQC;

import picard.cmdline.CommandLineProgram;
import picard.util.QuerySortedReadPairIteratorUtil;
import picard.util.QuerySortedReadPairIteratorUtil.ReadPair;

/**
 * 
 * @author flywind2.su@gmail.com
 * @date 2018年3月26日
 * @version 1.0
 */
@CommandLineProgramProperties(summary = "Correct sequence errors with read pairs alignment overlap to genome reference.", oneLineSummary = "Correct sequence errors with read pairs alignment overlap to genome reference.", programGroup = EasyQC.class)
public class AlignmentBaseCorrection extends CommandLineProgram {

	private static final Log log = Log.getInstance(AlignmentBaseCorrection.class);

	@Argument(shortName = "I", doc = "alignment and query name sorted bam.")
	public File INPUT;

	@Argument(shortName = "O", doc = "output bam file")
	public File OUTPUT;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

	}

	private static ReadPair processAdjust(SAMRecord r1, SAMRecord r2, int m1, int m2) {
		// TODO Auto-generated method stub

		ReadPair readPair = new ReadPair();

		int p1 = r1.getReadPositionAtReferencePosition(m1);
		int p2 = r1.getReadPositionAtReferencePosition(m2);

		String r1Seq1 = r1.getReadString().substring(0, p1);
		String r1Seq2 = r1.getReadString().substring(p1, p2);
		byte[] q1 = getBaseQualities(r1, p1, p2);
		String r1Seq3 = r1.getReadString().substring(p2, r1.getReadString().length());
		// System.out.println(p1 + "\t" + p2 + "\t" + r1.getReadLength() + "\t" + r1Seq2
		// + "\t" + r1.getReadString());

		int p3 = r2.getReadPositionAtReferencePosition(m1);
		int p4 = r2.getReadPositionAtReferencePosition(m2);

		String r2Seq1 = r2.getReadString().substring(0, p3);
		String r2Seq2 = r2.getReadString().substring(p3, p4);
		byte[] q2 = getBaseQualities(r2, p3, p4);
		String r2Seq3 = r2.getReadString().substring(p4, r2.getReadString().length());
		// System.out.println(p3 + "\t" + p4 + "\t" + r2.getReadLength() + "\t" + r2Seq2
		// + "\t" + r2.getReadString());

		if (r1Seq2.equals(r2Seq2)) {
			readPair.read1 = r1;
			readPair.read2 = r2;
		} else {

			SAMRecord _r1 = r1.deepCopy();
			SAMRecord _r2 = r2.deepCopy();

			char[] c1 = r1Seq2.toCharArray();
			char[] c2 = r2Seq2.toCharArray();

			// System.out.println(c1.length+"\t"+c2.length);

			// combine common
			for (int i = 0; i < c1.length; i++) {
				if (c1[i] != c2[i]) {
					if (q1[i] >= q2[i]) {
						q2[i] = q1[i];
						c2[i] = c1[i];
					} else {
						q1[i] = q2[i];
						c1[i] = c2[i];
					}
				}
			}

			_r1.setReadString(r1Seq1 + String.valueOf(c1) + r1Seq3);
			_r2.setReadString(r2Seq1 + String.valueOf(c2) + r2Seq3);

			readPair.read1 = _r1;

			readPair.read2 = _r2;

		}

		return readPair;
	}

	private static byte[] getBaseQualities(SAMRecord r1, int p1, int p2) {
		// TODO Auto-generated method stub
		final byte[] b = r1.getBaseQualities();
		byte[] result = new byte[p2 - p1 + 1];
		for (int i = p1; i < p2; i++) {
			result[i - p1] = b[i];
		}
		return result;
	}

	public static int getStart(int s1, int s2) {
		if (s1 < s2) {
			return s2;
		} else {
			return s1;
		}
	}

	public static int getEnd(int e1, int e2) {
		if (e1 < e2) {
			return e1;
		} else {
			return e2;
		}
	}

	public static int hammingDistance(String s1, String s2) {
		String t1 = s1;
		String t2 = s2;
		if (s1.length() <= s2.length()) {
			t2 = s2.substring(0, s1.length());
		} else {
			t1 = t1.substring(0, s2.length());
		}
		return StringUtil.hammingDistance(t1, t2);
	}

	@Override
	protected int doWork() {
		// TODO Auto-generated method stub
		IOUtil.assertFileIsReadable(INPUT);
		IOUtil.assertFileIsWritable(OUTPUT);

		SamReader reader = SamReaderFactory.makeDefault().open(INPUT);

		BufferedOutputStream bos;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(OUTPUT));
			SAMFileWriter writer = new SAMFileWriterFactory().setCompressionLevel(8)
					.makeBAMWriter(reader.getFileHeader(), false, bos);

			PeekableIterator<SAMRecord> peekableIterator = new PeekableIterator<>(reader.iterator());

			ProgressLogger logger = new ProgressLogger(log);

			ReadPair readPair = null;
			while ((readPair = QuerySortedReadPairIteratorUtil.getNextReadPair(peekableIterator)) != null) {

				SAMRecord r1 = readPair.read1;

				SAMRecord r2 = readPair.read2;
				logger.record(r1);

				if (r2 == null) {
					writer.addAlignment(r1);
				} else if (r1 == null) {
					writer.addAlignment(r2);
				} else {

					int a = SequenceUtil.countDeletedBases(r1) + SequenceUtil.countInsertedBases(r1);
					int b = SequenceUtil.countDeletedBases(r2) + SequenceUtil.countInsertedBases(r2);

					if (a > 0 || b > 0) {
						writer.addAlignment(r1);
						writer.addAlignment(r2);
					} else {
						int s1 = r1.getAlignmentStart();
						int e1 = r1.getAlignmentEnd();

						int s2 = r2.getAlignmentStart();
						int e2 = r2.getAlignmentEnd();

						boolean isOverlap = CoordMath.overlaps(s1, e1, s2, e2);

						if (isOverlap && (!r1.getReadUnmappedFlag()) && (!r2.getReadUnmappedFlag())) {

							int m1 = getStart(s1, s2);
							int m2 = getEnd(e1, e2);

							ReadPair adjustReadPair = processAdjust(r1, r2, m1, m2);
							writer.addAlignment(adjustReadPair.read1);
							writer.addAlignment(adjustReadPair.read2);
							// boolean x = (r1.getReadLength() == adjustReadPair.read1.getReadLength());
							// boolean y = (r2.getReadLength() == adjustReadPair.read2.getReadLength());

						} else {
							writer.addAlignment(r1);
							writer.addAlignment(r2);
						}
					}

				}

				//
			}

			peekableIterator.close();
			CloserUtil.close(writer);
			bos.flush();
			bos.close();
			CloserUtil.close(reader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}

		return 0;
	}

}
