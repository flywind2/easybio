/**
 * 
 */
package org.flywind2.easybio.gtf;

import java.io.File;

import htsjdk.samtools.util.IOUtil;
import htsjdk.samtools.util.IterableOnceIterator;

/**
 * 
 * @author flywind2.su@gmail.com
 * @date 2018年3月14日
 * @version 1.0
 */
public class GTFReader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File("gencode.v27lift37.annotation.gtf.gz");
		IterableOnceIterator<String> lines = IOUtil.readLines(file);
		while(lines.hasNext()) {
			//String line 
		}
	}

}
