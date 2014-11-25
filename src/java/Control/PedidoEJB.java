
package Control;

import Model.Funcionario;
import Model.ItemDeVenda;
import Model.Orcamento;
import Model.PedidoVenda;
import Model.Pessoa;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PedidoEJB {

        @PersistenceContext 
         EntityManager em; 
        
        public PedidoVenda incluiPedido(PedidoVenda pedido) throws Exception
        {
            for(ItemDeVenda item : pedido.getOrcamento().getProdutos())
              {
                     if(item.getDesconto().getValorDesconto() > item.getDesconto().getValorDescontoAutorizado())
                     {
                         pedido.getOrcamento().setBloqueio(true);
                     }
              }
              incluiOrcamento(pedido.getOrcamento());
              pedido =  em.merge(pedido);
              if(pedido.getOrcamento().isBloqueio() == true){
                  
                 throw new Exception ("Pedido Bloqueado!");
                 
                }
              return pedido;
              
             
        }
      
       public void incluiOrcamento(Orcamento orcamento){
           em.merge(orcamento);
       }
       public void atualizaItens(List<ItemDeVenda> itens){
           for(ItemDeVenda item : itens){
               if(item.getId() != null){
                 em.merge(item.getDesconto());
               }
           }
       }
        public List<PedidoVenda> findByAllPedidos( Funcionario funcionario){
            
             List<PedidoVenda> lista = em.createQuery("Select u From PedidoVenda u WHERE u.orcamento.funcionario.id =:id")
                     .setParameter("id", funcionario.getId()).getResultList();
             if (lista.size() <1){
                    return lista = new ArrayList<PedidoVenda>();
                }
            
                return lista;
           
        }
        public List<PedidoVenda> findByAllPedidosCliente( Pessoa pessoa){
            
             List<PedidoVenda> lista = em.createQuery("Select u From PedidoVenda u WHERE u.orcamento.pessoa.id =:id AND u.status.id <> 3")
                     .setParameter("id",pessoa.getId())
                     .getResultList();
             if (lista.size() <1){
                    return lista = new ArrayList<PedidoVenda>();
                }
            
                return lista;
           
        }
        public List<PedidoVenda> findByAllPedidosClientePesquisa( Pessoa pessoa, int pesquisa){
            
             List<PedidoVenda> lista = em.createQuery("Select u From PedidoVenda u WHERE u.orcamento.pessoa.id =:id AND u.status.id <> 3 AND u.id =:pedidoId")
                     .setParameter("id",pessoa.getId()).setParameter("pedidoId",pesquisa )
                     .getResultList();
             if (lista.size() <1){
                    return lista = new ArrayList<PedidoVenda>();
                }
            
                return lista;
        }
        public List<PedidoVenda> findByAllPedidosData( Date dataInicial,Date dataFim, Pessoa pessoa){
            
             List<PedidoVenda> lista = 
                     em.createQuery("Select u From PedidoVenda u WHERE u.orcamento.pessoa.id =:id AND u.status.id <> 3 AND u.dataPedido BETWEEN :dataInicial AND :dataFim ")
                     .setParameter("id",pessoa.getId())
                     .setParameter("dataInicial", dataInicial)
                     .setParameter("dataFim", dataFim)
                     .getResultList();
             if (lista.size() <1){
                    return lista = new ArrayList<PedidoVenda>();
                }
            
                return lista;
           
        }
        public List<PedidoVenda> findByAllPedidos(){
            
             List<PedidoVenda> lista = em.createQuery("Select u From PedidoVenda u ")
                     .getResultList();
             if (lista.size() <1){
                    return lista = new ArrayList<PedidoVenda>();
                }
            
                return lista;
        }
        public List<PedidoVenda> findByAllPedidosBloqueados(){
            
             List<PedidoVenda> lista = em.createQuery("Select u From PedidoVenda u WHERE u.orcamento.bloqueio = 1")
                     .getResultList();
             if (lista.size() <1){
                    return lista = new ArrayList<PedidoVenda>();
                }
            
                return lista;
        }
        public void removePedido(PedidoVenda pedido){
               
               pedido = em.merge(pedido);
               em.remove(pedido);
        }
        public void removeOrcamento(Orcamento orcamento){
               
               orcamento = em.merge(orcamento);
               em.remove(orcamento);
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
     public List<PedidoVenda> findByAllPedidosPeriodo( Date dataInicial,Date dataFim){
            
             List<PedidoVenda> lista = 
                     em.createQuery("Select u From PedidoVenda u WHERE  u.status.id = 3 AND u.dataPedido BETWEEN :dataInicial AND :dataFim ")
                     .setParameter("dataInicial", dataInicial)
                     .setParameter("dataFim", dataFim)
                     .getResultList();
             if (lista.size() <1){
                    return lista = new ArrayList<PedidoVenda>();
                }
            
                return lista;
           
        }

} 
