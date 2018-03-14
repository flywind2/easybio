/**
 * 
 */
package ucsc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

/**
 * @author flywind2.su@gmail.com
 * @date 2018年3月14日
 * @version 1.0
 *  @see http://genome-asia.ucsc.edu/cgi-bin/hgTables?db=hg19&hgta_group=rep&hgta_track=rmsk&hgta_table=rmsk&hgta_doSchema=describe+table+schema
 */
@Entity
@Table(name="rmsk")
public class RepeatMasker  {
    
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pid;
    
    @Column
    private int bin;
    
    @Column
    private int swScore;
    
    @Column
    private int milliDiv;
    
    @Column
    private int milliDel;
    
    @Column
    private int milliIns;
    
    @Column
    private String genoName;
    
    @Column
    private int genoStart;
    
    @Column
    private int genoEnd;
    
    @Column
    private int genoLeft;
    
    @Column
    private char strand;
    
    @Column
    private String repName;
    
    @Column
    private String repClass;
    
    @Column
    private String repFamily;
    
    @Column
    private int repStart;
    
    @Column
    private int repEnd;
    
    @Column
    private int repLeft;
    
    @Column
    private int id;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public int getBin() {
        return bin;
    }

    public void setBin(int bin) {
        this.bin = bin;
    }

    public int getSwScore() {
        return swScore;
    }

    public void setSwScore(int swScore) {
        this.swScore = swScore;
    }

    public int getMilliDiv() {
        return milliDiv;
    }

    public void setMilliDiv(int milliDiv) {
        this.milliDiv = milliDiv;
    }

    public int getMilliDel() {
        return milliDel;
    }

    public void setMilliDel(int milliDel) {
        this.milliDel = milliDel;
    }

    public int getMilliIns() {
        return milliIns;
    }

    public void setMilliIns(int milliIns) {
        this.milliIns = milliIns;
    }

    public String getGenoName() {
        return genoName;
    }

    public void setGenoName(String genoName) {
        this.genoName = genoName;
    }

    public int getGenoStart() {
        return genoStart;
    }

    public void setGenoStart(int genoStart) {
        this.genoStart = genoStart;
    }

    public int getGenoEnd() {
        return genoEnd;
    }

    public void setGenoEnd(int genoEnd) {
        this.genoEnd = genoEnd;
    }

    public int getGenoLeft() {
        return genoLeft;
    }

    public void setGenoLeft(int genoLeft) {
        this.genoLeft = genoLeft;
    }

    public char getStrand() {
        return strand;
    }

    public void setStrand(char strand) {
        this.strand = strand;
    }

    public String getRepName() {
        return repName;
    }

    public void setRepName(String repName) {
        this.repName = repName;
    }

    public String getRepClass() {
        return repClass;
    }

    public void setRepClass(String repClass) {
        this.repClass = repClass;
    }

    public String getRepFamily() {
        return repFamily;
    }

    public void setRepFamily(String repFamily) {
        this.repFamily = repFamily;
    }

    public int getRepStart() {
        return repStart;
    }

    public void setRepStart(int repStart) {
        this.repStart = repStart;
    }

    public int getRepEnd() {
        return repEnd;
    }

    public void setRepEnd(int repEnd) {
        this.repEnd = repEnd;
    }

    public int getRepLeft() {
        return repLeft;
    }

    public void setRepLeft(int repLeft) {
        this.repLeft = repLeft;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RepeatMasker [genoName=" + genoName + ", genoStart=" + genoStart + ", genoEnd=" + genoEnd + ", repName="
                + repName + ", repClass=" + repClass + ", repFamily=" + repFamily + "]";
    }
    
    
}
