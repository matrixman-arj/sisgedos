package br.mil.eb.decex.ati.sisgedos.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.mil.eb.decex.ati.sisgedos.modelo.Usuario;

public class Usuarios implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Usuario porId(Long id) {
		return this.manager.find(Usuario.class, id);
	}
	
	public List<Usuario> tecnicos() {
		//filtrar apenas tecnicos (por um grupo específico)
		return this.manager.createQuery("from Usuario", Usuario.class)
				.getResultList();
	}
	
	public List<Usuario> porNome(String nome) {
		return this.manager.createQuery("from Usuario " +
				"where upper(nome) like :nome", Usuario.class)
				.setParameter("nome", nome.toUpperCase() + "%")
				.getResultList();
	}
	
}