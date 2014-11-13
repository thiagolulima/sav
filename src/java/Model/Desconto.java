
package Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.Transient;


@Entity
public class Desconto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Funcionario funcionarioAutorizou;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataLiberacao ;
    private double valorDesconto = 0 ;
    private double valorDescontoAutorizado = 0;
    private double porcentagemDesconto ; 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Funcionario getFuncionarioAutorizou() {
        return funcionarioAutorizou;
    }

    public void setFuncionarioAutorizou(Funcionario funcionarioAutorizou) {
        this.funcionarioAutorizou = funcionarioAutorizou;
    }

    public Date getDataLiberacao() {
        return dataLiberacao;
    }

    public void setDataLiberacao(Date dataLiberacao) {
        this.dataLiberacao = dataLiberacao;
    }

    public double getValorDesconto() {
       
        return valorDesconto;
    }

    public void setValorDesconto(double valorDesconto) {
       
        this.valorDesconto = valorDesconto;
    }

    public double getValorDescontoAutorizado() {
        return valorDescontoAutorizado;
    }

    public void setValorDescontoAutorizado(double valorDescontoAutorizado) {
        this.valorDescontoAutorizado = valorDescontoAutorizado;
    }

    public double getPorcentagemDesconto() {
        return porcentagemDesconto;
    }

    public void setPorcentagemDesconto(double porcentagemDesconto) {
        this.porcentagemDesconto = porcentagemDesconto;
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
        if (!(object instanceof Desconto)) {
            return false;
        }
        Desconto other = (Desconto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Desconto[ id=" + id + " ]";
    }
    
}
