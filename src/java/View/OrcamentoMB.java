
package View;

import Control.OrcamentoEJB;
import Model.ItemDeVenda;
import Model.Orcamento;
import Model.PreCadastroCliente;
import Model.Produto;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;



@javax.faces.bean.ManagedBean
@ViewScoped
public class OrcamentoMB {
    
    private Orcamento orcamento = new Orcamento();
    private ItemDeVenda item = new ItemDeVenda();
    private List<ItemDeVenda> items = new ArrayList<ItemDeVenda>();
    private PreCadastroCliente preCadastro = new PreCadastroCliente();
    private int quantidade = 1 ;
    private boolean habilitaCadastro = false;
    Date data  = new Date();
    @EJB
    OrcamentoEJB orcamentoEJB;
    @Inject
    usuarioMB funcionario;
    @Inject
    ImpressaoMB imprime;
    public OrcamentoMB() {
    }
    public Orcamento incluiOrcamento(Orcamento orcamento)
    {
        return orcamentoEJB.incluiOrcamento(orcamento);
    }
    public void removeOrcamento(Orcamento orcamento){
        orcamentoEJB.removeOrcamento(orcamento);
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public ItemDeVenda getItem() {
        return item;
    }

    public void setItem(ItemDeVenda item) {
        this.item = item;
    }
    
    public List<ItemDeVenda> getItems() {
        return items;
    }

    public void setItems(List<ItemDeVenda> items) {
        this.items = items;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public boolean isHabilitaCadastro() {
        return habilitaCadastro;
    }
    
    public void setHabilitaCadastro(boolean habilitaCadastro) {
        if(habilitaCadastro == true){
        this.habilitaCadastro = habilitaCadastro;
        orcamento.setPessoa(null);
        }else{
        this.habilitaCadastro = habilitaCadastro;
        }
    }

    public PreCadastroCliente getPreCadastro() {
        return preCadastro;
    }

    public void setPreCadastro(PreCadastroCliente preCadastro) {
        this.preCadastro = preCadastro;
    }
     public List<Orcamento> findByAllOrcamentosBloqueados(){
        return orcamentoEJB.findByAllOrcamentoBloqueados();
    }
    public Date retornaDataOrcamento(){
         if(orcamento.getDataOrcamento() == null)
         {
             orcamento.setDataOrcamento(data);
             return orcamento.getDataOrcamento();
         }
         else {
             return orcamento.getDataOrcamento();
         }
    }
        public void novoOrcamento(){
        orcamento = new Orcamento();
        quantidade = 1;
        item = new ItemDeVenda();
        items = new ArrayList<ItemDeVenda>();
        preCadastro = new PreCadastroCliente();
    }
      public List<Orcamento> findByAllOrcamentos() throws IOException {
        funcionario.verificaFuncionarioAtivo();
        return orcamentoEJB.findOrcamentosNaoVencidosPorFuncionario(funcionario.getFuncionario());
    }
      public void setaItensECadastroEdicao(Orcamento orcamento){
          this.items = orcamento.getProdutos();
          this.preCadastro = orcamento.getPreCadastroCliente();
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
    public String valorTotalCompra(){
        this.orcamento.setProdutos(items);
        return orcamento.retornaTotalCompra();
    }
     public void salvaOrcamento() {
        try {
               if(orcamento.getPessoa()== null && habilitaCadastro == false)
               {
                   adicionarMensagem(FacesMessage.SEVERITY_ERROR,"Adicione o Cliente!"); 
               }else
               if(items.size()< 1){
                    adicionarMensagem(FacesMessage.SEVERITY_ERROR,"Lista sem Produtos!"); 
               }else{
                     orcamento.setProdutos(items);
                     if(orcamento.getId() != null)
                     {
                      orcamentoEJB.atualizaItens(items);
                     }
                     if(habilitaCadastro == true){
                          orcamento.setPreCadastroCliente(preCadastro);
                      }
                      funcionario.verificaFuncionarioAtivo();
                      orcamento.setFuncionario(funcionario.getFuncionario());
                      orcamento = orcamentoEJB.salvaOrcamento(orcamento);              
                      adicionarMensagem(FacesMessage.SEVERITY_INFO ,"Orcamento Mantido com Sucesso!"); 
               }
         }catch (Exception ex)
            {
              adicionarMensagem(FacesMessage.SEVERITY_ERROR, ex.getMessage()); 
            }
     }
     private void adicionarMensagem(FacesMessage.Severity severidade, String msg){
           FacesContext facesContext = FacesContext.getCurrentInstance();
           facesContext.addMessage(null,
           new FacesMessage(severidade, msg, null));
     }
    public void autorizaOrcamento() throws Exception{
        if(orcamento.getPessoa()== null && habilitaCadastro == false)
               {
                   adicionarMensagem(FacesMessage.SEVERITY_ERROR,"Adicione o Cliente!"); 
               }else
               if(items.size()< 1){
                    adicionarMensagem(FacesMessage.SEVERITY_ERROR,"Lista sem Produtos!"); 
               }
               else{
                   orcamentoEJB.zeraNaoAutorizados(items);
                   orcamento.setBloqueio(false);
                   orcamento = orcamentoEJB.incluiOrcamento(orcamento);
                   novoOrcamento();
                   adicionarMensagem(FacesMessage.SEVERITY_INFO ,"Orcamento Mantido com Sucesso!"); 
                 }
    }
    public String imprimir() throws IOException{
        orcamento.setProdutos(items);
        imprime.setOrcamento(orcamento);
       return "javascript:imprimirOrc()" ;
    }
    public boolean habilitaImpressao(){
        if(orcamento.getId()== null || orcamento.isBloqueio() == true){
            return true;
        }
        else{
            return false;
        }
    }
}
