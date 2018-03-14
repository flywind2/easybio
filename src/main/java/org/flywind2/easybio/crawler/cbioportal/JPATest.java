/**
 * 
 */
package org.flywind2.easybio.crawler.cbioportal;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * 
 * @author flywind2.su@gmail.com
 * @date 2018年3月14日
 * @version 1.0
 */
public class JPATest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CancerType cancerType = new CancerType();
		cancerType.setCancerTypeId("blca");
		cancerType.setClinicalTrialKeywords("bladder urothelial carcinoma");
		cancerType.setDedicatedColor("Yellow");
		cancerType.setName("Bladder Urothelial Carcinoma");
		cancerType.setParent("bladder");
		cancerType.setShortName("BLCA");

		// 1.获得Factory
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA");
		// 2.获取Manager
		EntityManager em = factory.createEntityManager();
		// 3.获得事务，并开启uiwu
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		Query query = em.createQuery("select  t from CancerType t where  " + " t.name=? "
				+ "and t.clinicalTrialKeywords=? " + "and t.dedicatedColor=? " + "and t.shortName=? " + "and t.parent=?"
				+ "and t.cancerTypeId=?");
		query.setParameter(0, cancerType.getName());
		query.setParameter(1, cancerType.getClinicalTrialKeywords());
		query.setParameter(2, cancerType.getDedicatedColor());
		query.setParameter(3, cancerType.getShortName());
		query.setParameter(4, cancerType.getParent());
		query.setParameter(5, cancerType.getCancerTypeId());

		List result = query.getResultList();
		if (!result.isEmpty())
			System.out.println(result.get(0).getClass());
		transaction.commit();
		em.close();
		factory.close();
	}

}
