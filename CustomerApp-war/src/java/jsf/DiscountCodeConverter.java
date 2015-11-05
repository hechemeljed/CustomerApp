package jsf;

import session.DiscountCodeManager;
import entities.DiscountCode;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Convertit de manière implicite un DiscountCode en String en utilisant la 
 * propriété discountCode. Les conversions seront faites automatiquement
 * dans les pages JSF ou dans les Backing beans, sans qu'on ait besoin de
 * spécifier un attribut converter = ou même d'instancier ce convertisseur
 * @author richard
 */
@FacesConverter(forClass = entities.DiscountCode.class) 
public class DiscountCodeConverter implements Converter {
    // Note : on ne peut pas faire de @EJB ou @Inject dans un convertisseur. Ce
    // sera possible dans la prochaine version de JSF

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        // Récupère l'EJB session qui va aller chercher une instance de DiscoutnCode
        // à partir du code de la réduction. Ce code est généré automatiquement
        // par clic droit / insert code / call enterprise bean
        try {
            InitialContext ic = new InitialContext();
            DiscountCodeManager dcm =
                    (DiscountCodeManager) ic.lookup("java:app/TP2CustomerApplicationViewScoped-ejb/DiscountCodeManager");
            DiscountCode dc = dcm.getDiscountCodeByCode(value);
            return dc;
        } catch (NamingException e) {
            System.err.println("Problème de nommage");
            throw new ConverterException("Problème de nommage");
        }

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((DiscountCode) value).getDiscountCode().toString();
    }
}
