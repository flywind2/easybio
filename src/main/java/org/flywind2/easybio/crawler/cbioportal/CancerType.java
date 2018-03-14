/**
 * 
 */
package org.flywind2.easybio.crawler.cbioportal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * 
 * @author flywind2.su@gmail.com
 * @date 2018年3月14日
 * @version 1.0
 */
@Entity
@Table(name="cbioportal_cancer_type")
public class CancerType {
	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private String clinicalTrialKeywords;
	
	@Column
	private String dedicatedColor;
	
	@Column
	private String shortName;
	
	@Column
	private String parent;
	
	@Column
	private String cancerTypeId;
	
	
	@Version
	private Long version;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClinicalTrialKeywords() {
		return clinicalTrialKeywords;
	}

	public void setClinicalTrialKeywords(String clinicalTrialKeywords) {
		this.clinicalTrialKeywords = clinicalTrialKeywords;
	}

	public String getDedicatedColor() {
		return dedicatedColor;
	}

	public void setDedicatedColor(String dedicatedColor) {
		this.dedicatedColor = dedicatedColor;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getCancerTypeId() {
		return cancerTypeId;
	}

	public void setCancerTypeId(String cancerTypeId) {
		this.cancerTypeId = cancerTypeId;
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

	@Override
	public String toString() {
		return "CancerType [name=" + name + ", clinicalTrialKeywords=" + clinicalTrialKeywords + ", dedicatedColor="
				+ dedicatedColor + ", shortName=" + shortName + ", parent=" + parent + ", cancerTypeId=" + cancerTypeId
				+ "]";
	}

}
