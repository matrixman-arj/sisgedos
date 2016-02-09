package br.mil.eb.decex.ati.sisgedos.repositorio.filtro;

import java.io.Serializable;

import br.mil.eb.decex.ati.sisgedos.validacao.SKU;

public class ProdutoFilter implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String sku;
	private String nome;
	
	@SKU
	public String getSku() {
		return sku;
	}
	
	public void setSku(String sku) {
		this.sku = sku == null ? null : sku.toUpperCase();
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	

}
