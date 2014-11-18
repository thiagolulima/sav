/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.PrecadastroEJB;
import Model.PreCadastroCliente;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;



@ManagedBean
@RequestScoped
public class PreCadastroMB {

    @EJB
    PrecadastroEJB ejb;
    public PreCadastroMB() {
    }
    public PreCadastroCliente getPreCadastro(int id)
    {
       return ejb.getPreCadastro(id) ;
    }
}
