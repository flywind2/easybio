/**
 * 
 */
package org.flywind2.easybio.crawler.ckb;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import org.jsoup.helper.StringUtil;

import htsjdk.samtools.util.CloserUtil;
import htsjdk.samtools.util.IOUtil;

/**
 * 
 * @author flywind2.su@gmail.com
 * @date 2018年3月14日
 * @version 1.0
 */
public class CKBFormater {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		List<String> lines = Files.readAllLines(new File("civic-process.txt").toPath(),
				Charset.forName("utf-8"));
		BufferedWriter output = IOUtil
				.openFileForBufferedUtf8Writing(new File("civic-process.format.txt"));
		for (String line : lines) {
			final String[] array = line.split("\t", -1);

			array[10] = array[1];

			// 处理突变列
			final String[] mutation = array[2].split("\\s+", -1);
			if (mutation.length == 2) {
				array[11] = array[2].replaceAll(array[1], "").trim();
			} else {
				array[11] = array[2];
			}

			
			array[12] = array[3];
			array[13] = array[5];
			array[14] = array[6];
			
			String drugName = array[5];

			if (array[4].equals("resistant") || array[4].equals("no benefit") || array[4].equals("decreased response")
					|| array[4].equals("predicted – resistant")||array[4].contains("Resistance")) {
				//array[20] = array[5];
				array[22] = array[5];
				drugName = "-";
				array[23] = "不推荐用药";
			}
			
			if(array[4].contains("Sensitivity") || array[4].contains("sensitive")) {
				array[23] = "推荐用药";
			}

			// 处理临床等级
			/*
			 * Approval Status Preclinical Preclinical - Cell line xenograft Clinical Study
			 * Preclinical - Cell culture Phase I Phase Ib/II Phase II Preclinical - Pdx FDA
			 * approved Phase III Preclinical - Patient cell culture Preclinical - Pdx &
			 * cell culture Guideline Phase 0
			 */

			
				switch (array[6]) {
				case "Preclinical":
					//array[19] = drugName;
					array[20] = drugName;
					break;
				case "Preclinical - Cell line xenograft":
					array[19] = drugName;
					break;
				case "Clinical Study":
					array[18] = drugName;
					break;
				case "Preclinical - Cell culture":
					array[19] = drugName;
					break;
				case "Phase I":
					array[18] = drugName;
					break;
				case "Phase Ib/II":
					array[18] = drugName;
					break;
				case "Phase II":
					array[18] = drugName;
					break;
				case "Preclinical - Pdx":
					array[19] = drugName;
					break;
				case "FDA approved":
					//array[16] = drugName;
					array[15] = drugName;
					break;
				case "Phase III":
					array[18] = drugName;
					break;
				case "Preclinical - Patient cell culture":
					array[19] = drugName;
					break;
				case "Preclinical - Pdx & cell culture":
					array[19] = drugName;
					break;
				case "Guideline":
					array[16] = drugName;
					break;
				case "Phase 0":
					array[18] = drugName;
					break;
				default:
					break;
				}
				array[25] = array[9];
				
				
				
				
				output.write(StringUtil.join(array, "\t"));
				output.newLine();
			}
		

		// close bufferedwriter
		output.flush();
		CloserUtil.close(output);

	}

}
