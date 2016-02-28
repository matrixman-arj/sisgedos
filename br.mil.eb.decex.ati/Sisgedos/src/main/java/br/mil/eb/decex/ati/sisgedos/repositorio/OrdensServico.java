package br.mil.eb.decex.ati.sisgedos.repositorio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import br.mil.eb.decex.ati.sisgedos.modelo.OrdemServico;
import br.mil.eb.decex.ati.sisgedos.modelo.Usuario;
import br.mil.eb.decex.ati.sisgedos.modelo.vo.DataValor;
import br.mil.eb.decex.ati.sisgedos.repositorio.filtro.OSFilter;

public class OrdensServico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	@SuppressWarnings("unchecked")
	public Map<Date, BigDecimal> valoresTotaisPorData(Integer numeroDeDias, Usuario criadoPor){
		Session session = manager.unwrap(Session.class);
		numeroDeDias -=1;
		
		Calendar dataInicial = Calendar.getInstance();
		dataInicial = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH);
		dataInicial.add(Calendar.DAY_OF_MONTH, numeroDeDias * -1);
		
		Map<Date, BigDecimal> resultado = criarMapaVazio(numeroDeDias, dataInicial);
		
		Criteria criteria = session.createCriteria(OrdemServico.class);
		
		criteria.setProjection(Projections.projectionList()
				.add(Projections.sqlGroupProjection("date(data_criacao) as data", 
						"date(data_criacao)", new String[] {"data"}, 
						new Type[] {StandardBasicTypes.DATE}))
				
				.add(Projections.sum("valorTotal").as("valor"))
				)
				.add(Restrictions.ge("dataCriacao", dataInicial.getTime()));
			if (criadoPor != null){
				criteria.add(Restrictions.eqOrIsNull("tecnico", criadoPor));
			}
			
			List<DataValor> valoresPorData = criteria
					.setResultTransformer(Transformers.aliasToBean(DataValor.class)).list();
			
			for (DataValor dataValor : valoresPorData) {
				resultado.put(dataValor.getData(), dataValor.getValor());
			}
			
			//select date(data_criacao) as data, sum(valor_total) as valor
			//from ordemServico where data_criacao >= :dataInicial and tecnico_id = :criadoPor
			//group by date(data_criacao)
		
		return resultado;
	}

	private Map<Date, BigDecimal> criarMapaVazio(Integer numeroDeDias, Calendar dataInicial) {
		
		dataInicial = (Calendar) dataInicial.clone();
		Map<Date, BigDecimal> mapaInicial = new TreeMap<>();
		
		for(int i = 0; i <= numeroDeDias; i++){
			mapaInicial.put(dataInicial.getTime(), BigDecimal.ZERO);
			dataInicial.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		return mapaInicial;
	}
	
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