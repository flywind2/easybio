/**
 * 
 */
package org.flywind2.easybio.gtf;

import java.io.File;

import htsjdk.samtools.util.IOUtil;
import htsjdk.samtools.util.IterableOnceIterator;

/**
 * @author sufeng
 *
 */
public class GTFReader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File("G:\\superbio\\database\\gencode\\gencode.v27lift37.annotation.gtf.gz");
		IterableOnceIterator<String> lines = IOUtil.readLines(file);
		while(lines.hasNext()) {
			//String line 
		}
	}

}
