

package View;


import Control.UsersEJB;
import Model.Funcionario;
import Model.Pessoa;
import java.io.IOException;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


@ManagedBean
@SessionScoped
public class usuarioMB  {

    private Pessoa pessoa = new Pessoa();
    private Funcionario funcionario = new Funcionario();
    @EJB
    UsersEJB ejb;
    public usuarioMB() {
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    public String retornaNomeLogado(){
        String nome = ((SecurityContext) SecurityContextHolder.getContext()).
                             getAuthentication().getName();
         if (nome.equals( "anonymousUser" ))
         {
             nome = "";
             return nome;
         }
         else{
             return nome;
         }
    }
    public void setaFuncionarioNaSessao() throws IOException{
        try  {
                    pessoa = 
                             ejb.retornaPessoaParaSessao(((SecurityContext) SecurityContextHolder.getContext()).
                             getAuthentication().getName()) ;
                    verificaFuncionarioAtivo();
                    if(pessoa.getUsuario().getAuthority().equals("ROLE_CLIENTE"))
                    {
                        FacesContext.getCurrentInstance().getExternalContext().redirect("../cliente");
                    }
                    else{
                        FacesContext.getCurrentInstance().getExternalContext().redirect("../intranet");
                    }
             }catch (Exception ex) {
                 pessoa = new Pessoa();
               FacesContext.getCurrentInstance().getExternalContext().redirect("./login.xhtml");
             } 
    }
    public void verificaFuncionarioAtivo() throws IOException{
        try  {
                   if(funcionario.getId() == null)
                   {
                    funcionario = 
                             ejb.retornaFuncionarioParaSessao(((SecurityContext) SecurityContextHolder.getContext()).
                             getAuthentication().getName()) ;
                   }
                   if(funcionario.isFuncionarioAtivo() == false)
                   {
                     FacesContext.getCurrentInstance().getExternalContext().redirect("../logout");   
                   }
                  
                  
             }catch (Exception ex) {
                 pessoa = new Pessoa();
               FacesContext.getCurrentInstance().getExternalContext().redirect("./login.xhtml");
             } 
    }
  

    
}
