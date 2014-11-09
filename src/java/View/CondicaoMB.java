/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.CondicaoEJB;
import Model.CondicaoPagamento;
import Model.Parcela;
import java.util.ArrayList;
import java.util.Collections;
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
    private List<Parcela> parcelas ;
    private int quantidadeParcelas = 0;
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

    public int getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(int quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }
   
    public void adicionaParcela(){
        
         if(quantidadeParcelas > 0 )
         {
             
              int numeroParcela = 1;
              if(condicao.getParcelas().size() > 0){
                   parcelas = new ArrayList<Parcela>();
                   condicao.setParcelas(parcelas);
              }
              for (int i = quantidadeParcelas ; i >= 1 ; i-- )
              {
                  parcela.setNumeroParcela(numeroParcela);
                  condicao.getParcelas().add(parcela);
                  parcela = new Parcela();
                  numeroParcela++;
              }
              parcela = new Parcela();
         }
         
       
    }
    public void removeParcela(){
                   parcelas = new ArrayList<Parcela>();
                   condicao.setParcelas(parcelas);
       
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
                Collections.sort(condicao.getParcelas());
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
   public List<Parcela> retornaListaParcelas(){
       Collections.sort(condicao.getParcelas());
       return condicao.getParcelas();
   }
   public void setaCondicao(CondicaoPagamento condicao){
       this.condicao = condicao;
      Collections.sort(condicao.getParcelas());
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
