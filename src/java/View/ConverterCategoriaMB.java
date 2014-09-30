
package View;

import Control.CategoriaEJB;
import Model.Categoria;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;


@Named(value = "converterProdutoMB")
@RequestScoped
public class ConverterCategoriaMB implements Converter {

    @EJB
    CategoriaEJB ejb;
    public ConverterCategoriaMB() {
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return ejb.findById((Integer) Integer.parseInt(value)  );
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Categoria)value).getId().toString();
    }
}
