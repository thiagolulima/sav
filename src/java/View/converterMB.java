/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.EstadoEJB;
import Model.Estado;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


@Named(value = "converterMB")
@RequestScoped
public class converterMB implements Converter {

    public converterMB() {
    }
      
    @EJB
    EstadoEJB ejb;
   
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return ejb.findById((Integer) Integer.parseInt(value)  );
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Estado)value).getId().toString();
    }
}
