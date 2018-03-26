perl ~/biosoft/factera-1.4.4/factera.pl  \
-o tmp -f 0.15 -S 0.2  -x 500 -k 10  -c 10 -b 75 -p 10 \
-F ../bam/YF-57180124-HD753_S9.sort.bam /data/software/factera-1.4.4/exons.bed  /data/software/factera-1.4.4/hg19/hg19.2bit ~/panel/idt_210/fusion-designed-probe-


#VarDict
java -Djava.ext.dirs=../lib com.astrazeneca.vardict.Main

#run VarDict
java -Djava.ext.dirs=/data/home/sufeng/biosoft/VarDict-1.5.1/licom.astrazeneca.vardict.Main -G ~/reference/gatk/hg19/hg19.fasta -N 1711  -b  1711.recal.bam -f 0.001 -z -c 1 -S 2 -E 3 -g 4  ~/panel/idt_bt/IDTV3_designed-probe-coords.sort.bed ｜ ~/biosoft/VarDict-1.5.1/VarDict/teststrandbias.R | ~/biosoft/VarDict-1.5.1/VarDict/var2vcf_valid.pl -N 1711 -E -f 0.001


##horizon 标准品检测
#单独样本分析
/data/software/samtools-1.4.1/bin/samtools mpileup -d100000 -Bxq 30 -l ~/panel/horizon/ctdna.standard.snp.indel.bed -f /data/home/sufeng/reference/gatk/hg19/hg19.fasta \
/data/home/sufeng/data/210gene/20180212/VARIANTS/GATK/YF-57180207-AN779.recal.bam  \
/data/home/sufeng/data/210gene/20180212/VARIANTS/GATK/YF-57180207-HD776.recal.bam  \
/data/home/sufeng/data/210gene/20180212/VARIANTS/GATK/YF-57180207-HD778.recal.bam  \
/data/home/sufeng/data/210gene/20180212/VARIANTS/GATK/YF-57180207-HD779.recal.bam  \
| java -jar /data/software/VarScan-2.4.3/VarScan-2.4.3.jar mpileup2cns --mpileup 1  \
--min-var-freq 5.0E-4  \
--strand-filter 1  \
--output-vcf 1 \
--variants 1 --min-freq-for-hom 0.8 --min-coverage 200  --min-reads2 4  --min-avg-qual 20 --p-value 0.05 --vcf-sample-list sample.list


/data/software/samtools-1.4.1/bin/samtools mpileup -d100000 -Bxq 30 -l ~/panel/horizon/ctdna.standard.snp.indel.bed -f /data/home/sufeng/reference/gatk/hg19/hg19.fasta HD779.abra.sort.bam  | java -jar /data/software/VarScan-2.4.3/VarScan.v2.4.3.jar mpileup2cns --mpileup 1  --min-var-freq 5.0E-4  --strand-filter 1  --output-vcf 1  --p-value 0.99

#突变注释
perl /data/database/annovar/table_annovar.pl \
CORNING.mutect2.filter.vcf \
/data/database/annovar/humandb \
-outfile result \
--buildver hg19 \
--protocol refGene,cytoBand,snp138,cosmic70,ljb26_all,exac03,avsnp147,cg69,clinvar_20160302,dbnsfp30a,EAS.sites.2015_08,esp6500siv2_all,icgc21,kaviar_20150923,nci60 \
--operation g,r,f,f,f,f,f,f,f,f,f,f,f,f,f --remove -otherinfo -nastring . --vcfinput


cat sample.list | xargs -n1 -I {} sh -c "perl /data/database/annovar/table_annovar.pl {}.vcf /data/database/annovar/humandb --buildver hg19 --protocol refGene,snp138,cosmic70,1000g2015aug_all --operation g,f,f,f  --remove -otherinfo -nastring . --vcfinput "

#配对样本分析
/data/software/samtools-1.4.1/bin/samtools mpileup \
-d100000 -Bxq 30 \
-f /data/home/sufeng/reference/gatk/hg19/hg19.fasta  \
-l ~/panel/horizon/ctdna.standard.snp.indel.bed \
/data/home/sufeng/data/210gene/20180212/VARIANTS/GATK/YF-57180207-HD776.recal.bam \
/data/home/sufeng/data/210gene/20180212/VARIANTS/GATK/YF-57180207-AN779.recal.bam | \
java -Xmx1G -jar /data/software/VarScan-2.4.3/VarScan.v2.4.3.jar somatic --mpileup 1 \
--output-snp YF-57180207-AN779.snp.vcf \
--output-indel YF-57180207-AN779.indel.vcf \
--strand-filter 1 --output-vcf 1 --min-coverage-tumor 2 --min-var-freq 5.0E-4  --min-freq-for-hom 0.85   --min-avg-qual 20 


java -jar /data/software/GenomeAnalysisTK-3.7/GenomeAnalysisTK.jar  \
-T MuTect2 \
-cosmic ~/reference/cosmic/v81/CosmicCodingMuts.v81.hg19.vcf.gz \
-D ~/reference/gatk/hg19/dbsnp_138.hg19.vcf \
-useFilteredReadsForAnnotations \
--annotateNDA \
--dontUseSoftClippedBases \
-gt_mode DISCOVERY  \
--maxReadsInRegionPerSample 10000 \
--min_base_quality_score 30 \
-o YF-57180207-AN779.mutect2.vcf \
-I:tumor /data/home/sufeng/data/210gene/20180212/VARIANTS/GATK/YF-57180207-AN779.recal.bam \
-I:normal /data/home/sufeng/data/210gene/20180212/VARIANTS/GATK/YF-57180207-HD776.recal.bam \
-R ~/reference/gatk/hg19/hg19.fasta \
-L  ~/panel/horizon/ctdna.standard.snp.indel.bed


#校正
java -jar ~/biosoft/sinvict/abra/target/abra-0.97-SNAPSHOT-jar-with-dependencies.jar --in ../YF-57180207-HD779.recal.bam  --out `pwd`/HD779.abra.bam --ref ~/reference/gatk/hg19/hg19.fasta --targets ~/panel/agilent_151/target.sort.kmer.bed --threads 8 --working `pwd`/working

#计算bam readcount
~/biosoft/sinvict/bam-readcount/bin/bam-readcount  -q 30 -b 30 -f ~/reference/gatk/hg19/hg19.fasta -w 1  HD779.abra.sort.bam chr21 > ./chr21.readcount
#过滤fp varscan2 fpfilter
java -jar /data/software/VarScan-2.4.3/VarScan.v2.4.3.jar fpfilter HD779.varscan2.vcf  readcount/HD779.abra.bam.sort.readcount --output-file HD779.varscan2.filter.vcf --filtered-file HD779.varscan2.fail.vcf --dream3-settings 1 --min-var-freq 0.0005 --max-rl-diff 0.25


#统计比对信息
java -jar /data/software/picard-2.9.2/picard.jar CollectAlignmentSummaryMetrics R=~/reference/gatk/hg19/hg19.fasta  I={} O={}.alignment.stats.txt MAX_INSERT_SIZE=null