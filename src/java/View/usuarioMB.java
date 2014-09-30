

package View;


import Control.UsersEJB;
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
    @EJB
    UsersEJB ejb;
    public usuarioMB() {
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    public void setaFuncionarioNaSessao() throws IOException{
        try  {
                    pessoa = 
                             ejb.retornaPessoaParaSessao(((SecurityContext) SecurityContextHolder.getContext()).
                             getAuthentication().getName()) ;
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

    
}
