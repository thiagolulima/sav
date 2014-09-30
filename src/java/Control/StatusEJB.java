
package Control;

import Model.Categoria;
import Model.StatusPedido;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class StatusEJB {
          
          @PersistenceContext
          EntityManager em;
         
         public void incluiStatus(StatusPedido status) throws Exception{
                  if(!status.getDescricao().trim().equals(""))
                    {
                       em.merge(status);
                    }
                  else{
                       throw new  Exception ("campo vazio");
                      }
         }
         public List<StatusPedido> findByAllStatus(){
              List<StatusPedido> lista = em.createQuery("Select u From StatusPedido u").getResultList();
                     if(lista.size() < 1)
                     {
                         return  lista = new ArrayList<StatusPedido>();
                     }
                     return lista;
            
         }
         public void excluiStatus(StatusPedido status) throws Exception{
               try{
                  status = em.merge(status);
                   em.remove(status);
               }catch(Exception erro )
               {
                   throw new Exception ("status já associada!");
                           
               }
             }
        public StatusPedido findById(Integer id){
        return ( StatusPedido )em.createQuery( "SELECT t FROM StatusPedido t WHERE t.id = :id" )
                 .setParameter( "id" , id ).getSingleResult();
    }

    

}
