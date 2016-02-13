package br.mil.eb.decex.ati.sisgedos.controle;

import java.io.Serializable;

import javax.enterprise.event.Event;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.mil.eb.decex.ati.sisgedos.evento.OSAlteradaEvent;
import br.mil.eb.decex.ati.sisgedos.modelo.OrdemServico;
import br.mil.eb.decex.ati.sisgedos.servico.EmissaoOSService;
import br.mil.eb.decex.ati.sisgedos.util.jsf.FacesUtil;

@Named
@ViewScoped
public class EmissaoOSBean implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EmissaoOSService emissaoOSService;
	
	@Inject	
	@OrdemServicoEdicao
	private OrdemServico ordemServico;
	
	@Inject
	private Event<OSAlteradaEvent> oSAlteradaEvent;
	
	public void emitirOrdemServico(){
		this.ordemServico.removerItemVazio();
		
		try {
			this.ordemServico = this.emissaoOSService.emitir(this.ordemServico);
			this.oSAlteradaEvent.fire(new OSAlteradaEvent(this.ordemServico));			
			
			FacesUtil.addInfoMessage("Ordem de serviço emitida com sucesso!");
		} finally {
			this.ordemServico.adicionarItemVazio();
		}
		
	}

}
