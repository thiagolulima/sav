
package Model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class ItemDeVenda implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Produto produto;
    @ManyToOne
    private Orcamento orcamento;
    @ManyToOne
    private Desconto desconto;
    private int quantidade;
    private double valorPago;
    
    public ItemDeVenda (){
        desconto = new Desconto();
        atualizaDescontoValorPorcentagem();
        atualizaDescontoValor();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }
    public Double retornaValorTotalItem(){
        return quantidade * valorPago;
    }

   
    public double retornaValorTotalItemComDesconto(){
        return (quantidade * valorPago) - desconto.getValorDesconto();
    }
   
    public Desconto getDesconto() {
        return desconto;
    }

    public void setDesconto(Desconto desconto) {
        this.desconto = desconto;
    }
    public final void atualizaDescontoValorPorcentagem(){
        if(valorPago > 0){
         double valorComDesconto = valorPago - desconto.getValorDesconto() ;
         double porcentagem = (valorComDesconto / valorPago) * 100;
         double porcentagemReal = 100 - porcentagem;
         String formatString = String.format("%.2f", porcentagemReal)  ;
         System.out.println(formatString);
         String replace = formatString.replace("," , "."); 
         desconto.setPorcentagemDesconto(Double.parseDouble(replace));
        }
    }
    public final void atualizaDescontoValor(){
        if(valorPago > 0){
           double valorDesconto =  desconto.getPorcentagemDesconto()/100  * valorPago ;
           String formatString = String.format("%.2f", valorDesconto)  ;
           String replace = formatString.replace("," , "."); 
           desconto.setValorDesconto(Double.parseDouble(replace));
        }
    }
    public void autorizaOrcamento(Funcionario funcionario){
        Date dataLiberacao = new Date();
        desconto.setDataLiberacao(dataLiberacao);
        desconto.setFuncionarioAutorizou(funcionario);
        desconto.setValorDescontoAutorizado(desconto.getValorDesconto());
    }
    public boolean verificaItemSemDesconto(){
        if(desconto.getValorDescontoAutorizado() < desconto.getValorDesconto())
          {
            return false;
          }
        else{
            return true;
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
        if (!(object instanceof ItemDeVenda)) {
            return false;
        }
        ItemDeVenda other = (ItemDeVenda) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.ItemDeVenda[ id=" + id + " ]";
    }
    
}
