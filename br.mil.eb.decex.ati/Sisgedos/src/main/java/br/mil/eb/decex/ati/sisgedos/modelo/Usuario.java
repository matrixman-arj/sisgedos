package br.mil.eb.decex.ati.sisgedos.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import br.mil.eb.decex.ati.sisgedos.enumerado.Posto;
import br.mil.eb.decex.ati.sisgedos.enumerado.SituacaoUsuario;
import br.mil.eb.decex.ati.sisgedos.enumerado.TipoPessoa;

@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String cpf;
	private String email;
	private String identidade;
	private String nome;
	private String nomeGuerra;
	private Posto postoGraduacao;
	private String senha;
	private SituacaoUsuario situacao;
	private TipoPessoa tipo;
	private List<Grupo> grupos = new ArrayList<>();
	private List<SecaoAssessoria> secaoAssessorias = new ArrayList<>();
	private List<Equipamento> equipamentos = new ArrayList<>();

	@Id
	@SequenceGenerator(name = "USUARIO_ID_GENERATOR", sequenceName = "USUARIO_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_ID_GENERATOR")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable = false, length = 20)
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Column(nullable = false, length = 200)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(nullable = false, length = 20)
	public String getIdentidade() {
		return identidade;
	}

	public void setIdentidade(String identidade) {
		this.identidade = identidade;
	}

	@Column(nullable = false, length = 100)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(nullable = false, length = 50)
	public String getNomeGuerra() {
		return nomeGuerra;
	}

	public void setNomeGuerra(String nomeGuerra) {
		this.nomeGuerra = nomeGuerra;
	}

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	public Posto getPostoGraduacao() {
		return postoGraduacao;
	}

	public void setPostoGraduacao(Posto postoGraduacao) {
		this.postoGraduacao = postoGraduacao;
	}

	@Column(nullable = false, length = 20)
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	public SituacaoUsuario getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoUsuario situacao) {
		this.situacao = situacao;
	}

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	public TipoPessoa getTipo() {
		return tipo;
	}

	public void setTipo(TipoPessoa tipo) {
		this.tipo = tipo;
	}

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "usuario_grupo", joinColumns = @JoinColumn(name = "usuario_id") , 
	inverseJoinColumns = @JoinColumn(name = "grupo_id") )
	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}	
	
	@Column(nullable = false, length = 150)
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	public List<SecaoAssessoria> getSecaoAssessorias() {
		return secaoAssessorias;
	}

	public void setSecaoAssessorias(List<SecaoAssessoria> secaoAssessorias) {
		this.secaoAssessorias = secaoAssessorias;
	}
	
	
	@Column(nullable = false, length = 150)
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	public List<Equipamento> getEquipamentos() {
		return equipamentos;
	}

	public void setEquipamentos(List<Equipamento> equipamentos) {
		this.equipamentos = equipamentos;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
