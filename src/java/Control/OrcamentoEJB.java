
package Control;

import Model.Funcionario;
import Model.ItemDeVenda;
import Model.Orcamento;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class OrcamentoEJB {

       @PersistenceContext
        EntityManager em;
       public Orcamento incluiOrcamento(Orcamento orcamento)
       { 
           em.merge(orcamento);
           return orcamento;
       }
       public void removeOrcamento(Orcamento orcamento){
               
               orcamento = em.merge(orcamento);
               em.remove(orcamento);
        }
       public List<Orcamento> findOrcamentosNaoVencidosPorFuncionario(Funcionario funcionario){
                  Date data = new Date();
                  List<Orcamento> lista = em.createQuery("Select u From Orcamento u WHERE u.funcionario.id =:id AND u.dataValidade >=:data ORDER BY u.dataValidade ASC")
                     .setParameter("id", funcionario.getId()).setParameter("data", data).getResultList();
             if (lista.size() <1){
                    return lista = new ArrayList<Orcamento>();
                }
            
                return lista;
       }
       public void atualizaItens(List<ItemDeVenda> itens){
           for(ItemDeVenda item : itens){
               if(item.getId() != null){
                 em.merge(item.getDesconto());
               }
          }
       }
       public Orcamento salvaOrcamento(Orcamento orcamento) throws Exception
        {
            for(ItemDeVenda item : orcamento.getProdutos())
              {
                     if(item.getDesconto().getValorDesconto() > item.getDesconto().getValorDescontoAutorizado())
                     {
                         orcamento.setBloqueio(true);
                     }
              }
              orcamento =  em.merge(orcamento);
              if(orcamento.isBloqueio() == true){
                  
                 throw new Exception ("Orcamento Bloqueado!");
                 
                }
              return orcamento; 
         }
         public void zeraNaoAutorizados(List<ItemDeVenda> itens){
               for(ItemDeVenda item : itens){
               if(item.getDesconto().getValorDescontoAutorizado() < item.getDesconto().getValorDesconto())
               {
                   item.getDesconto().setValorDesconto(item.getDesconto().getValorDescontoAutorizado());
                   item.atualizaDescontoValorPorcentagem();
               }
               em.merge(item.getDesconto());
           }
        }
        public List<Orcamento> findByAllOrcamentoBloqueados(){
            
             List<Orcamento> lista = em.createQuery("Select u From Orcamento u WHERE u.bloqueio = 1 ")
                     .getResultList();
             if (lista.size() <1){
                    return lista = new ArrayList<Orcamento>();
                }
            
                return lista;
        }
        public int dataDiff(java.util.Date dataLow, java.util.Date dataHigh){  
  
                    GregorianCalendar startTime = new GregorianCalendar();  
                    GregorianCalendar endTime = new GregorianCalendar();  
       
                    GregorianCalendar curTime = new GregorianCalendar();  
                    GregorianCalendar baseTime = new GregorianCalendar();  
                    startTime.setTime(dataLow);  
                    endTime.setTime(dataHigh);  
                    int dif_multiplier = 1;  
                   // Verifica a ordem de inicio das datas  
                 if( dataLow.compareTo( dataHigh ) < 0 ){  
                    baseTime.setTime(dataHigh);  
                    curTime.setTime(dataLow);  
                    dif_multiplier = 1;  
                }else{  
                     baseTime.setTime(dataLow);  
                     curTime.setTime(dataHigh);  
                     dif_multiplier = -1;  
                 }  
                 int result_years = 0;  
                 int result_months = 0;  
                 int result_days = 0;  
     // Para cada mes e ano, vai de mes em mes pegar o ultimo dia para import acumulando  
     // no total de dias. Ja leva em consideracao ano bissesto  
                 while( curTime.get(GregorianCalendar.YEAR) < baseTime.get(GregorianCalendar.YEAR) ||  
                        curTime.get(GregorianCalendar.MONTH) < baseTime.get(GregorianCalendar.MONTH)  )  
                    {  
           
                      int max_day = curTime.getActualMaximum( GregorianCalendar.DAY_OF_MONTH );  
                      result_months += max_day;  
                      curTime.add(GregorianCalendar.MONTH, 1);  
                     }  
      // Marca que é um saldo negativo ou positivo  
                   result_months = result_months*dif_multiplier;  
       
       
     // Retirna a diferenca de dias do total dos meses  
                  result_days += (endTime.get(GregorianCalendar.DAY_OF_MONTH) - startTime.get(GregorianCalendar.DAY_OF_MONTH));  
                  System.out.println(result_years+result_months+result_days);
                  return result_years+result_months+result_days;  
     }

}
