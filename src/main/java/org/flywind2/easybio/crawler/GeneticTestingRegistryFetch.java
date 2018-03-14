/**
 * 
 */
package org.flywind2.easybio.crawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * @author flywind2.su@gmail.com
 * @date 2018年3月14日
 * @version 1.0
 */
public class GeneticTestingRegistryFetch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "https://www.ncbi.nlm.nih.gov/gtr/all/tests/?term=target_somatic[PROP]&page=%s";
		System.out.println(String.format(url, 1));
		Document doc;
		try {
			doc = Jsoup.parse(new URL(String.format(url, 1)), 0);
			Element tbody = doc.selectFirst("tbody");
			
			Elements rows = tbody.getElementsByTag("tr");
			for(Element row : rows) {
				Elements cols = row.getElementsByTag("td");
				List<String> geneList = new ArrayList<>();
				int i = 0;
				for(Element col : cols) {
					if(i==0) {
						Element detail = col.selectFirst("p>a");
						
						geneList.addAll(fetchGene("https://www.ncbi.nlm.nih.gov"+detail.attr("href")));
						System.exit(0);
					}
					//System.out.print(col.text()+"\t");
					i++;
				}
				//genes [test-item-long-list]
				
				//System.out.println();
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static  List<String> fetchGene(String url) throws MalformedURLException, IOException{
		System.out.println(url);
		List<String> geneList = new ArrayList<>();
		Document doc = Jsoup.parse(new URL(url), 0);
		System.out.println(doc.text());
		return geneList;
	}

}
