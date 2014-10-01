
package Control;

import Model.Funcionario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class FuncionarioEJB {
        
              @PersistenceContext
              EntityManager em;
       public void incluiFuncionario(Funcionario funcionario)
       {
           em.merge(funcionario);
       }
       public List<Funcionario> findAllFuncionario(){
         return  em.createQuery("SELECT u FROM u ").getResultList();
       }
}
