/**
 * 
 */
package org.flywind2.easybio.crawler.genecards;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import htsjdk.samtools.util.CloserUtil;
import htsjdk.samtools.util.IOUtil;
import htsjdk.samtools.util.Log;

/**
 * 
 * @author flywind2.su@gmail.com
 * @date 2018年3月14日
 * @version 1.0
 */
public class GeneCardsAPI {
	private static final Log log = Log.getInstance(GeneCardsAPI.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {

			List<String> lines = Files.readAllLines(new File("gene-list.txt").toPath());
			BufferedWriter output = IOUtil
					.openFileForBufferedUtf8Writing(new File("gene-list-format.txt"));

			// 处理每一行（基因）
			int i = 0;
			for (String line : lines) {
				++i;
				final String[] array = line.split("\t", -1);
				// gene symbol is array[1]
				final String gene = array[1];
				log.info("process gene ", i, " ", gene);
				if (i % 10 == 0) {
					Thread.sleep(5000);
				}
				//// 设置 User-Agent
				Document document = Jsoup.connect("http://www.genecards.org/cgi-bin/carddisp.pl?gene=" + gene)
						.userAgent(
								"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
						.get();
				final StringBuilder buffer = new StringBuilder(Short.MAX_VALUE);
				Elements subsections = document.select("#summaries > div.gc-subsection");
				for (Element e : subsections) {
					Element header = e.selectFirst(".gc-subsection-header > h3");
					
					Element summary = e.selectFirst("p");
					
					//System.out.println((header!=null?header.text():"-")+"\t"+(summary!=null?summary.text():"-"));
					
					if (header != null && summary != null) {
						final String text = header.text().trim();
						
						if (text.startsWith("Entrez")) {
							buffer.append("Entrez Gene:").append(summary.text());
						} else if (text.startsWith("CIViC")) {
							if (buffer.length() > 0)
								buffer.append("|");
							buffer.append("CIViC:").append(summary.text());
						} else if (text.startsWith("GeneCards")) {
							if (buffer.length() > 0)
								buffer.append("|");
							buffer.append("GeneCards:").append(summary.text());
						} else {
							// do nothing
						}
					}
				}
				output.write(line + "\t" + buffer.toString());
				output.newLine();
				output.flush();
			}

			// 关闭文件句柄
			CloserUtil.close(output);

		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
