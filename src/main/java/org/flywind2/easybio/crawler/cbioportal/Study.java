/**
 * 
 */
package org.flywind2.easybio.crawler.cbioportal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * @author sufeng
 *
 */
@Entity
@Table(name="cbioportal_study")
public class Study {
	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private String shortName;
	
	@Column(length=2000)
	private String description;
	
	@Column
	private boolean publicStudy;
	
	@Column
	private String pmid;
	
	@Column
	private String citation;
	
	@Column
	private String groups;
	
	@Column
	private String status;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date importDate;
	
	@Column
	private int allSampleCount;
	
	@Column
	private int sequencedSampleCount;
	
	@Column
	private int cnaSampleCount;
	
	@Column
	private int mrnaRnaSeqSampleCount;
	
	@Column
	private int mrnaRnaSeqV2SampleCount;
	
	@Column
	private int mrnaMicroarraySampleCount;
	
	@Column
	private int miRnaSampleCount;
	
	@Column
	private int methylationHm27SampleCount;
	
	@Column
	private int rppaSampleCount;
	
	@Column
	private int completeSampleCount;
	
	@Column
	private String studyId;
	
	@Column
	private String cancerTypeId;
	
	@ManyToOne
	@JoinColumn(name="cancer_type_id")
	private CancerType cancerType;
	
	@Column
	private String downloadUrl;
	
	@Version
	private Long version;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isPublicStudy() {
		return publicStudy;
	}
	public void setPublicStudy(boolean publicStudy) {
		this.publicStudy = publicStudy;
	}
	public String getPmid() {
		return pmid;
	}
	public void setPmid(String pmid) {
		this.pmid = pmid;
	}
	public String getCitation() {
		return citation;
	}
	public void setCitation(String citation) {
		this.citation = citation;
	}
	public String getGroups() {
		return groups;
	}
	public void setGroups(String groups) {
		this.groups = groups;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getImportDate() {
		return importDate;
	}
	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}
	public int getAllSampleCount() {
		return allSampleCount;
	}
	public void setAllSampleCount(int allSampleCount) {
		this.allSampleCount = allSampleCount;
	}
	public int getSequencedSampleCount() {
		return sequencedSampleCount;
	}
	public void setSequencedSampleCount(int sequencedSampleCount) {
		this.sequencedSampleCount = sequencedSampleCount;
	}
	public int getCnaSampleCount() {
		return cnaSampleCount;
	}
	public void setCnaSampleCount(int cnaSampleCount) {
		this.cnaSampleCount = cnaSampleCount;
	}
	public int getMrnaRnaSeqSampleCount() {
		return mrnaRnaSeqSampleCount;
	}
	public void setMrnaRnaSeqSampleCount(int mrnaRnaSeqSampleCount) {
		this.mrnaRnaSeqSampleCount = mrnaRnaSeqSampleCount;
	}
	public int getMrnaRnaSeqV2SampleCount() {
		return mrnaRnaSeqV2SampleCount;
	}
	public void setMrnaRnaSeqV2SampleCount(int mrnaRnaSeqV2SampleCount) {
		this.mrnaRnaSeqV2SampleCount = mrnaRnaSeqV2SampleCount;
	}
	public int getMrnaMicroarraySampleCount() {
		return mrnaMicroarraySampleCount;
	}
	public void setMrnaMicroarraySampleCount(int mrnaMicroarraySampleCount) {
		this.mrnaMicroarraySampleCount = mrnaMicroarraySampleCount;
	}
	public int getMiRnaSampleCount() {
		return miRnaSampleCount;
	}
	public void setMiRnaSampleCount(int miRnaSampleCount) {
		this.miRnaSampleCount = miRnaSampleCount;
	}
	public int getMethylationHm27SampleCount() {
		return methylationHm27SampleCount;
	}
	public void setMethylationHm27SampleCount(int methylationHm27SampleCount) {
		this.methylationHm27SampleCount = methylationHm27SampleCount;
	}
	public int getRppaSampleCount() {
		return rppaSampleCount;
	}
	public void setRppaSampleCount(int rppaSampleCount) {
		this.rppaSampleCount = rppaSampleCount;
	}
	public int getCompleteSampleCount() {
		return completeSampleCount;
	}
	public void setCompleteSampleCount(int completeSampleCount) {
		this.completeSampleCount = completeSampleCount;
	}
	public String getStudyId() {
		return studyId;
	}
	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}
	public String getCancerTypeId() {
		return cancerTypeId;
	}
	public void setCancerTypeId(String cancerTypeId) {
		this.cancerTypeId = cancerTypeId;
	}
	public CancerType getCancerType() {
		return cancerType;
	}
	public void setCancerType(CancerType cancerType) {
		this.cancerType = cancerType;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	@Override
	public String toString() {
		return "Study [name=" + name + ", shortName=" + shortName + ", description=" + description + ", publicStudy="
				+ publicStudy + ", pmid=" + pmid + ", citation=" + citation + ", groups=" + groups + ", status="
				+ status + ", importDate=" + importDate + ", allSampleCount=" + allSampleCount
				+ ", sequencedSampleCount=" + sequencedSampleCount + ", cnaSampleCount=" + cnaSampleCount
				+ ", mrnaRnaSeqSampleCount=" + mrnaRnaSeqSampleCount + ", mrnaRnaSeqV2SampleCount="
				+ mrnaRnaSeqV2SampleCount + ", mrnaMicroarraySampleCount=" + mrnaMicroarraySampleCount
				+ ", miRnaSampleCount=" + miRnaSampleCount + ", methylationHm27SampleCount="
				+ methylationHm27SampleCount + ", rppaSampleCount=" + rppaSampleCount + ", completeSampleCount="
				+ completeSampleCount + ", studyId=" + studyId + ", cancerTypeId=" + cancerTypeId + ", cancerType="
				+ cancerType + ", downloadUrl=" + downloadUrl + "]";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	
	
	
	

}
