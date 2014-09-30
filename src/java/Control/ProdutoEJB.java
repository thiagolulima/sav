
package Control;

import Model.Produto;
import Model.ProdutoKit;
import Model.ProdutoUnico;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class ProdutoEJB {
       
         @PersistenceContext
          EntityManager em;
        
         
         public void incluiProduto (Produto produto) throws Exception{
                    if(verificaCodigoProdutoNovo(produto) == false){
                       if(produto.getCodProduto() < 1)
                       {
                           throw new Exception (" Codigo menor que 1")  ;
                       }
                       else
                       {
                       em.merge(produto);  
                       } 
                    }
                    else{
                       throw new Exception ("Codigo Produdo Ja cadastrado!")  ;
                    }
         }
         public void alteraProduto (Produto produto) throws Exception{
                    if(verificaCodigoProdutoAltera(produto) == false){
                         if(produto.getCodProduto() < 1)
                           {
                            throw new Exception (" Codigo menor que 1")  ;
                           }
                        else
                          {
                            em.merge(produto);  
                          }  
                    }
                    else{
                       throw new Exception ("Codigo Produdo Ja cadastrado!")  ;
                    }
         }
         public boolean verificaCodigoProdutoNovo(Produto produto) {
                 List<Produto> lista = 
                         em.createQuery("Select u From Produto u Where u.codProduto =:cod ")
                         .setParameter("cod",produto.getCodProduto()).getResultList();
                 for(Produto prod : lista)
                 {
                        if(prod.getCodProduto() == produto.getCodProduto()){
                            return true;
                        }
                 }
                 return false;
         }
         public boolean verificaCodigoProdutoAltera(Produto produto) {
                 List<Produto> lista = 
                         em.createQuery("Select u From Produto u Where u.codProduto =:cod and u.id <> :id ")
                         .setParameter("cod",produto.getCodProduto()).setParameter("id", produto.getId()).getResultList();
                 for(Produto prod : lista)
                 {
                        if(prod.getCodProduto() == produto.getCodProduto()){
                            return true;
                        }
                 }
                 return false;
         }
         public List<ProdutoUnico> findByAllProdutoUnico(int tipoPesquisa, String pesquisa) throws Exception{
                List<ProdutoUnico> lista = em.createQuery("Select u From ProdutoUnico u   ").getResultList();
                if(tipoPesquisa == 0 || pesquisa.equals(""))
                  {
                     return new ArrayList<ProdutoUnico>();
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
                     return em.createQuery("select u from ProdutoUnico u where u.codProduto =:produto ")
                         .setParameter("produto", Integer.parseInt(pesquisa)).getResultList();
                   }
                if (tipoPesquisa == 2)
                   {   
                     return em.createQuery("select u from ProdutoUnico u where u.descricao LIKE :descricao ")
                         .setParameter("descricao", "%"+pesquisa+"%").getResultList();
                   }
                if (tipoPesquisa == 3)
                   {   
                     return em.createQuery("select u from ProdutoUnico u where u.numeroSerie LIKE :numero ")
                         .setParameter("numero", "%"+pesquisa+"%").getResultList();
                   }
                if (tipoPesquisa == 4)
                   {   
                     return em.createQuery("select u from ProdutoUnico u where u.categoria.descricao LIKE :categoria ")
                         .setParameter("categoria", "%"+pesquisa+"%").getResultList();
                   }
                return new ArrayList<ProdutoUnico>();
         }
         public List<ProdutoKit> findByAllProdutoKit(){
                List<ProdutoKit> lista = em.createQuery("Select u From ProdutoKit u  ").getResultList();
                if (lista.size() <1){
                    return lista = new ArrayList<ProdutoKit>();
                }
                return lista;
         }
         public void excluiProduto(Produto produto) throws Exception{
             try{
                 em.merge(produto);
                 em.remove(produto);
             }catch (Exception erro)
             {
                 throw new Exception("Produto Associoado!");
             }
         }
   

}
