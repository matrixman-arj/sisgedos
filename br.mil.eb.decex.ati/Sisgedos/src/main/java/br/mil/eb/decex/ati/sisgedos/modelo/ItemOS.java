package br.mil.eb.decex.ati.sisgedos.modelo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "item_os")
public class ItemOS implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer quantidade = 1;
	private BigDecimal valorUnitario = BigDecimal.ZERO;
	private Produto produto;
	private OrdemServico ordemServico;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(nullable = false, length = 3)
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	@Column(name = "valor_unitario", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	
	@ManyToOne
	@JoinColumn(name = "produto_id", nullable = false)
	public Produto getProduto() {
		return produto;		
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	@ManyToOne
	@JoinColumn(name = "ordem_servico_id", nullable = false)
	public OrdemServico getOrdemServico() {
		return ordemServico;
	}
	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
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
		ItemOS other = (ItemOS) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Transient
	public BigDecimal getValorTotal() {
		return this.getValorUnitario().multiply(new BigDecimal(this.getQuantidade()));
	}
	
	@Transient
	public boolean isProdutoAssociado(){
		return this.getProduto() != null && this.getProduto().getId() != null;
	}
	
	@Transient
	public boolean isEstoqueSuficiente(){
		return this.getOrdemServico().isEmitido() || this.getProduto().getId() == null 
				|| this.getProduto().getQuantidadeEstoque() >= this.getQuantidade();
	}
	
	@Transient
	public boolean isEstoqueInsuficiente(){
		return !this.isEstoqueSuficiente();
	}
}
