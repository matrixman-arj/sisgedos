package br.mil.eb.decex.ati.sisgedos.servico;

import java.io.Serializable;

import javax.inject.Inject;

import br.mil.eb.decex.ati.sisgedos.enumerado.StatusOS;
import br.mil.eb.decex.ati.sisgedos.modelo.OrdemServico;
import br.mil.eb.decex.ati.sisgedos.repositorio.OrdensServico;
import br.mil.eb.decex.ati.sisgedos.util.jpa.Transactional;

public class CancelamentoOSService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private OrdensServico ordensServico;
	
	@Inject
	private EstoqueService estoqueService;
	
	@Transactional
	public OrdemServico cancelar(OrdemServico ordemServico) {
		ordemServico = this.ordensServico.porId(ordemServico.getId());
		
		if (ordemServico.isNaoCancelavel()){
			throw new NegocioException("Ordem de serviço não pode ser cancelada no status "
					+ ordemServico.getStatus().getDescricao() + ".");
		}
		if 
		(ordemServico.isEmitida()){
			this.estoqueService.retornarItensEstoque(ordemServico);
		}
		
		ordemServico.setStatus(StatusOS.CANCELADA);
		
		ordemServico = this.ordensServico.guardar(ordemServico);
		
		return ordemServico;
	}

}
