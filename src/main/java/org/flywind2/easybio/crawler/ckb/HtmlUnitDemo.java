/**
 * 
 */
package org.flywind2.easybio.crawler.ckb;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.helper.StringUtil;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.google.common.io.Files;

import htsjdk.samtools.util.CloserUtil;
import htsjdk.samtools.util.IOUtil;

/**
 * 
 * @author flywind2.su@gmail.com
 * @date 2018年3月14日
 * @version 1.0
 */
public class HtmlUnitDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
			webClient.getOptions().setUseInsecureSSL(true);
			webClient.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
			webClient.getOptions().setCssEnabled(true); // 禁用css支持
			webClient.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常
			webClient.getOptions().setTimeout(100000); // 设置连接超时时间 ，这里是10S。如果为0，则无限期等待
			webClient.getOptions().setDoNotTrackEnabled(false);
			//启动ajax代理
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			// 当出现Http error时，程序不抛异常继续执行
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            // 防止js语法错误抛出异常
            webClient.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常
			
			
			final HtmlPage page = webClient.getPage("https://ckb.jax.org/gene/show?geneId=1956");
			Files.write(page.asXml().getBytes("UTF-8"), new File("G:\\superbio\\database\\ckb\\egfr.html"));
			
			
			//等待js渲染执行 waitime等待时间(ms)
			webClient.waitForBackgroundJavaScript(10000);

			DomElement table = page.querySelector("#DataTables_Table_2");
			
			List<HtmlTableRow> domNodeList = table.getByXPath(".//tr[@role='row']");
			System.out.println("total row is "+domNodeList.size());
			BufferedWriter output = IOUtil.openFileForBufferedUtf8Writing(new File("G:\\superbio\\database\\ckb\\egfr.geneLevelEvidence.txt"));
			
			for(HtmlTableRow row : domNodeList) {
//				final List<HtmlTableCell> cellList = row.getCells();
//				final List<String> colStringList = new ArrayList<>(cellList.size());
//				for(HtmlTableCell cell : cellList) {
//					colStringList.add(cell.getTextContent());
//				}
				output.write(row.asText());
				output.newLine();
			}
			

			// final String pageAsText = page.asText();
			// System.out.println(pageAsText);
			output.flush();
			CloserUtil.close(output);
			webClient.close();
		} catch (FailingHttpStatusCodeException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
