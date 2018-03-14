package org.flywind2.easybio;

import java.io.File;

import htsjdk.variant.variantcontext.Genotype;
import htsjdk.variant.variantcontext.VariantContext;
import htsjdk.variant.variantcontext.writer.Options;
import htsjdk.variant.variantcontext.writer.VariantContextWriter;
import htsjdk.variant.variantcontext.writer.VariantContextWriterBuilder;
import htsjdk.variant.vcf.VCFFileReader;
import htsjdk.variant.vcf.VCFHeader;

public class VariantGenotypeFilterDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		VCFFileReader reader = new VCFFileReader(new File("E:\\BaiduNetdiskDownload\\B1234\\1711\\CORNING.mutect1.vcf"),false);
		VCFHeader header = reader.getFileHeader();
		//
		VariantContextWriter output = new VariantContextWriterBuilder().setOption(Options.INDEX_ON_THE_FLY).setReferenceDictionary(header.getSequenceDictionary()).setOutputFile("E:\\BaiduNetdiskDownload\\B1234\\1711\\CORNING.mutect1.final.vcf").build();
		output.writeHeader(header);
		int a=0,b=0;
		for(VariantContext vc : reader) {
			a++;
			final Genotype genotype = vc.getGenotype("CORNING");
			if((!vc.isFiltered())&&genotype.hasExtendedAttribute("FA")) {
				if(Double.parseDouble(genotype.getExtendedAttribute("FA").toString()) >=0.05) {
					b++;
					output.add(vc);
				}
				
			}
		}
		System.out.println("variant mutant allele frequency greater than 0.05 is "+  + (b/(double)a) + "("+b+","+a+")");
		reader.close();
		output.close();
	}

}
