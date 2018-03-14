/**
 * 
 */
package org.flywind2.easybio.crawler.ckb;

/**
 * @author sufeng
 *
 */
public class GeneLevelEvidence {
	private Long id;
	private String entrezId;
	private String geneSymbol;
	private String molecularProfile;
	private String indicationOrTumorType;
	private String responseType;
	private String therapyName;
	private String approvalStatus;
	private String evidenceType;
	private String EfficacyEvidence;
	private String references;
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
	public String getIndicationOrTumorType() {
		return indicationOrTumorType;
	}
	public void setIndicationOrTumorType(String indicationOrTumorType) {
		this.indicationOrTumorType = indicationOrTumorType;
	}
	public String getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	public String getTherapyName() {
		return therapyName;
	}
	public void setTherapyName(String therapyName) {
		this.therapyName = therapyName;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public String getEvidenceType() {
		return evidenceType;
	}
	public void setEvidenceType(String evidenceType) {
		this.evidenceType = evidenceType;
	}
	public String getEfficacyEvidence() {
		return EfficacyEvidence;
	}
	public void setEfficacyEvidence(String efficacyEvidence) {
		EfficacyEvidence = efficacyEvidence;
	}
	public String getReferences() {
		return references;
	}
	public void setReferences(String references) {
		this.references = references;
	}
	
}
