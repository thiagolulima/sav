/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.PedidoEJB;
import Model.ItemDeVenda;
import Model.Orcamento;
import Model.PedidoVenda;
import Model.Produto;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;



@ManagedBean
@ViewScoped
public class PedidoMB {
    
    private PedidoVenda pedido = new PedidoVenda();
    private Orcamento orcamento = new Orcamento();
    private ItemDeVenda item = new ItemDeVenda();
    private List<ItemDeVenda> items = new ArrayList<ItemDeVenda>();
    
    
    int quantidade = 1 ;
    Date data  = new Date();
    @EJB
    PedidoEJB pedidoEJB;
    @Inject
    OrcamentoMB orcamentoMB ;
    @Inject
    usuarioMB funcionario;
    public PedidoMB() {
        
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

    public PedidoEJB getPedidoEJB() {
        return pedidoEJB;
    }

    public void setPedidoEJB(PedidoEJB pedidoEJB) {
        this.pedidoEJB = pedidoEJB;
    }
    public Date retornaDataPedido(){
         if(pedido.getDataPedido()== null)
         {
             pedido.setDataPedido(data);
             return pedido.getDataPedido();
         }
         else {
             return pedido.getDataPedido();
         }
    }
    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public List<ItemDeVenda> getItems() {
        return items;
    }

    public void setItems(List<ItemDeVenda> items) {
        this.items = items;
    }
    
    public void selecionaProduto(Produto produto)
    {
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setValorPago(produto.getValor());
        item.setOrcamento(orcamento);
        items.add(item);
        item = new ItemDeVenda();
    }
    public void gravaOrcamento(){
      orcamento =  orcamentoMB.incluiOrcamento(orcamento);
    }
    public void salvaPedido(){
        try {
               if(orcamento.getPessoa()== null)
               {
                   adicionarMensagem(FacesMessage.SEVERITY_ERROR,"Adicione o Cliente!"); 
               }else
               if(items.size()< 1){
                    adicionarMensagem(FacesMessage.SEVERITY_ERROR,"Lista sem Produtos!"); 
               }else{
                     orcamento.setProdutos(items);
                     if(pedido.getId() != null)
                     {
                      pedidoEJB.incluiOrcamento(orcamento);
                      pedidoEJB.atualizaItens(items);
                     }
                      pedido.setOrcamento(orcamento);
                      funcionario.verificaFuncionarioAtivo();
                      pedido.getOrcamento().setFuncionario(funcionario.getFuncionario());
                      pedido = pedidoEJB.incluiPedido(pedido);
                      this.orcamento = pedido.getOrcamento();
                      adicionarMensagem(FacesMessage.SEVERITY_INFO ,"Pedido Mantido com Sucesso!"); 
               }
            }catch (Exception ex)
            {
             adicionarMensagem(FacesMessage.SEVERITY_ERROR, ex.getMessage()); 
            }
    }
    public void autorizaPedido() throws Exception{
        if(orcamento.getPessoa()== null)
               {
                   adicionarMensagem(FacesMessage.SEVERITY_ERROR,"Adicione o Cliente!"); 
               }else
               if(items.size()< 1){
                    adicionarMensagem(FacesMessage.SEVERITY_ERROR,"Lista sem Produtos!"); 
               }
               else{
                   pedidoEJB.zeraNaoAutorizados(items);
                   orcamento.setBloqueio(false);
                   pedidoEJB.incluiOrcamento(orcamento);
                   pedido = pedidoEJB.incluiPedido(pedido);
                   novoPedido();
                   adicionarMensagem(FacesMessage.SEVERITY_INFO ,"Pedido Mantido com Sucesso!"); 
               }
    }
    public List<PedidoVenda> findByAllPedidos() throws IOException{
        funcionario.verificaFuncionarioAtivo();
        return pedidoEJB.findByAllPedidos(funcionario.getFuncionario());
    }
    public List<PedidoVenda> findByAllPedidosBloqueados(){
        return pedidoEJB.findByAllPedidosBloqueados();
    }
    public void removePedido(PedidoVenda pedido){
        orcamento = pedido.getOrcamento();
        pedidoEJB.removePedido(pedido);
        pedidoEJB.removeOrcamento(orcamento);
    }
    private void adicionarMensagem(FacesMessage.Severity severidade, String msg){
           FacesContext facesContext = FacesContext.getCurrentInstance();
           facesContext.addMessage(null,
           new FacesMessage(severidade, msg, null));
     }
    public void setaOrcamento(Orcamento orcamento){
        this.orcamento = orcamento;
        items = orcamento.getProdutos();
    }
    public void novoPedido(){
        pedido = new PedidoVenda();
        orcamento = new Orcamento();
        quantidade = 1;
        item = new ItemDeVenda();
        items = new ArrayList<ItemDeVenda>();
    }
    public String valorTotalCompra(){
        this.orcamento.setProdutos(items);
        return orcamento.retornaTotalCompra();
    }
    
   
}
