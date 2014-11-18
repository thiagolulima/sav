
package View;

import Control.ClienteEJB;
import Model.Endereco;
import Model.Estado;
import Model.Fisica;
import Model.Juridica;
import Model.PreCadastroCliente;
import Model.Telefones;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;



@javax.faces.bean.ManagedBean
@ViewScoped
public class clienteMB {

    private Fisica fisica = new Fisica();
    private Juridica juridica = new Juridica();
    private Endereco endereco = new Endereco();
    private Telefones telefone = new Telefones();
    private PreCadastroCliente preCadastro = new PreCadastroCliente();
    int tipoPesquisa = 0;
    int idPreCadastro = 0;
    String pesquisa = "";
    @EJB
    ClienteEJB ejbCliente;
    @Inject
    enderecoMB enderecoMB;
    @Inject
    PreCadastroMB preCadastroCliente;
    public clienteMB() {
    }

    public Fisica getFisica() {
        return fisica;
    }

    public void setFisica(Fisica fisica) {
        this.fisica = fisica;
    }

    public Juridica getJuridica() {
        return juridica;
    }

    public void setJuridica(Juridica juridica) {
        this.juridica = juridica;
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

    public int getIdPreCadastro() {
        return idPreCadastro;
    }

    public void setIdPreCadastro(int idPreCadastro) {
        this.idPreCadastro = idPreCadastro;
    }
    
    public void incluiCliente(){
        try{
            fisica.setEndereco(endereco);
            ejbCliente.verificaSeACadastradocpf(fisica);
            FacesMessage msg = new FacesMessage("Cliente incluido com sucesso ");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            fisica = new Fisica();
            endereco = new Endereco();
           }catch (Exception erro){
             adicionarMensagem(FacesMessage.SEVERITY_ERROR, erro.getMessage()); 
           }  
        
    }
    public void incluiClienteJuridica(){
        try{
            juridica.setEndereco(endereco);
            ejbCliente.verificaSeACadastradoCnpj(juridica);
            FacesMessage msg = new FacesMessage("Cliente incluido com sucesso ");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            juridica = new Juridica();
            endereco = new Endereco();
           }catch (Exception erro){
             adicionarMensagem(FacesMessage.SEVERITY_ERROR, erro.getMessage()); 
           }  
        
    }
     public void excluiClienteFisica(){
        try{
           ejbCliente.excluiCliente(fisica);
           enderecoMB.excluiEndereco(fisica.getEndereco());
           FacesMessage msg = new FacesMessage("Cliente excluido com sucesso ");
           FacesContext.getCurrentInstance().addMessage(null, msg);
         }catch(Exception ex){    
            adicionarMensagem(FacesMessage.SEVERITY_ERROR, "não é permitido excluir um cliente que ja tenha sido utilizado");  
        }   
    }
     public void excluiClienteJuridica(){
        try{
           ejbCliente.excluiCliente(juridica);
           enderecoMB.excluiEndereco(juridica.getEndereco());
           FacesMessage msg = new FacesMessage("Cliente excluido com sucesso ");
           FacesContext.getCurrentInstance().addMessage(null, msg);
         }catch(Exception ex){    
            adicionarMensagem(FacesMessage.SEVERITY_ERROR, "não é permitido excluir um cliente que ja tenha sido utilizado");  
        }   
    }
     public void editaClienteFisica() throws Exception{
        try 
        {
          enderecoMB.atualizaEndereco(endereco);
          ejbCliente.incluiCliente(fisica);
          FacesMessage msg = new FacesMessage("Cliente alterado com sucesso ");
          FacesContext.getCurrentInstance().addMessage(null, msg);
          fisica = new Fisica();
          endereco = new Endereco();
        }catch (Exception erro)
        {
           adicionarMensagem(FacesMessage.SEVERITY_ERROR, erro.getMessage());   
        }
        
    }
     public void editaClienteJuridica() throws Exception{
       try 
        {   
         enderecoMB.atualizaEndereco(endereco);
         ejbCliente.incluiCliente(juridica);
         FacesMessage msg = new FacesMessage("Cliente alterado com sucesso ");
         FacesContext.getCurrentInstance().addMessage(null, msg);
         juridica = new Juridica();
         endereco = new Endereco();
         }catch (Exception erro)
        {
           adicionarMensagem(FacesMessage.SEVERITY_ERROR, erro.getMessage());   
        }
    }
    public List<Fisica> findByAllClienteFisica() throws Exception{
        try{
             return ejbCliente.findAllClienteFisica(tipoPesquisa , pesquisa);
           }
        catch (Exception e)
          {
            adicionarMensagem(FacesMessage.SEVERITY_ERROR, e.getMessage());  
          }
        return new ArrayList<Fisica>();
    }
    public List<Juridica> findByAllClienteJuridica() throws Exception{
        try {
              return ejbCliente.findAllClienteJuridica(tipoPesquisa , pesquisa);
            }catch (Exception erro)
            {
                adicionarMensagem(FacesMessage.SEVERITY_ERROR, erro.getMessage()); 
            }
        return new ArrayList<Juridica>();
    }
    public void  adicionaEnderecoEstadoClienteParaEdicao(){
      endereco = new Endereco();
      endereco = fisica.getEndereco();
    }
    public void  adicionaEnderecoEstadoClientejParaEdicao(){
      endereco = new Endereco();
      endereco = juridica.getEndereco();
    }
     public void setaClienteFisicaParaExclusao(Fisica cliente){
        this.fisica =  cliente;
    }
     public void setaClienteJuridicaParaExclusao(Juridica cliente){
        this.juridica =  cliente;
    }
    public void adicionaTelefone(){
        if(!telefone.getNumeroTel().trim().equals(""))
        {
           fisica.getTelefones().add(telefone);
           telefone = new Telefones();
        }
    }
    public void adicionaTelefoneJuridica(){
        if(!telefone.getNumeroTel().trim().equals(""))
        {
           juridica.getTelefones().add(telefone);
           telefone = new Telefones();
        }
    }
    public void resetaCliente(){
        fisica = new Fisica();
        juridica = new Juridica();
        endereco = new Endereco();
        telefone = new Telefones();
    }
     public String mensagemConfirmaExclusaoFisica(){
        
        return "\n" + fisica.getNome() + " ?";
    }
     public String mensagemConfirmaExclusaoJuridica(){
        
        return "\n" + juridica.getNome() + " ?";
    }
    public void redirecionaClienteJuridica() throws IOException{
         
          FacesContext.getCurrentInstance().getExternalContext().redirect("clientej.xhtml");
     }
    public void redirecionaClienteFisica() throws IOException{
         
          FacesContext.getCurrentInstance().getExternalContext().redirect("cliente.xhtml");
     }
     private void adicionarMensagem(FacesMessage.Severity severidade, String msg)
    {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      facesContext.addMessage(null,
      new FacesMessage(severidade, msg, null));
    }
     public void buscaPreCadastroFisica(){
        preCadastro = preCadastroCliente.getPreCadastro(idPreCadastro);
        if(preCadastro != null){
           fisica.setNome(preCadastro.getNome());
           fisica.setEmail(preCadastro.getEmail());
           telefone = new Telefones();
           telefone.setNumeroTel(preCadastro.getTelefone());
           fisica.getTelefones().add(telefone);
           telefone = new Telefones();
           adicionarMensagem(FacesMessage.SEVERITY_INFO, "Pre Cadastro encontrado!"); 
        }
     }
     public void buscaPreCadastroJuridica(){
        preCadastro = preCadastroCliente.getPreCadastro(idPreCadastro);
        if(preCadastro != null){
           juridica.setNome(preCadastro.getNome());
           juridica.setEmail(preCadastro.getEmail());
           telefone = new Telefones();
           telefone.setNumeroTel(preCadastro.getTelefone());
           juridica.getTelefones().add(telefone);
           telefone = new Telefones();
           adicionarMensagem(FacesMessage.SEVERITY_INFO, "Pre Cadastro encontrado!"); 
        }
     }
}
