
package View;

import Control.StatusEJB;
import Model.Categoria;
import Model.StatusPedido;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@javax.faces.bean.ManagedBean
@ViewScoped
public class StatusPedidoMB {

    StatusPedido status = new StatusPedido();
    @EJB
    StatusEJB ejb;
    public StatusPedidoMB() {
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }
     public void incluiStatus() throws Exception{
        try{
               ejb.incluiStatus(status);
               adicionarMensagem(FacesMessage.SEVERITY_INFO,"Status Incluida com Sucesso!");  
               status = new StatusPedido();
           }catch (Exception erro)
           {
                 adicionarMensagem(FacesMessage.SEVERITY_ERROR,erro.getMessage());
           }
                     
    }
    public void excluiStatus(){
        try{
             ejb.excluiStatus(status);
          }catch (Exception erro)
        {
            
           adicionarMensagem(FacesMessage.SEVERITY_ERROR, erro.getMessage()); 
        }
    }
    public List<StatusPedido> findByAllStatus(){
           return ejb.findByAllStatus();
    }
    private void adicionarMensagem(FacesMessage.Severity severidade, String msg)
          {
           FacesContext facesContext = FacesContext.getCurrentInstance();
           facesContext.addMessage(null,
           new FacesMessage(severidade, msg, null));
    }
    public String mensagemConfirmaExclusaoStatus(){
        
        return "\n" + status.getDescricao() + " ?";
    }
    public void resetaStatus(){
       status = new StatusPedido();
    }
    public void setaStatusExclusao(StatusPedido status){
        this.status = status;
    }

}
