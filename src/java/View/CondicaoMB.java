/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.CondicaoPagamento;
import Model.Parcela;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;



/**
 *
 * @author THIAGO
 */
@ManagedBean
@ViewScoped
public class CondicaoMB {

    private CondicaoPagamento condicao = new CondicaoPagamento();
    private Parcela parcela = new Parcela();
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
    public void RemoveParcela(){
        condicao.getParcelas().add(parcela);
        parcela= new Parcela();
    }
    public void resetaCondicao(){
        condicao = new CondicaoPagamento();
    }
    
    
}
