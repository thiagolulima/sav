/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.CondicaoPagamento;
import Model.Parcela;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class CondicaoEJB {

      @PersistenceContext 
      EntityManager em;
      public void incluiCondicao(CondicaoPagamento condicao){
         Collections.sort(condicao.getParcelas());
         em.merge(condicao);
     }
     public List<CondicaoPagamento> findByAllCondicao(){
         return em.createQuery("Select u From CondicaoPagamento u").getResultList();
     }
     public CondicaoPagamento findById(Integer id){
        return ( CondicaoPagamento )em.createQuery( "SELECT t FROM CondicaoPagamento t WHERE t.id = :id" )
                 .setParameter( "id" , id ).getSingleResult();
    }
     public void removeCondicao(CondicaoPagamento condicao){
          condicao = em.merge(condicao);
          em.remove(condicao);
         
     }

}
