/**
 * 
 */
package org.flywind2.easybio.crawler.ckb;

/**
 * 
 * @author flywind2.su@gmail.com
 * @date 2018年3月14日
 * @version 1.0
 */
public class GeneVariant {
	private Long id;
	private String entrezId;
	private String geneSymbol;
	
	private String variant;
	private String impact;
	private String proteinEffect;
	private String variantDescription;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getEntrezId() {
		return entrezId;
	}
	public void setEntrezId(String entrezId) {
		this.entrezId = entrezId;
	}
	public String getVariant() {
		return variant;
	}
	public void setVariant(String variant) {
		this.variant = variant;
	}
	public String getImpact() {
		return impact;
	}
	public void setImpact(String impact) {
		this.impact = impact;
	}
	public String getProteinEffect() {
		return proteinEffect;
	}
	public void setProteinEffect(String proteinEffect) {
		this.proteinEffect = proteinEffect;
	}
	public String getVariantDescription() {
		return variantDescription;
	}
	public void setVariantDescription(String variantDescription) {
		this.variantDescription = variantDescription;
	}
	public String getGeneSymbol() {
		return geneSymbol;
	}
	public void setGeneSymbol(String geneSymbol) {
		this.geneSymbol = geneSymbol;
	}
	
}
