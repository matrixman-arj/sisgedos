package br.mil.eb.decex.ati.sisgedos.servico;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import br.mil.eb.decex.ati.sisgedos.enumerado.StatusOS;
import br.mil.eb.decex.ati.sisgedos.modelo.OrdemServico;
import br.mil.eb.decex.ati.sisgedos.repositorio.OrdensServico;
import br.mil.eb.decex.ati.sisgedos.util.jpa.Transactional;
public class CadastroOSService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private OrdensServico ordensServico;
	
	@Transactional
	public OrdemServico salvar(OrdemServico ordemServico) {
		if (ordemServico.isNovo()) {
			ordemServico.setDataCriacao(new Date());
			ordemServico.setStatus(StatusOS.ORCAMENTO);
		}
		
		ordemServico.recalcularValorTotal();
		
		if (ordemServico.isNaoAlteravel()){
			throw new NegocioException("Ordem de serviço não pode ser alterada no status " 
					+ ordemServico.getStatus().getDescricao() + ".");
		}
		
		if (ordemServico.getItens().isEmpty()){
			throw new NegocioException("A ordem de serviço deve possuir pelo menos um item.");
		}
		
		if (ordemServico.isValorTotalNegativo()){
			throw new NegocioException("Valor total da ordem de serviço não pode ser negativo.");
		}
		
		ordemServico = this.ordensServico.guardar(ordemServico);
		return ordemServico;
	}
	
}
