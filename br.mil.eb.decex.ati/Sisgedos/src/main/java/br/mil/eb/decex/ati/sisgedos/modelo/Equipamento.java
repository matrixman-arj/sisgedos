package br.mil.eb.decex.ati.sisgedos.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import br.mil.eb.decex.ati.sisgedos.enumerado.MarcaEquipamento;
import br.mil.eb.decex.ati.sisgedos.enumerado.TipoEquipamento;

@Entity
@Table(name="equipamento")
public class Equipamento {
	
	private Long id;
	private String descricao;
	private MarcaEquipamento marca;
	private TipoEquipamento tipo;
	private String modelo;
	private Usuario usuario;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
		
	public void setId(Long id) {
		this.id = id;
	}
	
	@NotBlank @Size(max = 150)
	@Column(nullable = false, length = 150)
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	public MarcaEquipamento getMarca() {
		return marca;
	}
	public void setMarca(MarcaEquipamento marca) {
		this.marca = marca;
	}
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	public TipoEquipamento getTipo() {
		return tipo;
	}
	public void setTipo(TipoEquipamento tipo) {
		this.tipo = tipo;
	}
	
	@NotNull
	@Column(nullable = false, length = 80)
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = false)	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
		Equipamento other = (Equipamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
