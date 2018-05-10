package br.com.casadocodigo.loja.beans;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.Compra;
import br.com.casadocodigo.loja.models.Usuario;

@Model
public class CheckoutBean {

    private Usuario usuario = new Usuario();

    @Inject
    private CarrinhoCompras carrinho;
    
    @Inject
    private FacesContext facesContext;
    
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void seetUsuario(Usuario usuario) {
        this.usuario = usuario;
    }   
    

    @Transactional
    public void finalizar() {
    	Compra compra = new Compra();
    	compra.setUsuario(usuario);
    	carrinho.finalizar(compra);
    	
    	String contextName = facesContext.getExternalContext().getRequestContextPath();    
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);       
        response.setHeader("Location", contextName+"/service/pagamento?uuid="+compra.getUuid());
        
    	 

    }
}