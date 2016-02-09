package br.mil.eb.decex.ati.sisgedos.servico;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import br.mil.eb.decex.ati.sisgedos.enumerado.statusOS;
import br.mil.eb.decex.ati.sisgedos.modelo.OrdemServico;
import br.mil.eb.decex.ati.sisgedos.repositorio.OrdensServico;
import br.mil.eb.decex.ati.sisgedos.util.jpa.Transactional;
public class CadastroOsService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private OrdensServico ordensServico;
	
	@Transactional
	public OrdemServico salvar(OrdemServico ordemServico) {
		if (ordemServico.isNovo()) {
			ordemServico.setDataCriacao(new Date());
			ordemServico.setStatus(statusOS.ORCAMENTO);
		}
		
		ordemServico = this.ordensServico.guardar(ordemServico);
		return ordemServico;
	}
	
}
