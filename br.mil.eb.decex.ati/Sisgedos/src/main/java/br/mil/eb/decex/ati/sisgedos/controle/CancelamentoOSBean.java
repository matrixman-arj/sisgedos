package br.mil.eb.decex.ati.sisgedos.controle;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import br.mil.eb.decex.ati.sisgedos.evento.OSAlteradaEvent;
import br.mil.eb.decex.ati.sisgedos.modelo.OrdemServico;
import br.mil.eb.decex.ati.sisgedos.servico.CancelamentoOSService;
import br.mil.eb.decex.ati.sisgedos.util.jsf.FacesUtil;

@Named
@RequestScoped
public class CancelamentoOSBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CancelamentoOSService cancelamentoOSService;
	
	@Inject
	private Event<OSAlteradaEvent> oSAlteradaEvent;
	
	@Inject
	@OSEdicao
	private OrdemServico ordemServico;
	
	public void cancelarOrdemServico(){
		this.ordemServico = this.cancelamentoOSService.cancelar(this.ordemServico);
		this.oSAlteradaEvent.fire(new OSAlteradaEvent(this.ordemServico));
		
		FacesUtil.addInfoMessage("Ordem de serviço cancelada com sucesso! ");
	}

}
