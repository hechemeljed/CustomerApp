                                                                     
                                                                     
                                                                     
                                             
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entities.DiscountCode;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author richard
 */
@Stateless
@LocalBean
public class DiscountCodeManager {
  @PersistenceContext
  private EntityManager em;

  public List<DiscountCode> getDiscountCodes() {
    Query query = em.createNamedQuery("DiscountCode.findAll");
    return query.getResultList();
  }
  
  public DiscountCode getDiscountCodeByCode(String c) {
    Query query = em.createNamedQuery("DiscountCode.findByDiscountCode");
    query.setParameter("discountCode", c.charAt(0));
    return (DiscountCode)query.getSingleResult();
  }

  // Add business logic below. (Right-click in editor and choose
  // "Insert Code > Add Business Method")

  public void persist(Object object) {
    em.persist(object);
  }

  
}
