package jsf;

import session.CustomerManager;
import session.DiscountCodeManager;
import entities.Customer;
import entities.DiscountCode;
import javax.ejb.EJB;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpSession;

@ManagedBean
@ViewScoped
public class CustomerMBean implements Serializable {

    @EJB
    private DiscountCodeManager discountCodeManager;
    @EJB
    private CustomerManager customerManager;
    /* Client courant dans la session, utilisé pour afficher ses détails ou
     * pour faire une mise à jour du client modifié dans la base */
    private Customer customer;
    private List<Customer> customers;
    /**
     * Id du client dont on veut les détails.
     */
    private int idDetails;

    public void preRenderView() {
        // Hack pour éviter l'erreur Cannot create a session after the response 
        // has been committed des datatables PrimeFaces

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }

    public int getIdDetails() {
        return idDetails;
    }

    public void setIdDetails(int idDetails) {
        this.idDetails = idDetails;
    }

    /** Creates a new instance of CustomerMBean */
    public CustomerMBean() {
    }

    /**
     * Renvoie la liste des clients pour affichage dans une DataTable
     * @return
     */
    public List<Customer> getCustomers() {
        if (customers == null) {
            customers = customerManager.getAllCustomers();
        }
        return customers;
    }

    /**
     * Renvoie les détails du client courant (celui dans l'attribut customer de
     * cette classe), qu'on appelle une propriété (property)
     * @return
     */
    public Customer getDetails() {
        return customer;
    }

    /**
     * Action handler - appelé lorsque l'utilisateur sélectionne une ligne dans
     * la DataTable pour voir les détails
     * @param customer
     * @return
     */
    public String showDetails(Customer customer) {
        this.customer = customer;
        return "CustomerDetails?id=" + customer.getCustomerId() + "&faces-redirect=true";
    }

    /**
     * Action handler - met à jour la base de données en fonction du client
     * passé en paramètres
     * @return
     */
    public String update() {
        System.out.println("###UPDATE###");
        customer = customerManager.update(customer);
        System.out.println("******DiscoutnCode du customer :" + customer.getDiscountCode());
        return "CustomerList?faces-redirect=true";
    }

    /**
     * Action handler - renvoie vers la page qui affiche la liste des clients
     * @return
     */
    public String list() {
        System.out.println("###LIST###");
        return "CustomerList";
    }

    /**
     * Renvoie un tableau de SelectItem pour affichage dans le menu déroulant
     * de la page des détails d'un client
     * @return
     */
    public List<DiscountCode> getDiscountCodes() {
        return discountCodeManager.getDiscountCodes();
    }

    public void lireClient(ComponentSystemEvent event) {
        this.customer = customerManager.findById(idDetails);
    }
}
