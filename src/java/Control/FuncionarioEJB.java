
package Control;

import Model.Funcionario;
import Model.Pessoa;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class FuncionarioEJB {
        
              @PersistenceContext
              EntityManager em;
       public void incluiFuncionario(Funcionario funcionario)throws Exception
       {
           if (verificaSeACadastradoEmail(funcionario) == false)
        {
            
            em.merge(funcionario); 
        }
        else{
              throw new Exception("Email já Cadastrado");
             }
       }
       public List<Funcionario> findAllFuncionario(){
         return  em.createQuery("SELECT u FROM Funcionario u ").getResultList();
       }
       public boolean verificaSeACadastradoEmail(Pessoa pessoa) throws Exception{
            
             List<Pessoa> lista = em.createQuery("SELECT u FROM Pessoa u WHERE u.email=:email and u.id <> :id")
                     .setParameter("email", pessoa.getEmail().trim()).setParameter("id", pessoa.getId()).getResultList();
              if(pessoa.getId()== null){
                 lista = em.createQuery("SELECT u FROM Pessoa u WHERE u.email=:email ")
                     .setParameter("email", pessoa.getEmail().trim()).getResultList();
              }
              if(lista.size() > 0){
                    return true;
               }else{
              return false ;  
             }
         }
}
