package br.com.casadocodigo.loja.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class CarrinhoCompras implements Serializable{

    
	private static final long serialVersionUID = -5079866118840158714L;
	private Set<CarrinhoItem> itens = new HashSet<>();

    public void add(CarrinhoItem item) {
        itens.add(item);
    }

	public List<CarrinhoItem> getItens() {
		return new ArrayList<CarrinhoItem>(itens);
	}
	
	public BigDecimal getTotal(CarrinhoItem carrinhoItem) {
		
		return carrinhoItem.getLivro().getPreco().multiply(new BigDecimal(carrinhoItem.getQuantidade()));
	
	}
	
	public BigDecimal getTotal() {
		BigDecimal total = BigDecimal.ZERO;
		for (CarrinhoItem carrinhoItem : itens) {
			total = total.add(carrinhoItem.getLivro().getPreco().multiply(new BigDecimal(carrinhoItem.getQuantidade())));
		}
		return total;		
		
	}

}