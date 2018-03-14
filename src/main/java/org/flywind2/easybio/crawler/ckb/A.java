package org.flywind2.easybio.crawler.ckb;

import java.io.File;
import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.common.io.Files;

/**
 * 
 * @author flywind2.su@gmail.com
 * @date 2018年3月14日
 * @version 1.0
 */
public class A {
	public static void main(String ...strings) {
		CloseableHttpClient httpCilent = HttpClients.createDefault();//Creates CloseableHttpClient instance with default configuration.
		HttpGet httpGet = new HttpGet("https://ckb.jax.org/gene/show?geneId=1956");
		try {
			CloseableHttpResponse  response = httpCilent.execute(httpGet);
			
			Files.write((EntityUtils.toString(response.getEntity()).getBytes("UTF-8")),new File("G:\\superbio\\database\\ckb\\egfr-1.html"));//获得返回的结果);
			
		} catch (IOException e) {
		    e.printStackTrace();
		}finally {
		    try {
		        httpCilent.close();//释放资源
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
	}
}
