
package View;

import Control.FuncionarioEJB;
import Model.Endereco;
import Model.Funcionario;
import Model.Telefones;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


@ManagedBean
@ViewScoped
public class FuncionarioMB {

    private Funcionario funcionario = new Funcionario();
    private Endereco endereco = new Endereco();
    Telefones telefone = new Telefones();
    @EJB
    FuncionarioEJB ejb;
    public FuncionarioMB() {
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    public Telefones getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefones telefone) {
        this.telefone = telefone;
    }

    
    public void manterFuncionario(){
       try{
           funcionario.setEndereco(endereco);
           ejb.incluiFuncionario(funcionario);
           adicionarMensagem(FacesMessage.SEVERITY_INFO, "Funcionario Mantido Com Sucesso");
           funcionario = new Funcionario();
           endereco = new Endereco();
           telefone = new Telefones();
        }catch (Exception erro){
            adicionarMensagem(FacesMessage.SEVERITY_ERROR, erro.getMessage()); 
        }
    }
    private void adicionarMensagem(FacesMessage.Severity severidade, String msg){
           FacesContext facesContext = FacesContext.getCurrentInstance();
           facesContext.addMessage(null,
           new FacesMessage(severidade, msg, null));
     }
    public List<Funcionario> findByAllFuncionarios(){
        return ejb.findAllFuncionario();
    }
    public void adicionaEnderecoPraEdicao(){
        this.endereco = funcionario.getEndereco();
    }
     public void adicionaTelefone(){
        if(!telefone.getNumeroTel().trim().equals(""))
        {
           funcionario.getTelefones().add(telefone);
           telefone = new Telefones();
        }
    }
    
}
