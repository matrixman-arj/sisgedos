package br.mil.eb.decex.ati.sisgedos.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import br.mil.eb.decex.ati.sisgedos.enumerado.FormaPagamento;
import br.mil.eb.decex.ati.sisgedos.enumerado.statusOS;

@Entity
@Table(name = "ordem_servico")
public class OrdemServico implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Date dataCriacao;
	private String observacao;
	private Date dataEntrega;
	private BigDecimal valorFrete = BigDecimal.ZERO;
	private BigDecimal valorDesconto = BigDecimal.ZERO;
	private BigDecimal valorTotal = BigDecimal.ZERO ;
	private statusOS status = statusOS.ORCAMENTO;
	private FormaPagamento formaPagamento;
	private Usuario tecnico;
	private Usuario usuario;
	private Equipamento dadosEquipto;
	private List<ItemOS> itens = new ArrayList<>();
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao", nullable = false)
	public Date getDataCriacao() {
		return dataCriacao;
	}
	
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	@Column(columnDefinition = "text")
	public String getObservacao() {
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "data_entrega", nullable = false)
	public Date getDataEntrega() {
		return dataEntrega;
	}
	
	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
	
	@NotNull
	@Column(name = "valor_frete", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}
		
	@NotNull
	@Column(name = "valor_desconto", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	@NotNull
	@Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	public statusOS getStatus() {
		return status;
	}
	
	public void setStatus(statusOS status) {
		this.status = status;
	}
	
	//@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "forma_pagamento", nullable = false, length = 20)
	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "tecnico_id", nullable = false)
	public Usuario getTecnico() {
		return tecnico;
	}
	
	public void setTecnico(Usuario tecnico) {
		this.tecnico = tecnico;
	}
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	//@NotNull
	@ManyToOne
	@JoinColumn(name = "equipamento_id")
	public Equipamento getDadosEquipto() {
		return dadosEquipto;
	}
	
	
	
	public void setDadosEquipto(Equipamento dadosEquipto) {
		this.dadosEquipto = dadosEquipto;
	}
	
	
	@OneToMany(mappedBy = "ordemServico",cascade = CascadeType.ALL, orphanRemoval = true)
	public List<ItemOS> getItens() {
		return itens;
	}
	
	public void setItens(List<ItemOS> itens) {
		this.itens = itens;
	}
	
	@Transient
	public boolean isNovo(){
		return getId() == null;
	}
	
	@Transient
	public boolean isExistente(){
		return !isNovo();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdemServico other = (OrdemServico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Transient
	public BigDecimal getValorSubtotal(){
		return this.getValorTotal().subtract(this.getValorFrete().add(this.valorDesconto));
	}

	public void recalcularValorTotal() {
		BigDecimal total = BigDecimal.ZERO;
		
		total = total.add(this.getValorFrete()).subtract(this.getValorDesconto());
		
		for(ItemOS item : this.getItens()){
			if(item.getProduto() != null && item.getProduto().getId() != null){
			total = total.add(item.getValorTotal());
			}
		}
				
		this.setValorTotal(total);
		
	}

	public void adicionarItemVazio() {
		if (this.isOrcamento()){
			Produto produto = new Produto();
						
			ItemOS	item = new ItemOS();			
			item.setProduto(produto);
			item.setOrdemServico(this);
			
			this.getItens().add(0, item);
		}
	}
	
	@Transient
	public boolean isOrcamento() {		
		return statusOS.ORCAMENTO.equals(this.getStatus());
	}

	public void removerItemVazio() {
		ItemOS primeiroItem = this.getItens().get(0);
		
		if(primeiroItem != null && primeiroItem.getProduto().getId() == null){
			this.getItens().remove(0);
		}
		
	}
	
	@Transient
	public boolean isValorTotalNegativo() {		
		return this.getValorTotal().compareTo(BigDecimal.ZERO)< 0;
	}
	
	@Transient
	public boolean isEmitido() {		
		return statusOS.EMITIDA.equals(this.getStatus());
	}
	
	@Transient
	public boolean isNaoEmissivel() {		
		return !this.isEmissivel() ;
	}
	
	@Transient
	public boolean isEmissivel() {		
		return this.isExistente() && this.isOrcamento();
	}
	
}
