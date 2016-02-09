package br.mil.eb.decex.ati.sisgedos.servico;

import java.io.Serializable;

import javax.inject.Inject;

import br.mil.eb.decex.ati.sisgedos.modelo.Produto;
import br.mil.eb.decex.ati.sisgedos.repositorio.Produtos;
import br.mil.eb.decex.ati.sisgedos.util.jpa.Transactional;

public class CadastroProdutoService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Produtos produtos;	
	
	@Transactional
	public Produto salvar(Produto produto){
		Produto produtoExistente = produtos.porSku(produto.getSku());
		
		if (produtoExistente != null && !produtoExistente.equals(produto)){
			throw new NegocioException("Já existe um produto com o SKU informado");
		}
		
		return produtos.guardar(produto);
	} 

}
