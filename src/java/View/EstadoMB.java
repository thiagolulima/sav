/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.EstadoEJB;
import Model.Estado;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author THIAGO
 */
@Named(value = "estadoMB")
@RequestScoped
public class EstadoMB {

    @EJB
    EstadoEJB ejb;
    public EstadoMB() {
    }
    public List<Estado> findByAllEstado(){
        return ejb.findyAllEstado();
    }
}
