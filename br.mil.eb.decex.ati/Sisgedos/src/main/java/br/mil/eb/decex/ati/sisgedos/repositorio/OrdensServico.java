package br.mil.eb.decex.ati.sisgedos.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.mil.eb.decex.ati.sisgedos.modelo.OrdemServico;
import br.mil.eb.decex.ati.sisgedos.repositorio.filtro.OSFilter;

public class OrdensServico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	public List<OrdemServico> filtrados(OSFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(OrdemServico.class)
				// fazemos uma associação (join) com usuario e nomeamos como "u"
				.createAlias("usuario", "u")
				// fazemos uma associação (join) com tecnico e nomeamos como "t"
				.createAlias("tecnico", "t");
		
		if (filtro.getNumeroDe() != null) {
			// id deve ser maior ou igual (ge = greater or equals) a filtro.numeroDe
			criteria.add(Restrictions.ge("id", filtro.getNumeroDe()));
		}

		if (filtro.getNumeroAte() != null) {
			// id deve ser menor ou igual (le = lower or equal) a filtro.numeroDe
			criteria.add(Restrictions.le("id", filtro.getNumeroAte()));
		}

		if (filtro.getDataCriacaoDe() != null) {
			criteria.add(Restrictions.ge("dataCriacao", filtro.getDataCriacaoDe()));
		}
		
		if (filtro.getDataCriacaoAte() != null) {
			criteria.add(Restrictions.le("dataCriacao", filtro.getDataCriacaoAte()));
		}
		
		if (StringUtils.isNotBlank(filtro.getNomeUsuario())) {
			// acessamos o nome do cliente associado ao pedido pelo alias "c", criado anteriormente
			criteria.add(Restrictions.ilike("u.nome", filtro.getNomeUsuario(), MatchMode.ANYWHERE));
		}
		
		if (StringUtils.isNotBlank(filtro.getNomeTecnico())) {
			// acessamos o nome do vendedor associado ao pedido pelo alias "v", criado anteriormente
			criteria.add(Restrictions.ilike("t.nome", filtro.getNomeTecnico(), MatchMode.ANYWHERE));
		}
		
		if (filtro.getStatusOS() != null && filtro.getStatusOS().length > 0) {
			// adicionamos uma restrição "in", passando um array de constantes da enum StatusOS
			criteria.add(Restrictions.in("status", filtro.getStatusOS()));
		}
		
		return criteria.addOrder(Order.asc("id")).list();
	}
	
	public OrdemServico guardar(OrdemServico ordemServico) {
		return this.manager.merge(ordemServico);
	}
	
	public OrdemServico porId(Long id) {		
		return this.manager.find(OrdemServico.class, id);
	}
	
}