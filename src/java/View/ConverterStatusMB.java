
package View;

import Control.StatusEJB;
import Model.StatusPedido;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;


@Named(value = "converterStatusMB")
@RequestScoped 
public class ConverterStatusMB implements Converter {

    @EJB
    StatusEJB ejb;
    public ConverterStatusMB() {
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
            return ejb.findById((Integer) Integer.parseInt(value)  );
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((StatusPedido)value).getId().toString();
    }
}
