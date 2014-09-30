/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.enderecoEJB;
import Model.Endereco;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;


@Named(value = "enderecoMB")
@RequestScoped
public class enderecoMB {

    @EJB
    enderecoEJB ejb;
    public enderecoMB() {
    }
     public void atualizaEndereco (Endereco endereco){
        ejb.salvaEndereco(endereco);
    }
    public void excluiEndereco(Endereco endereco){
        ejb.excluiEndereco(endereco);
    }
}
