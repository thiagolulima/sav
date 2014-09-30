/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Endereco;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class enderecoEJB {

       @PersistenceContext
    EntityManager em ;
    public void salvaEndereco(Endereco endereco){
        em.merge(endereco);
    }
    public void excluiEndereco(Endereco endereco){
        endereco = em.merge(endereco);
        em.remove(endereco);
    }

}
