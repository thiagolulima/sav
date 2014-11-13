
package Control;

import Model.Funcionario;
import Model.ItemDeVenda;
import Model.Orcamento;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class OrcamentoEJB {

       @PersistenceContext
        EntityManager em;
       public Orcamento incluiOrcamento(Orcamento orcamento)
       { 
           em.merge(orcamento);
           return orcamento;
       }
       public void removeOrcamento(Orcamento orcamento){
               
               orcamento = em.merge(orcamento);
               em.remove(orcamento);
        }
       public List<Orcamento> findOrcamentosNaoVencidosPorFuncionario(Funcionario funcionario){
                  Date data = new Date();
                  List<Orcamento> lista = em.createQuery("Select u From Orcamento u WHERE u.funcionario.id =:id AND u.dataValidade >=:data ORDER BY u.dataValidade ASC")
                     .setParameter("id", funcionario.getId()).setParameter("data", data).getResultList();
             if (lista.size() <1){
                    return lista = new ArrayList<Orcamento>();
                }
            
                return lista;
       }
       public void atualizaItens(List<ItemDeVenda> itens){
           for(ItemDeVenda item : itens){
               em.merge(item.getDesconto());
           }
       }
       public Orcamento salvaOrcamento(Orcamento orcamento) throws Exception
        {
            for(ItemDeVenda item : orcamento.getProdutos())
              {
                     if(item.getDesconto().getValorDesconto() > item.getDesconto().getValorDescontoAutorizado())
                     {
                         orcamento.setBloqueio(true);
                     }
              }
              orcamento =  em.merge(orcamento);
              if(orcamento.isBloqueio() == true){
                  
                 throw new Exception ("Orcamento Bloqueado!");
                 
                }
              return orcamento; 
         }
         public void zeraNaoAutorizados(List<ItemDeVenda> itens){
               for(ItemDeVenda item : itens){
               if(item.getDesconto().getValorDescontoAutorizado() < item.getDesconto().getValorDesconto())
               {
                   item.getDesconto().setValorDesconto(item.getDesconto().getValorDescontoAutorizado());
                   item.atualizaDescontoValorPorcentagem();
               }
               em.merge(item.getDesconto());
           }
        }
           public List<Orcamento> findByAllOrcamentoBloqueados(){
            
             List<Orcamento> lista = em.createQuery("Select u From Orcamento u WHERE u.bloqueio = 1 ")
                     .getResultList();
             if (lista.size() <1){
                    return lista = new ArrayList<Orcamento>();
                }
            
                return lista;
        }

}
