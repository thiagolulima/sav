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
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;



@javax.faces.bean.ManagedBean
@ViewScoped
public class PedidoMB {
    
    PedidoVenda pedido = new PedidoVenda();
    Orcamento orcamento = new Orcamento();
    ItemDeVenda item = new ItemDeVenda();
    int quantidade = 1 ;
    Date data  = new Date();
    @EJB
    PedidoEJB pedidoEJB;
    @Inject
    OrcamentoMB orcamentoMB ;
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
    
    public void selecionaProduto(Produto produto)
    {
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setValorPago(produto.getValor());
        item.setOrcamento(orcamento);
        orcamento.getProdutos().add(item);
        item = new ItemDeVenda();
    }
    public void gravaOrcamento(){
      orcamento =  orcamentoMB.incluiOrcamento(orcamento);
    }
    public List<PedidoVenda> findByAllPedidos(){
        return pedidoEJB.findByAllPedidos();
    }
    
    
    
    
    
    
}
