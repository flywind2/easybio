/**
 * 
 */
package org.flywind2.easybio.crawler.cbioportal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * 
 * @author flywind2.su@gmail.com
 * @date 2018年3月14日
 * @version 1.0
 */
public class CancerStudyFetch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Document document = Jsoup.connect("http://www.cbioportal.org/api/studies?projection=DETAILED")
					.ignoreContentType(true).get();

			JsonElement jsonElement = new JsonParser().parse(document.text());
			JsonArray jsonArray = jsonElement.getAsJsonArray();
			final Gson gson = new Gson();
			List<Study> studyList = new ArrayList<>();
			for (JsonElement e : jsonArray) {
				final Study study = gson.fromJson(e, Study.class);
				study.setDownloadUrl("https://media.githubusercontent.com/media/cBioPortal/datahub/master/public/"
						+ study.getStudyId() + ".tar.gz");
				studyList.add(study);
			}
			
			// 1.获得Factory  
	        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA");  
	        // 2.获取Manager  
	        EntityManager manager = factory.createEntityManager();  
	        // 3.获得事务，并开启uiwu  
	        EntityTransaction transaction = manager.getTransaction();  
	        transaction.begin();  
	        // 4.执行sql  
	        
	        for(Study study:studyList) {
	        	CancerType cancerType = processCancerType(manager,study.getCancerType());
	        	study.setCancerType(cancerType);
	        	manager.persist(study);
	        }
	       
	        // 5.提交事务，关闭资源  
	        transaction.commit();  
	        manager.close();  
	        factory.close();  

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static CancerType processCancerType(EntityManager em ,CancerType cancerType) {
		// TODO Auto-generated method stub
		CancerType temp = cancerType;
		Query query = em.createQuery("select  t from CancerType t where  " + " t.name=? "
				+ "and t.clinicalTrialKeywords=? " + "and t.dedicatedColor=? " + "and t.shortName=? " + "and t.parent=?"
				+ "and t.cancerTypeId=?",CancerType.class);
		query.setParameter(0, cancerType.getName());
		query.setParameter(1, cancerType.getClinicalTrialKeywords());
		query.setParameter(2, cancerType.getDedicatedColor());
		query.setParameter(3, cancerType.getShortName());
		query.setParameter(4, cancerType.getParent());
		query.setParameter(5, cancerType.getCancerTypeId());

		List<CancerType> result = query.getResultList();
		if (result.isEmpty()) {
			em.persist(temp);
		}else {
			temp = result.get(0);
		}
			
		
    	
		return temp;
	}

}
