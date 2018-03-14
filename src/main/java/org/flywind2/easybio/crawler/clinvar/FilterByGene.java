/**
 * 
 */
package org.flywind2.easybio.crawler.clinvar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.function.Consumer;

import htsjdk.samtools.util.CloserUtil;
import htsjdk.samtools.util.IOUtil;
import htsjdk.samtools.util.IterableOnceIterator;

/**
 * 
 * @author flywind2.su@gmail.com
 * @date 2018年3月14日
 * @version 1.0
 */
public class FilterByGene {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			List<String> genes = Files.readAllLines(new File("gene.txt").toPath());
			IterableOnceIterator<String> iterator = IOUtil.readLines(new File("variant_summary.format.txt"));
			
			BufferedWriter output = IOUtil.openFileForBufferedUtf8Writing(new File("gene-clinvar.txt"));
			
			iterator.forEach(new Consumer<String>() {

				@Override
				public void accept(String line) {
					// TODO Auto-generated method stub
					final String[] array = line.split("\t", -1);
					if(genes.contains(array[4])) {
						try {
							output.write(line);
							output.newLine();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}
				
			});
			
			CloserUtil.close(output);
			
//			while(iterator.hasNext()) {
//				final String line = iterator.next();
//				final String[] array = line.split("\t", -1);
//				
//				if(genes.contains(array[4])) {
//					System.out.println(line);
//				}
//				
//			}
			
			CloserUtil.close(iterator);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
