/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.CondicaoEJB;
import Model.CondicaoPagamento;
import Model.Parcela;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;



/**
 *
 * @author THIAGO
 */
@ManagedBean
@ViewScoped
public class CondicaoMB {

    private CondicaoPagamento condicao = new CondicaoPagamento();
    private Parcela parcela = new Parcela();
    @EJB
    CondicaoEJB ejb;
    public CondicaoMB() {
    }

    public CondicaoPagamento getCondicao() {
        return condicao;
    }

    public void setCondicao(CondicaoPagamento condicao) {
        this.condicao = condicao;
    }

    public Parcela getParcela() {
        return parcela;
    }
    public void setParcela(Parcela parcela) {
        this.parcela = parcela;
    }

    public void adicionaParcela(){
        
        parcela.setNumeroParcela(condicao.preencheNumeroParcelas());
        condicao.getParcelas().add(parcela);
        parcela= new Parcela();
    }
    public void removeParcela(){
       int index = condicao.getParcelas().size() - 1;
       for(Parcela p : condicao.getParcelas())
       {
           condicao.getParcelas().remove(index);
       }
       
    }
    public void resetaCondicao(){
        condicao = new CondicaoPagamento();
    }
    public void incluiCondicao(){
        if(condicao.getParcelas().size() < 1){
            adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Adicione Parcelas!");
        }
        else{
              if(condicao.getDescricao().trim().equals(""))
               {
                 adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Preencha Descricao!");
               }
             else
               {
                condicao.setDescricao(condicao.getDescricao().toUpperCase());
                ejb.incluiCondicao(condicao);
                resetaCondicao();
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Condicao Mantida Com Sucesso");
              }
          }
    }
    public List<CondicaoPagamento> findByAllCondicao(){
         return ejb.findByAllCondicao();
    }
   private void adicionarMensagem(FacesMessage.Severity severidade, String msg){
           FacesContext facesContext = FacesContext.getCurrentInstance();
           facesContext.addMessage(null,
           new FacesMessage(severidade, msg, null));
     }
   public void setaCondicao(CondicaoPagamento condicao){
       this.condicao = condicao;
   }
   public void removeCondicao(){
       try
       {
         ejb.removeCondicao(condicao);
         adicionarMensagem(FacesMessage.SEVERITY_INFO, "Condicao Removida com Sucesso!");
         condicao = new CondicaoPagamento();
       }
       catch (Exception erro)
       {
              adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Condicao ja Utilizada");
       }
   }
    public String mensagemConfirmaExclusaoCondicao(){
        
        return "\n" + condicao.getDescricao() + " ?";
    }
    
    
}
