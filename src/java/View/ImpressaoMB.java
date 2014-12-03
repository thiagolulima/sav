/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.PedidoEJB;
import Model.CondicaoPagamento;
import Model.Orcamento;
import Model.PedidoVenda;
import Model.Pessoa;
import Model.Telefones;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;

/**
 *
 * @author THIAGO
 */
@Named(value = "impressaoMB")
@SessionScoped
public class ImpressaoMB implements Serializable {

    PedidoVenda pedido = new PedidoVenda();
    Orcamento orcamento = new Orcamento();
    Pessoa pessoa = new Pessoa();
    CondicaoPagamento condicao = new CondicaoPagamento();
    Date dataInicio = new Date();
    Date dataFim = new Date();
    int codicoFiltro = 0;
    String pesquisa = "";
    List<PedidoVenda> pedidos = new ArrayList<PedidoVenda>();
    @EJB
    PedidoEJB pedidoEJB;
    public ImpressaoMB() {
    }

    public PedidoVenda getPedido() {
        return pedido;
    }

    public void setPedido(PedidoVenda pedido) {
        this.pedido = pedido;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public int getCodicoFiltro() {
        return codicoFiltro;
    }

    public void setCodicoFiltro(int codicoFiltro) {
        this.codicoFiltro = codicoFiltro;
    }

    public String getPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public CondicaoPagamento getCondicao() {
        return condicao;
    }

    public void setCondicao(CondicaoPagamento condicao) {
        this.condicao = condicao;
    }
    
    public String valorTotalCompra(){
         if(pedido.getId() != null){
          return pedido.getOrcamento().retornaTotalCompra();
         }else{
              return "";
         }
    }
     public String valorTotalCompraOrcamento(){
         if(orcamento.getId() != null){
          return orcamento.retornaTotalCompra();
         }else{
              return "";
         }
    }
     public String retornaTel(){
         String telefone = "";
         if(pedido.getId() != null){
            for(Telefones tel : pedido.getOrcamento().getPessoa().getTelefones())
              {
                 telefone = telefone + tel.getNumeroTel() + "  ";
              }
         }
         return telefone;
         
     }
     public String retornaTelOrc(){
         String telefone = "";
         if(orcamento.getId() != null){
            for(Telefones tel : orcamento.getPessoa().getTelefones())
              {
                 telefone = telefone + tel.getNumeroTel() + "  ";
              }
         }
         return telefone;
         
     }
     public List<PedidoVenda> pedidosPorPeriodo(){
         pedidos = pedidoEJB.findByAllPedidosPeriodo(dataInicio, dataFim);
         return  pedidos;
     }
     public List<PedidoVenda> pedidosPorPeriodoCondicao(){
         pedidos = pedidoEJB.findByAllPedidosPeriodoCondicao(dataInicio, dataFim,condicao);
         return  pedidos;
     }
     public List<PedidoVenda> pedidosPorPeriodoFuncionario(){
         pedidos = pedidoEJB.findByAllPedidosDataFuncionario(dataInicio, dataFim, pessoa);
         return  pedidos;
     }
     public List<PedidoVenda> pedidosPorPeriodoCliente(){
         pedidos = pedidoEJB.findByAllPedidosDataCliente(dataInicio, dataFim, pessoa);
         return  pedidos;
     }
     public String retornaValorTotalPedidos(){
         
         Double total = 0.0;
         for(PedidoVenda ped: pedidos){
             
             total = total + ped.getOrcamento().retornaTotalCompraDouble();
         }
         String formatString = String.format("%.2f", total)  ;
         return "R$  " + String.valueOf(formatString);
     }
     public void imprimeRelatorioVendas() throws IOException{
         if(codicoFiltro == 0){
             FacesContext.getCurrentInstance().getExternalContext().redirect("imprimePeriodo.xhtml");   
         }
         if(codicoFiltro == 1){
             FacesContext.getCurrentInstance().getExternalContext().redirect("imprimeFuncionario.xhtml"); 
         }
         if(codicoFiltro == 2){
             FacesContext.getCurrentInstance().getExternalContext().redirect("imprimeCliente.xhtml"); 
         }
         if(codicoFiltro == 4){
             FacesContext.getCurrentInstance().getExternalContext().redirect("imprimeCondicao.xhtml"); 
         }
     }
     
     
    
}
