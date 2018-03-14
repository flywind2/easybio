/**
 * 
 */
package org.flywind2.easybio.crawler.mycancergenome;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author sufeng
 *
 */
public class McgPathway {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Document doc = Jsoup.connect("https://www.mycancergenome.org/content/molecular-medicine/pathways/").get();
			Elements elements = doc.select(".section-content > ul > li > a");
			//int i = 0;
			for (Element element : elements) {
				// System.out.println(element.text()+"\t"+element.attr("href"));
				//if (i == 0)
					getPathway(element.text(), element.attr("href"));
				//i++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取Pathway详细信息
	 * 
	 * @param Pathway名称
	 * @param Pathway
	 *            url
	 * @throws IOException
	 */
	private static void getPathway(String name, String url) throws IOException {
		// TODO Auto-generated method stub
		Document doc = Jsoup.connect(url).get();
		// Pathway基本说明
		Elements elements = doc.select(".section-content > p");
		List<String> descriptionList = new ArrayList<>();
		descriptionList.add(name);
		descriptionList.add(url);
		for (Element element : elements) {
			descriptionList.add(element.text().trim());
		}

		// System.out.println(StringUtil.join(descriptionList, "\t"));

		// 获取所有子元素
		Elements child = doc.selectFirst(".section-content").children();
		// 判断所有h3的位置
		List<Integer> indexList = checkTagIndex(child, "h3");
		for (int i = 0; i < indexList.size(); i++) {
			final int index = indexList.get(i);
			final String section = child.get(index).text();
			//System.out.println("===>"+section);
			final Element element = child.get(index+1);
			if(element.tagName().equals("ul")) {
				Elements liList = element.select("li");
				for(Element li:liList) {
					String entityUrl = ".";
					Element link = li.selectFirst("a");
					if(link!=null) {
						entityUrl = link.attr("href");
					}
					System.out.println(name+"\t"+section+"\t"+li.text().trim()+"\t"+entityUrl);
				}
			}
		}

		// Pathway中包含的基因
		// Elements genes = doc.select(".section-content > ul > li> a> em");
		// for(Element gene : genes) {
		//
		// System.out.println(name+"\t"+gene.text().trim());
		// }
	}

	/**
	 * 判断指定标签的位置
	 * 
	 * @param child
	 */
	private static List<Integer> checkTagIndex(Elements elements, String tagName) {
		// TODO Auto-generated method stub
		List<Integer> indexList = new ArrayList<>();
		for (int i = 0; i < elements.size(); i++) {
			final Element e = elements.get(i);
			if (e.tagName().trim().equals(tagName)) {
				indexList.add(i);
			}
		}
		return indexList;
	}

}
