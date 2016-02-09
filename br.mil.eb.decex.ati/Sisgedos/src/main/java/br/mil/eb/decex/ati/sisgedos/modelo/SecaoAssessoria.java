package br.mil.eb.decex.ati.sisgedos.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


import br.mil.eb.decex.ati.sisgedos.enumerado.TipoSecaoAssessoria;

@Entity
@Table(name = "secao_assessoria")
public class SecaoAssessoria implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String codigo;
	private String nome;
	private String sigla;
	private TipoSecaoAssessoria tipo;
	private Usuario usuario;
	
	@Id
	@SequenceGenerator(name="SECAO_ID_GENERATOR", sequenceName="SECAO_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SECAO_ID_GENERATOR")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(nullable = false, length = 20)
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@Column(nullable = false, length = 100)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(nullable = false, length = 50)
	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}	
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	public TipoSecaoAssessoria getTipo() {
		return tipo;
	}

	public void setTipo(TipoSecaoAssessoria tipo) {
		this.tipo = tipo;
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
		SecaoAssessoria other = (SecaoAssessoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
