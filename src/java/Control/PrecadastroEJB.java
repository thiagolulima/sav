/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.PreCadastroCliente;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author THIAGO
 */
@Stateless
public class PrecadastroEJB {

            @PersistenceContext
            EntityManager em;
      public PreCadastroCliente  getPreCadastro(int id)
       {
           return (PreCadastroCliente) em.createQuery("Select u from PreCadastroCliente u Where u.id=:id")
                   .setParameter("id", id).getSingleResult();
       }

}
