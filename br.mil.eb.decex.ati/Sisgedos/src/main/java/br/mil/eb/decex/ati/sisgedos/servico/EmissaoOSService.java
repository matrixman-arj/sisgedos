package br.mil.eb.decex.ati.sisgedos.servico;

import java.io.Serializable;

import javax.inject.Inject;

import br.mil.eb.decex.ati.sisgedos.enumerado.statusOS;
import br.mil.eb.decex.ati.sisgedos.modelo.OrdemServico;
import br.mil.eb.decex.ati.sisgedos.repositorio.OrdensServico;

public class EmissaoOSService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CadastroOsService cadastroOsService;
	
	@Inject
	private EstoqueService estoqueService;
	
	@Inject
	private OrdensServico ordensServico;
	
	public OrdemServico emitir(OrdemServico ordemServico){
		ordemServico = this.cadastroOsService.salvar(ordemServico);
		
		if(ordemServico.isNaoEmissivel()){
			throw new NegocioException("Ordem de serviço não pode ser emitida com status" 
					+ ordemServico.getStatus().getDescricao() + ".");
		}
		
		this.estoqueService.baixarItensEstoque(ordemServico);
		
		ordemServico.setStatus(statusOS.EMITIDA);
		
		ordemServico = this.ordensServico.guardar(ordemServico);
		
		return ordemServico;
	}

}
