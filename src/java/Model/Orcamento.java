
package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;


@Entity
public class Orcamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataOrcamento ;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataValidade ;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ItemDeVenda> produtos = new ArrayList<ItemDeVenda>() ;
    @ManyToOne
    private Pessoa pessoa;
    @ManyToOne
    private CondicaoPagamento condicaoPagamento ;
    @ManyToOne
    private Funcionario funcionario;
    @ManyToOne
    private PreCadastroCliente preCadastroCliente;
    private double entrada;
    private boolean bloqueio = false;
   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataOrcamento() {
        return dataOrcamento;
    }

    public void setDataOrcamento(Date dataOrcamento) {
        this.dataOrcamento = dataOrcamento;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public List<ItemDeVenda> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ItemDeVenda> produtos) {
        this.produtos = produtos;
    }
    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public CondicaoPagamento getCondicaoPagamento() {
        return condicaoPagamento;
    }

    public void setCondicaoPagamento(CondicaoPagamento condicaoPagamento) {
        this.condicaoPagamento = condicaoPagamento;
    }
    public Funcionario getFuncionario() {
        return funcionario;
    }

    public double getEntrada() {
        return entrada;
    }

    public void setEntrada(double entrada) {
        this.entrada = entrada;
    }
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public boolean isBloqueio() {
        return bloqueio;
    }

    public void setBloqueio(boolean bloqueio) {
        this.bloqueio = bloqueio;
    }

    public PreCadastroCliente getPreCadastroCliente() {
        return preCadastroCliente;
    }

    public void setPreCadastroCliente(PreCadastroCliente preCadastroCliente) {
        this.preCadastroCliente = preCadastroCliente;
    }
    
    public String retornaTotalCompra(){
        Double total = 0.0 ;
        for (ItemDeVenda item :produtos )
        {
           total = total + item.retornaValorTotalItemComDesconto();
        }
        String formatString = String.format("%.2f", total)  ;
        return formatString ;
    }
    public double retornaValorParcela(){
        return Double.parseDouble(retornaTotalCompra())/condicaoPagamento.retornaQuantidadeParcelas();
    }
    public boolean verificaPrazoZero(){
      if(condicaoPagamento != null){
        for(Parcela parcela : condicaoPagamento.getParcelas())
        {
            if(parcela.getPrazo() == 0 && condicaoPagamento.getParcelas().size() > 1){
                return true;
            }
        }
      }
        return false;
    }
    public void autorizaTodosOrcamentos(Funcionario funcionario){
         Date dataLiberacao = new Date();
        for(ItemDeVenda item : produtos){
             item.getDesconto().setDataLiberacao(dataLiberacao);
             item.getDesconto().setFuncionarioAutorizou(funcionario);
             item.getDesconto().setValorDescontoAutorizado(item.getDesconto().getValorDesconto());
        }
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orcamento)) {
            return false;
        }
        Orcamento other = (Orcamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Orcamento[ id=" + id + " ]";
    }
    
}
