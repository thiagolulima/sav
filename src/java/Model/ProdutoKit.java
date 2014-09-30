

package Model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;


@Entity

public class ProdutoKit extends Produto {
  
    @OneToMany(cascade = CascadeType.ALL)
    List<Produto> produtos = new ArrayList<Produto>();
    
    @NotNull
    private int porcentagemDesconto ; 

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

   
   public void adicionaProduto (Produto produto){
       produtos.add(produto);
   }
    public int getPorcentagemDesconto() {
        return porcentagemDesconto;
    }

    public void setPorcentagemDesconto(int porcentagemDesconto) {
        this.porcentagemDesconto = porcentagemDesconto;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProdutoKit)) {
            return false;
        }
        ProdutoKit other = (ProdutoKit) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.ProdutoKit[ id=" + getId() + " ]";
    }

    @Override
    public String getDescricao() {
         descricao = "";
         for(Produto lista : produtos){
             descricao = lista.getDescricao() + " + " ;
         }
         return descricao;
    }

    @Override
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public double getValor() {
        valor = 0.0;
        Double totalDesconto = 0.0;
        for (Produto lista : produtos){
             valor = valor + lista.getValor();
        }
       totalDesconto = valor * (porcentagemDesconto / 100) ;
       return valor - totalDesconto; 
    }

    @Override
    public void setValor(double valor) {
        this.valor = valor;
    }
    
}
