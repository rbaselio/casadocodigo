package br.com.casadocodigo.loja.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;


import com.google.gson.Gson;


import br.com.casadocodigo.loja.daos.CompraDao;


@Named
@SessionScoped
public class CarrinhoCompras implements Serializable{

    
	private static final long serialVersionUID = -5079866118840158714L;
	private Set<CarrinhoItem> itens = new HashSet<>();
	

    @Inject
    private CompraDao compraDao;

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
	
	public Integer getQuantidadeTotal() {
		return itens.stream().mapToInt(item -> item.getQuantidade()).sum();
		
		
	}


	public void remover(CarrinhoItem item) {
		this.itens.remove(item);
		
	}

	public void finalizar(Usuario usuario) {
		Compra compra = new Compra();
	    compra.setUsuario(usuario);
		compra.setItens(this.toJson());	   
	    compraDao.salvar(compra);		
	}

	private String toJson() {
		
		Gson gson = new Gson();
		System.out.println("PASSEI");
		System.out.println(gson.toJson(getItens()););
		
		
		JsonArrayBuilder builder = Json.createArrayBuilder();
	    for (CarrinhoItem item : itens) {
	        builder.add(Json.createObjectBuilder()
	                .add("titulo", item.getLivro().getTitulo())
	                .add("preco", item.getLivro().getPreco())
	                .add("quantidade", item.getQuantidade())
	                .add("total", getTotal(item)));
	    }
	    return builder.build().toString();
	}

}