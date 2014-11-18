
package View;

import Control.PedidoEJB;
import Model.PedidoVenda;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;


/**
 *
 * @author THIAGO
 */

@ManagedBean
@SessionScoped
public class relatorioMB {

    private Date dataInicial = new Date();
    private Date dataFinal = new Date();
    private int pesquisa = 0;
    private int controlerPesquisa = 1;
    @EJB
    PedidoEJB pedidoEJB;
    @Inject
    usuarioMB user;
    public relatorioMB() {
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public int getPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(int pesquisa) {
        this.pesquisa = pesquisa;
    }
    public int getControlerPesquisa() {
        return controlerPesquisa;
    }

    public void setControlerPesquisa(int controlerPesquisa) {
        this.controlerPesquisa = controlerPesquisa;
    }
    public List<PedidoVenda> findPedidosVenda() throws IOException
    {
        user.verificaCliente();
        if(controlerPesquisa == 2){
          return  pedidoEJB.findByAllPedidosData(dataInicial, dataFinal, user.getPessoa());
        }
        else{
            return pedidoEJB.findByAllPedidosClientePesquisa(user.getPessoa(), pesquisa);
        }
    }
}
