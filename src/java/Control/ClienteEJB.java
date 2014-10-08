

package Control;

import Model.Fisica;
import Model.Juridica;
import Model.Pessoa;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class ClienteEJB {
    @PersistenceContext(unitName = "SAVPU")
     EntityManager em;
    public void incluiCliente(Pessoa pessoa) throws Exception{
        if (verificaSeACadastradoEmail(pessoa) == false)
        {
            
            em.merge(pessoa); 
        }
        else{
              throw new Exception("Email já Cadastrado");
             }
    }
    public List<Fisica> findAllClienteFisica(int tipoPesquisa, String pesquisa) throws Exception{
        if(tipoPesquisa == 0 || pesquisa.trim().equals(""))
          {
            return new ArrayList<Fisica>();
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
            return em.createQuery("select u from Fisica u where u.id =:id ")
                    .setParameter("id", Integer.parseInt(pesquisa)).getResultList();
          }
        if (tipoPesquisa == 2)
          {   
            return em.createQuery("select u from Fisica u where u.nome LIKE :nome ")
                    .setParameter("nome", "%"+pesquisa+"%").getResultList();
          }
        if (tipoPesquisa == 3)
          {   
            return em.createQuery("select u from Fisica u where u.cpf LIKE :cpf ")
                    .setParameter("cpf", "%"+pesquisa+"%").getResultList();
          }
        
        return new ArrayList<Fisica>();
    }
    
    public List<Juridica> findAllClienteJuridica(int tipoPesquisa, String pesquisa) throws Exception{
         if(tipoPesquisa == 0 || pesquisa.trim().equals(""))
          {
            return new ArrayList<Juridica>();
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
            return em.createQuery("select u from Juridica u where u.id =:id ")
                    .setParameter("id", Integer.parseInt(pesquisa)).getResultList();
          }if (tipoPesquisa == 2)
          {   
            return em.createQuery("select u from Juridica u where u.nome LIKE :nome ")
                    .setParameter("nome", "%"+pesquisa+"%").getResultList();
          }
          if (tipoPesquisa == 3)
          {   
            return em.createQuery("select u from Juridica u where u.cnpj LIKE :cnpj ")
                    .setParameter("cnpj", "%"+pesquisa+"%").getResultList();
          }
          if (tipoPesquisa == 4)
          {   
            return em.createQuery("select u from Juridica u where u.nomeFantasia LIKE :fantasia ")
                    .setParameter("fantasia", "%"+pesquisa+"%").getResultList();
          }
         
        return new ArrayList<Juridica>();
    }
    public void excluiCliente(Pessoa cliente){
              cliente = em.merge(cliente);
              em.remove(cliente);
             }
   public void verificaSeACadastradocpf(Fisica fisica) throws Exception{
             List<Fisica> clientes =  new ArrayList<Fisica>();
                   clientes =  em.createQuery("SELECT u FROM Fisica u WHERE u.cpf=:cpf")
                     .setParameter("cpf", fisica.getCpf()).getResultList();
             for(Fisica cli : clientes ){
                 if(cli.getCpf().equals(fisica.getCpf())){
                     throw new Exception ("CPF cadastrado!");
                   } 
               }
            
            incluiCliente(fisica);   
         }
   public void verificaSeACadastradoCnpj(Juridica juridica) throws Exception{
             List<Juridica> clientes =  new ArrayList<Juridica>();
                   clientes =  em.createQuery("SELECT u FROM Juridica u WHERE u.cnpj=:cnpj")
                     .setParameter("cnpj",juridica.getCnpj() ).getResultList();
             for(Juridica cli : clientes ){
                 if(cli.getCnpj().equals(juridica.getCnpj())){
                     throw new Exception ("CNPJ cadastrado!");
                   } 
               }
            incluiCliente(juridica);   
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
               }
              return false;   
         }
    
}
