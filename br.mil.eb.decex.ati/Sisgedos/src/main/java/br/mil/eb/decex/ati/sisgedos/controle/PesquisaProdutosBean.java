package br.mil.eb.decex.ati.sisgedos.controle;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.mil.eb.decex.ati.sisgedos.modelo.Produto;
import br.mil.eb.decex.ati.sisgedos.repositorio.Produtos;
import br.mil.eb.decex.ati.sisgedos.repositorio.filtro.ProdutoFilter;
import br.mil.eb.decex.ati.sisgedos.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaProdutosBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Produtos produtos;
	
	private ProdutoFilter filtro;
	private List<Produto> produtosFiltrados;
	
	private Produto produtoSelecionado;
	
	public PesquisaProdutosBean(){
		filtro = new ProdutoFilter();
	}
	
	public void excluir(){
		produtos.remover(produtoSelecionado);		
		produtosFiltrados.remove(produtoSelecionado);
		
		FacesUtil.addInfoMessage("Produto " + produtoSelecionado.getSku()+ " excluido com sucesso");
	}
	
	public void pesquisar(){
		produtosFiltrados = produtos.filtrados(filtro);
	}
	
	public List<Produto> getProdutosFiltrados() {
		return produtosFiltrados;
	}

	public ProdutoFilter getFiltro() {
		return filtro;
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}
	
	
}
