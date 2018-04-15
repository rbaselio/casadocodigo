package br.com.casadocodigo.loja.beans;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;

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

	@Transactional
	public String salvar() {

		dao.salvar(livro);
		System.out.println("Livro: " + livro);
		System.out.println("Livro Salvo com Sucesso!");

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
