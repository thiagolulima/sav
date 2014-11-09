
package View;

import Control.OrcamentoEJB;
import Model.Orcamento;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;



@javax.faces.bean.ManagedBean
@ViewScoped
public class OrcamentoMB {

    @EJB
    OrcamentoEJB orcamentoEJB;
    public OrcamentoMB() {
    }
    public Orcamento incluiOrcamento(Orcamento orcamento)
    {
        return orcamentoEJB.incluiOrcamento(orcamento);
    }
    public void removeOrcamento(Orcamento orcamento){
        orcamentoEJB.removeOrcamento(orcamento);
    }
}
