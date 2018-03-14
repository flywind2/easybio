/**
 * 
 */
package org.flywind2.easybio.crawler.ckb;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.io.Files;

import htsjdk.samtools.util.IOUtil;

/**
 * 
 * @author flywind2.su@gmail.com
 * @date 2018年3月14日
 * @version 1.0
 */
public class CKBDetail {

	private Document document;

	public CKBDetail(String geneUrl) {
		CloseableHttpClient httpCilent = HttpClients.createDefault();// Creates CloseableHttpClient instance with
																		// default configuration.
		HttpGet httpGet = new HttpGet(geneUrl);
		try {
			CloseableHttpResponse response = httpCilent.execute(httpGet);
			final String content = EntityUtils.toString(response.getEntity());
			// Files.write(content.getBytes("UTF-8"),new
			// File("G:\\superbio\\database\\ckb\\egfr-1.html"));//获得返回的结果);

			this.document = Jsoup.parse(content, "UTF-8");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				httpCilent.close();// 释放资源
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Map<String, String> fetchGeneDetail() {
		Map<String, String> map = new HashMap<String, String>();
		Element detailTable = document.select("table.table.table-striped.table-hover").first();
		Elements rows = detailTable.getElementsByTag("tr");
		for (Element e : rows) {
			Elements cols = e.getElementsByTag("td");
			map.put(cols.get(0).text(), cols.get(1).text());
		}
		return map;
	}

	/**
	 * Datatables_Table_0
	 * 
	 * @return
	 */
	public List<GeneVariant> fetchGeneVariants() {
		List<GeneVariant> geneVariantList = new ArrayList<>();
		Element geneVariantTable = document.selectFirst("table.gene_variant_tab_table");

		Elements rows = geneVariantTable.getElementsByTag("tr");
		for (Element e : rows) {
			Elements cols = e.getElementsByTag("td");
			if (!cols.isEmpty()) {
				final GeneVariant geneVariant = new GeneVariant();
				geneVariant.setVariant(cols.get(0).text());
				geneVariant.setImpact(cols.get(1).text());
				geneVariant.setProteinEffect(cols.get(2).text());
				geneVariant.setVariantDescription(cols.get(3).text());
				geneVariantList.add(geneVariant);
			}
		}

		return geneVariantList;
	}

	/**
	 * molecular-profile-tab-table
	 * 
	 * @return
	 */
	public List<MolecularProfile> fetchMolecularProfile() {
		List<MolecularProfile> molecularProfileList = new ArrayList<>();
		Element molecularProfileTable = document.selectFirst("table.molecular-profile-tab-table");

		Elements rows = molecularProfileTable.getElementsByTag("tr");
		for (Element e : rows) {
			Elements cols = e.getElementsByTag("td");
			if (!cols.isEmpty()) {
				final MolecularProfile molecularProfile = new MolecularProfile();
				molecularProfile.setMolecularProfile(cols.get(0).text());
				molecularProfile.setTreatmentApproaches(cols.get(1).text());
				molecularProfileList.add(molecularProfile);
			}
		}

		return molecularProfileList;
	}

	/**
	 * profile-response-table-without-treatment-approach
	 * 
	 * @return
	 */
	public List<GeneLevelEvidence> fetchGeneLevelEvidence(String geneSymbol) {
		List<GeneLevelEvidence> geneLevelEvidenceList = new ArrayList<>();
		Element geneLevelEvidenceTable = document
				.selectFirst("table.profile-response-table-without-treatment-approach");
		if (geneSymbol.equals("BRAF")) {
			try {
				Files.write(geneLevelEvidenceTable.html().getBytes("UTF-8"),
						new File("G:\\superbio\\database\\ckb\\braf.geneLevelEvidence.txt"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		Elements rows = geneLevelEvidenceTable.getElementsByTag("tr");
		for (Element e : rows) {
			Elements cols = e.getElementsByTag("td");
			if (!cols.isEmpty()) {
				final GeneLevelEvidence geneLevelEvidence = new GeneLevelEvidence();
				geneLevelEvidence.setMolecularProfile(cols.get(0).text());
				geneLevelEvidence.setIndicationOrTumorType(cols.get(1).text());
				geneLevelEvidence.setResponseType(cols.get(2).text());
				geneLevelEvidence.setTherapyName(cols.get(3).text());
				geneLevelEvidence.setApprovalStatus(cols.get(4).text());
				geneLevelEvidence.setEvidenceType(cols.get(5).text());
				geneLevelEvidence.setEfficacyEvidence(cols.get(6).text());
				geneLevelEvidence.setReferences(cols.get(7).text());
				geneLevelEvidenceList.add(geneLevelEvidence);
			}
		}

		return geneLevelEvidenceList;
	}

	public static void main(String args[]) {

		CloseableHttpClient httpCilent = HttpClients.createDefault();// Creates CloseableHttpClient instance with
																		// default configuration.
		HttpGet httpGet = new HttpGet("https://ckb.jax.org/gene/show?geneId=1956");
		try {
			CloseableHttpResponse response = httpCilent.execute(httpGet);
			final String content = EntityUtils.toString(response.getEntity());
			Files.write(content.getBytes("UTF-8"), new File("G:\\superbio\\database\\ckb\\egfr-1.html"));// 获得返回的结果);

			Document document = Jsoup.parse(content, "UTF-8");
			List<GeneLevelEvidence> geneLevelEvidenceList = new ArrayList<>();
			Element geneLevelEvidenceTable = document
					.selectFirst("table.profile-response-table-without-treatment-approach");

			Elements rows = geneLevelEvidenceTable.getElementsByTag("tr");
			System.out.println(
					rows.size() + "\t" + rows.size() / 8 + "\t" + geneLevelEvidenceTable.getElementsByTag("tr").size());
			for (Element e : rows) {
				Elements cols = e.getElementsByTag("td");
				if (!cols.isEmpty()) {
					final GeneLevelEvidence geneLevelEvidence = new GeneLevelEvidence();
					geneLevelEvidence.setMolecularProfile(cols.get(0).text());
					geneLevelEvidence.setIndicationOrTumorType(cols.get(1).text());
					geneLevelEvidence.setResponseType(cols.get(2).text());
					geneLevelEvidence.setTherapyName(cols.get(3).text());
					geneLevelEvidence.setApprovalStatus(cols.get(4).text());
					geneLevelEvidence.setEvidenceType(cols.get(5).text());
					geneLevelEvidence.setEfficacyEvidence(cols.get(6).text());
					geneLevelEvidence.setReferences(cols.get(7).text());
					geneLevelEvidenceList.add(geneLevelEvidence);
				}
			}

			BufferedWriter output = IOUtil
					.openFileForBufferedUtf8Writing(new File("G:\\superbio\\database\\ckb\\egfr-1.txt"));
			for (GeneLevelEvidence geneLevelEvidence : geneLevelEvidenceList) {
				output.write(geneLevelEvidence.getMolecularProfile());
				output.write("\t");
				output.write(geneLevelEvidence.getIndicationOrTumorType());
				output.write("\t");
				output.write(geneLevelEvidence.getResponseType());
				output.write("\t");
				output.write(geneLevelEvidence.getTherapyName());
				output.write("\t");
				output.write(geneLevelEvidence.getApprovalStatus());
				output.write("\t");
				output.write(geneLevelEvidence.getEvidenceType());
				output.write("\t");
				output.write(geneLevelEvidence.getEfficacyEvidence());
				output.write("\t");
				output.write(geneLevelEvidence.getReferences());
				output.newLine();
			}
			output.flush();
			output.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpCilent.close();// 释放资源
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
