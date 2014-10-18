/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.CondicaoEJB;
import Model.CondicaoPagamento;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author THIAGO
 */
@Named(value = "converterCondicaoMB")
@RequestScoped
public class ConverterCondicaoMB implements Converter {

    @EJB
    CondicaoEJB ejb;
    public ConverterCondicaoMB() {
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
         return ejb.findById((Integer) Integer.parseInt(value)  );
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((CondicaoPagamento)value).getId().toString();
    }
}
