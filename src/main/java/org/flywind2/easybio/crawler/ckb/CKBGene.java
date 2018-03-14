/**
 * 
 */
package org.flywind2.easybio.crawler.ckb;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.broadinstitute.barclay.argparser.Argument;
import org.broadinstitute.barclay.argparser.CommandLineProgramProperties;
import org.flywind2.easybio.programgroup.CKB;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import htsjdk.samtools.util.CloserUtil;
import htsjdk.samtools.util.IOUtil;
import htsjdk.samtools.util.Log;
import picard.cmdline.CommandLineProgram;

/**
 * @author sufeng
 *
 */
@CommandLineProgramProperties(summary = "JAX-Clinical Knowledgebase (CKB) Gene", oneLineSummary = "JAX-Clinical Knowledgebase (CKB) Gene", programGroup = CKB.class)
public class CKBGene extends CommandLineProgram {
	private final Log log = Log.getInstance(getClass());

	private static final String URL = "https://ckb.jax.org/gene/grid";

	@Argument(doc = "The ouput file prefix name.", shortName = "P")
	public String PREFIX;

	// @Argument(doc = "The Gene symbol to parse.",
	// shortName = "G")
	// public String GENE;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		args = new String[] {"P=G:\\superbio\\database\\ckb\\ckb"};
		CKBGene ckbGene = new CKBGene();
		ckbGene.instanceMain(args);
	}

	@Override
	protected int doWork() {
		// TODO Auto-generated method stub
		File OUTPUT = new File(PREFIX+"_summary.txt");
		File VARIANT = new File(PREFIX+"_variant.txt");
		File PROFILE = new File(PREFIX+"_molecularProfile.txt");
		File GENE_LEVEL_EVIDENCE = new File(PREFIX+"_geneLevelEvidence.txt");
		IOUtil.assertFileIsWritable(OUTPUT);
		IOUtil.assertFileIsWritable(VARIANT);
		IOUtil.assertFileIsWritable(PROFILE);
		IOUtil.assertFileIsWritable(GENE_LEVEL_EVIDENCE);
		try(BufferedWriter output = IOUtil.openFileForBufferedUtf8Writing(OUTPUT);
				BufferedWriter variant = IOUtil.openFileForBufferedUtf8Writing(VARIANT);
				BufferedWriter molecularProfile = IOUtil.openFileForBufferedUtf8Writing(PROFILE);
				BufferedWriter evidence = IOUtil.openFileForBufferedUtf8Writing(GENE_LEVEL_EVIDENCE)) {
			Document document = Jsoup.parse(new java.net.URL(URL), 0);
			Elements elements = document.select("a.btn.btn-default.btn-gene.btn-block");
			if (!elements.isEmpty()) {
				// process each gene
				for (Element e : elements) {
					final String gene_link = e.attr("abs:href");
					final CKBDetail geneDetail = new CKBDetail(gene_link);
					final String geneSymbol = e.html();
					log.info("process gene ",geneSymbol);
					//gene summary
					Map<String, String> geneDetailMap = geneDetail.fetchGeneDetail();
					output.write(geneDetailMap.getOrDefault("Entrez Id", ".") + "\t" + geneSymbol + "\t" + gene_link
							+ "\t" + geneDetailMap.getOrDefault("Synonyms", ".")
							+ "\t" + geneDetailMap.getOrDefault("Chromosome", ".")
							+ "\t" + geneDetailMap.getOrDefault("Map Location", ".")
							+ "\t" + geneDetailMap.getOrDefault("Gene Description", "."));
					output.newLine();
					
					//gene variant
					List<GeneVariant> geneVariantList = geneDetail.fetchGeneVariants();
					for(GeneVariant geneVariant:geneVariantList) {
						variant.write(geneDetailMap.getOrDefault("Entrez Id", ".")+"\t"+geneSymbol+"\t"+geneVariant.getVariant()+"\t"+geneVariant.getImpact()
						+ "\t" + geneVariant.getProteinEffect() 
						+ "\t" + geneVariant.getVariantDescription());
						variant.newLine();
					}
					
					
					//molecular profile
					List<MolecularProfile> molecularProfileList = geneDetail.fetchMolecularProfile();
					for(MolecularProfile mp:molecularProfileList) {
						molecularProfile.write(geneDetailMap.getOrDefault("Entrez Id", ".")
								+"\t"+geneSymbol
								+"\t"+mp.getMolecularProfile()
								+"\t"+mp.getTreatmentApproaches());
						molecularProfile.newLine();
					}
					
					//gene level evidence
					List<GeneLevelEvidence> geneLevelEvidenceList = geneDetail.fetchGeneLevelEvidence(geneSymbol);
					for(GeneLevelEvidence geneLevelEvidence : geneLevelEvidenceList) {
						evidence.write(geneDetailMap.getOrDefault("Entrez Id", ".")
								+"\t"+geneSymbol
								+"\t"+geneLevelEvidence.getMolecularProfile()
								+"\t"+geneLevelEvidence.getIndicationOrTumorType()
								+"\t"+geneLevelEvidence.getResponseType()
								+"\t"+geneLevelEvidence.getTherapyName()
								+"\t"+geneLevelEvidence.getApprovalStatus()
								+"\t"+geneLevelEvidence.getEvidenceType()
								+"\t"+geneLevelEvidence.getEfficacyEvidence()
								+"\t"+geneLevelEvidence.getReferences());
						evidence.newLine();
					}
				}

			}
			
			output.flush();
			variant.flush();
			molecularProfile.flush();
			evidence.flush();
			CloserUtil.close(output);
			CloserUtil.close(variant);
			CloserUtil.close(molecularProfile);
			CloserUtil.close(evidence);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

}
