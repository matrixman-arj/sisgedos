package br.mil.eb.decex.ati.sisgedos.evento;

import br.mil.eb.decex.ati.sisgedos.modelo.OrdemServico;

public class OSAlteradaEvent {
	
	private OrdemServico ordemServico;
	
	public OSAlteradaEvent(OrdemServico ordemServico){
		this.ordemServico = ordemServico;
	}
	
	public OrdemServico getOrdemServico(){
		return ordemServico;
	}

}
