/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Estado;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class EstadoEJB {

    @PersistenceContext
    EntityManager em;
    public List<Estado> findyAllEstado(){
        return em.createQuery("SELECT u FROM Estado u").getResultList();
    }
    public Estado findById(Integer id){
        return ( Estado )em.createQuery( "SELECT t FROM Estado t WHERE t.id = :id" )
                 .setParameter( "id" , id ).getSingleResult();
    }

}
