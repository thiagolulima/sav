
package Control;

import Model.Pessoa;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class UsersEJB {
         
         @PersistenceContext
         EntityManager em;
         public void incluiUsuario(Pessoa pessoa){
                em.merge(pessoa.getUsuario());
                em.merge(pessoa);
         }
         public List<Pessoa> findByUsuarios(){
              List<Pessoa> lista = em.createQuery("select u from Pessoa u where u.usuario.id > 0 ").getResultList();
              if(lista.size() < 1){
                  return lista = new ArrayList<Pessoa>();
              }
              return lista;
         } 
         public List<Pessoa> findByTodosUsuarios(){
              List<Pessoa> lista = em.createQuery("select u from Pessoa u ").getResultList();
              if(lista.size() < 1){
                  return lista = new ArrayList<Pessoa>();
              }
              return lista;
         } 

}
