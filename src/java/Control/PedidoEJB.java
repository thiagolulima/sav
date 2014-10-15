
package Control;

import Model.ItemDeVenda;
import Model.PedidoVenda;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PedidoEJB {

        @PersistenceContext 
         EntityManager em; 
        
        public void incluiPedido(PedidoVenda pedido) throws Exception
        {
            for(ItemDeVenda item : pedido.getOrcamento().getProdutos())
              {
                     if(item.getDesconto().getValorDesconto() > item.getDesconto().getValorDescontoAutorizado())
                     {
                         pedido.setBloqueio(true);
                     }
              }
              em.merge(pedido);
              if(pedido.isBloqueio() == true){
                 throw new Exception ("Pedido Bloqueado!");
                }
              else{
                 throw new Exception ("Pedido Mantido com Sucesso!");
              }
        }
        public List<PedidoVenda> findByAllPedidos(){
             List<PedidoVenda> lista = em.createQuery("Select u From PedidoVenda u ").getResultList();
             if (lista.size() <1){
                    return lista = new ArrayList<PedidoVenda>();
                }
                return lista;
        }

}
