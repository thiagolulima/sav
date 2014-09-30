
package Control;

import Model.Categoria;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class CategoriaEJB {

         @PersistenceContext
          EntityManager em;
         
         public void incluiCategoria(Categoria categoria) throws Exception{
                  if(!categoria.getDescricao().trim().equals(""))
                    {
                       em.merge(categoria);
                    }
                  else{
                       throw new  Exception ("campo vazio");
                      }
         }
         public List<Categoria> findByAllCategoria(){
              List<Categoria> lista = em.createQuery("Select u From Categoria u").getResultList();
                     if(lista.size() < 1)
                     {
                         return  lista = new ArrayList<Categoria>();
                     }
                     return lista;
            
         }
         public void excluiCategoria(Categoria categoria) throws Exception{
               try{
                  categoria = em.merge(categoria);
                   em.remove(categoria);
               }catch(Exception erro )
               {
                   throw new Exception ("categoria já associada!");
                           
               }
             }
        public Categoria findById(Integer id){
        return ( Categoria )em.createQuery( "SELECT t FROM Categoria t WHERE t.id = :id" )
                 .setParameter( "id" , id ).getSingleResult();
    }

}
