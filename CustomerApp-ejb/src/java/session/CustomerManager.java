                                                                     
                                                                     
                                                                     
                                             
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entities.Customer;
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
public class CustomerManager {

  @PersistenceContext(unitName = "CustomerApp-ejbPU")  
    private EntityManager em;  
  

  public List<Customer> getAllCustomers() {
//    System.out.println("Requête dans la base");
    Query query = em.createNamedQuery("Customer.findAll");
    return query.getResultList();
  }
  
  public Customer findById(int id) {
    return em.find(Customer.class, id);
  }
  public Customer update(Customer customer) {
    return em.merge(customer);
  }

  public void persist(Object object) {
    em.persist(object);
  }
    public Customer getCustomer(int idCustomer) {  
        return em.find(Customer.class, idCustomer);  
}
}
