/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.CategoriaEJB;
import Model.Categoria;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


@javax.faces.bean.ManagedBean
@ViewScoped
public class CategoriaMB {

    Categoria categoria = new Categoria();
    
    @EJB
    CategoriaEJB ejb;
    
    public CategoriaMB() {
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    public void incluiCategoria() throws Exception{
        try{
               ejb.incluiCategoria(categoria);
               adicionarMensagem(FacesMessage.SEVERITY_INFO,"Categoria Incluida com Sucesso!");  
               categoria = new Categoria();
           }catch (Exception erro)
           {
                 adicionarMensagem(FacesMessage.SEVERITY_ERROR,erro.getMessage());
           }
                     
    }
    public void excluiCategoria(){
        try{
             ejb.excluiCategoria(categoria);
         }catch (Exception erro)
        {
            
           adicionarMensagem(FacesMessage.SEVERITY_ERROR, erro.getMessage()); 
        }
    }
    public List<Categoria> findByAllCategoria(){
           return ejb.findByAllCategoria();
    }
    private void adicionarMensagem(FacesMessage.Severity severidade, String msg)
          {
           FacesContext facesContext = FacesContext.getCurrentInstance();
           facesContext.addMessage(null,
           new FacesMessage(severidade, msg, null));
    }
    public String mensagemConfirmaExclusaoCategoria(){
        
        return "\n" + categoria.getDescricao() + " ?";
    }
    public void resetaCategoria(){
        categoria = new Categoria();
    }
    public void setaCategoriaExclusao(Categoria categoria){
        this.categoria = categoria;
    }
}
