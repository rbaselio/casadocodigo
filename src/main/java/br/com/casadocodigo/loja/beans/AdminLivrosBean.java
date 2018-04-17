package br.com.casadocodigo.loja.beans;

import java.io.IOException;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.Part;
import javax.transaction.Transactional;

import br.com.casadocodigo.infra.FileSaver;
import br.com.casadocodigo.loja.daos.AutorDao;
import br.com.casadocodigo.loja.daos.LivroDao;
import br.com.casadocodigo.loja.models.Autor;
import br.com.casadocodigo.loja.models.Livro;

@Model
public class AdminLivrosBean {

	private Livro livro = new Livro();

	@Inject
	private LivroDao dao;
	@Inject
	private AutorDao autorDao;

	@Inject
	private FacesContext context;
	
	private Part capaLivro;

	public Part getCapaLivro() {
		return capaLivro;
	}

	public void setCapaLivro(Part capaLivro) {
		this.capaLivro = capaLivro;
	}

	@Transactional
	public String salvar() throws IOException {

		dao.salvar(livro);
		System.out.println("Livro: " + livro);
		System.out.println("Livro Salvo com Sucesso!");
		
		livro.setCapaPath(new FileSaver().write(capaLivro, "capasLivros"));

		context.getExternalContext().getFlash().setKeepMessages(true);

		context.addMessage(null, new FacesMessage("Livro Cadastrado com Sucesso"));

		return "/livros/lista?faces-redirect=true";

	}

	public List<Autor> getAutores() {
		return autorDao.listar();
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

}
