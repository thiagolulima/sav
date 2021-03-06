
package Control;

import Model.Fisica;
import Model.Funcionario;
import Model.Pessoa;
import java.util.ArrayList;
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
       public List<Funcionario> findAllFuncionarioVendedor(){
         return  em.createQuery("SELECT u FROM Funcionario u Where u.funcionarioAtivo = 1 and u.usuario.authority  = 'ROLE_MASTER' OR u.usuario.authority = 'ROLE_VENDEDOR' ").getResultList();
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
       public List<Funcionario> findAllFuncionario(int tipoPesquisa, String pesquisa) throws Exception{
        if(tipoPesquisa == 0 || pesquisa.trim().equals(""))
          {
            return new ArrayList<Funcionario>();
          }
        if (tipoPesquisa == 1)
          {
              try {
                  Integer.parseInt(pesquisa);
                 }
              catch (Exception e)
               {
                    throw new Exception ("codido Inválido!");
               } 
            return em.createQuery("select u from Funcionario u where u.id =:id ")
                    .setParameter("id", Integer.parseInt(pesquisa)).getResultList();
          }
        if (tipoPesquisa == 2)
          {   
            return em.createQuery("select u from Funcionario u where u.nome LIKE :nome ")
                    .setParameter("nome", "%"+pesquisa+"%").getResultList();
          }
        if (tipoPesquisa == 3)
          {   
            return em.createQuery("select u from Funcionario u where u.cpf LIKE :cpf ")
                    .setParameter("cpf", "%"+pesquisa+"%").getResultList();
          }
        
        return new ArrayList<Funcionario>();
    }
    
}
