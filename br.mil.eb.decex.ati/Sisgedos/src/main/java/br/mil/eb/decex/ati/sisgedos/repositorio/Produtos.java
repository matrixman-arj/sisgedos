package br.mil.eb.decex.ati.sisgedos.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import org.apache.commons.lang3.StringUtils;

import br.mil.eb.decex.ati.sisgedos.modelo.Produto;
import br.mil.eb.decex.ati.sisgedos.repositorio.filtro.ProdutoFilter;
import br.mil.eb.decex.ati.sisgedos.servico.NegocioException;
import br.mil.eb.decex.ati.sisgedos.util.jpa.Transactional;

public class Produtos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	@Transactional
	public Produto guardar(Produto produto) {		
		return manager.merge(produto);		
		
	}
	
	@Transactional
	public void remover(Produto produto){
	try{
		produto = porId(produto.getId());
		manager.remove(produto);
		manager.flush();
	}catch (PersistenceException e){
		throw new NegocioException("Produto não pode ser excluido.");
	}
  }

	public Produto porSku(String sku) {
		try {
			return manager.createQuery("from Produto where upper(sku) = :sku", Produto.class)
				.setParameter("sku", sku.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Produto> filtrados(ProdutoFilter filtro){
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Produto.class);
		
		if (StringUtils.isNotBlank(filtro.getSku())){
		criteria.add(Restrictions.eqOrIsNull("sku", filtro.getSku()));
	    }
		if (StringUtils.isNotBlank(filtro.getNome())){
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		return criteria.addOrder(Order.asc("nome")).list();
  }
  

	public Produto porId(Long id) {		
		return manager.find(Produto.class, id);
	}

	public List<Produto> porNome(String nome) {		
		return this.manager.createQuery("from Produto where upper(nome) like :nome", Produto.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}
}
