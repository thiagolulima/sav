
package Model;

import javax.persistence.Entity;



@Entity

public class Funcionario extends Fisica {
    
    private boolean funcionarioAtivo;

    public boolean isFuncionarioAtivo() {
        return funcionarioAtivo;
    }

    public void setFuncionarioAtivo(boolean funcionarioAtivo) {
        this.funcionarioAtivo = funcionarioAtivo;
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
        if (!(object instanceof Funcionario)) {
            return false;
        }
        Funcionario other = (Funcionario) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Funcionario[ id=" + getId() + " ]";
    }
    
}
