package org.flywind2.easybio.crawler.ckb;

/**
 * 
 * @author flywind2.su@gmail.com
 * @date 2018年3月14日
 * @version 1.0
 */
public class MolecularProfile {
	private Long id;
	private String entrezId;
	private String geneSymbol;
	private String molecularProfile;
	private String treatmentApproaches;
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
	public String getGeneSymbol() {
		return geneSymbol;
	}
	public void setGeneSymbol(String geneSymbol) {
		this.geneSymbol = geneSymbol;
	}
	public String getMolecularProfile() {
		return molecularProfile;
	}
	public void setMolecularProfile(String molecularProfile) {
		this.molecularProfile = molecularProfile;
	}
	public String getTreatmentApproaches() {
		return treatmentApproaches;
	}
	public void setTreatmentApproaches(String treatmentApproaches) {
		this.treatmentApproaches = treatmentApproaches;
	}
	
	
}
