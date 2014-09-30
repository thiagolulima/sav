
package View;

import Control.ProdutoEJB;
import Model.ProdutoKit;
import Model.ProdutoUnico;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


@javax.faces.bean.ManagedBean
@ViewScoped
public class ProdutoMB {
 
    ProdutoUnico produto = new ProdutoUnico();
    ProdutoKit  kit = new ProdutoKit();
    int tipoPesquisa = 0;
    String pesquisa = "";
    @EJB
    ProdutoEJB ejbProduto;
    public ProdutoMB() {
    }

    public ProdutoUnico getProduto() {
        return produto;
    }

    public void setProduto(ProdutoUnico produto) {
        this.produto = produto;
    }

    public ProdutoKit getKit() {
        return kit;
    }

    public void setKit(ProdutoKit kit) {
        this.kit = kit;
    }

    public int getTipoPesquisa() {
        return tipoPesquisa;
    }

    public void setTipoPesquisa(int tipoPesquisa) {
        this.tipoPesquisa = tipoPesquisa;
    }

    public String getPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
    }
    
    public void incluiProduto() throws Exception{
        try{
            ejbProduto.incluiProduto(produto);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Produto Mantido Com Sucesso"); 
            produto = new ProdutoUnico();
        }catch  (Exception erro)
        {
           adicionarMensagem(FacesMessage.SEVERITY_ERROR, erro.getMessage()); 
        }
    }
    public void incluiProdutoKit() throws Exception{
        try{
            ejbProduto.incluiProduto(produto);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Produto Mantido Com Sucesso"); 
            kit = new ProdutoKit();
        }catch  (Exception erro)
        {
           adicionarMensagem(FacesMessage.SEVERITY_ERROR, erro.getMessage()); 
        }
    }
    public void alteraProduto() throws Exception{
        try{
            ejbProduto.alteraProduto(produto);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Produto Mantido Com Sucesso"); 
            produto = new ProdutoUnico();
        }catch  (Exception erro)
        {
           adicionarMensagem(FacesMessage.SEVERITY_ERROR, erro.getMessage()); 
        }
    }
    public void excluiProduto() throws Exception{
        try{
             ejbProduto.excluiProduto(produto);
             adicionarMensagem(FacesMessage.SEVERITY_INFO, "Produto Exluido Com Sucesso"); 
           }catch (Exception erro)
           {
              adicionarMensagem(FacesMessage.SEVERITY_ERROR, erro.getMessage());   
           }
    }
    public List<ProdutoUnico> finByAllProduto () throws Exception{
        return ejbProduto.findByAllProdutoUnico(tipoPesquisa, pesquisa);
    }
    public List<ProdutoKit> finByAllProdutoKit (){
        return ejbProduto.findByAllProdutoKit();
    }
    public void setaProdutoExclusao(ProdutoUnico produto){
        this.produto = produto ;
    }
     public String mensagemConfirmaExclusaoProduto(){
        
        return "\n" + produto.getCodProduto() + " ?";
    }
     private void adicionarMensagem(FacesMessage.Severity severidade, String msg){
           FacesContext facesContext = FacesContext.getCurrentInstance();
           facesContext.addMessage(null,
           new FacesMessage(severidade, msg, null));
     }
    public void resetaProduto(){
        this.produto = new ProdutoUnico();
    }
    public void resetaProdutoKit(){
        this.kit = new ProdutoKit();
    }
}
