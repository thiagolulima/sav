
package View;

import Control.UsersEJB;
import Model.Pessoa;
import Model.Users;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


@javax.faces.bean.ManagedBean
@ViewScoped
public class UsersMB {

    Pessoa pessoa  = new Pessoa();
    Users user = new Users();
    public String senha;
    public String confirmaSenha ;
    @EJB
    UsersEJB ejb;
    public UsersMB() {
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String Senha) {
        this.senha = Senha;
    }
    
    public List<Pessoa> findByUsers(){
        return ejb.findByUsuarios();
    }
    public List<Pessoa> findByUsersTodos(){
        return ejb.findByTodosUsuarios();
    }

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }
    public void incluiUsuario(){
        if(confirmaSenha.trim().equals("") && senha.trim().equals("") ){
            adicionarMensagem(FacesMessage.SEVERITY_ERROR, "campo de senha em branco"); 
        }else{
              if(pessoa.getUsuario().getPassword().equals(confirmaSenha))
               {
                ejb.incluiUsuario(pessoa);
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Usuario incluido com sucesso!"); 
                pessoa = new Pessoa();
               }
             else
             {
               adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Senha ou Confirmacao incorreta"); 
             }
         }
    }
    public void edicaoUser(){
          if(confirmaSenha.trim().equals("") && senha.trim().equals("") ){
            ejb.incluiUsuario(pessoa);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Usuario Mantido com sucesso!"); 
         }else{
              if(senha.equals(confirmaSenha))
               {
                pessoa.getUsuario().setPassword(senha);
                ejb.incluiUsuario(pessoa);
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Usuario Mantido com sucesso!"); 
                pessoa = new Pessoa();
               }
             else
             {
               adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Senha ou Confirmacao incorreta"); 
             }
        }
    }
    public void incluiUserEdicao(Pessoa pessoa){
          ejb.incluiUsuario(pessoa);
    }
    public void setaPessoaParaEdicao(Pessoa pessoa){
        this.pessoa = new Pessoa();
        this.pessoa = pessoa;
        if(pessoa.getUsuario()== null)
        {
            this.user = new Users();
            this.user.setUsername(pessoa.getEmail());
            this.pessoa.setUsuario(user);
        }
        else
        {
           this.pessoa.getUsuario().setUsername(pessoa.getEmail());
           confirmaSenha = this.pessoa.getUsuario().getPassword();
        }
    }
     private void adicionarMensagem(FacesMessage.Severity severidade, String msg)
    {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      facesContext.addMessage(null,
      new FacesMessage(severidade, msg, null));
    }
     public void reseta(){
         this.pessoa = new Pessoa();
         this.user = new Users();
     }
}
