
package Control;

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
        
        public void incluiPedido(PedidoVenda pedido)
        {
            em.merge(pedido);
        }
        public List<PedidoVenda> findByAllPedidos(){
             List<PedidoVenda> lista = em.createQuery("Select u From PedidoVenda u ").getResultList();
             if (lista.size() <1){
                    return lista = new ArrayList<PedidoVenda>();
                }
                return lista;
        }

}
