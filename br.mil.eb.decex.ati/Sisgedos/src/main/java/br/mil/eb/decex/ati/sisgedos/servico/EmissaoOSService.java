package br.mil.eb.decex.ati.sisgedos.servico;

import java.io.Serializable;

import javax.inject.Inject;

import br.mil.eb.decex.ati.sisgedos.enumerado.StatusOS;
import br.mil.eb.decex.ati.sisgedos.modelo.OrdemServico;
import br.mil.eb.decex.ati.sisgedos.repositorio.OrdensServico;
import br.mil.eb.decex.ati.sisgedos.util.jpa.Transactional;

public class EmissaoOSService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CadastroOSService cadastroOsService;
	
	@Inject
	private EstoqueService estoqueService;
	
	@Inject
	private OrdensServico ordensServico;
	
	@Transactional
	public OrdemServico emitir(OrdemServico ordemServico){
		ordemServico = this.cadastroOsService.salvar(ordemServico);
		
		if(ordemServico.isNaoEmissivel()){
			throw new NegocioException("Ordem de serviço não pode ser emitida com status" 
					+ ordemServico.getStatus().getDescricao() + ".");
		}
		
		this.estoqueService.baixarItensEstoque(ordemServico);
		
		ordemServico.setStatus(StatusOS.EMITIDA);
		
		ordemServico = this.ordensServico.guardar(ordemServico);
		
		return ordemServico;
	}

}
