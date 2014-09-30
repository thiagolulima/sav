
package Control;

import Model.Orcamento;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class OrcamentoEJB {

       @PersistenceContext
        EntityManager em;
       public Orcamento incluiOrcamento(Orcamento orcamento)
       {
           return em.merge(orcamento);
       }

}
