package br.mil.eb.decex.ati.sisgedos.controle;

import java.io.Serializable;


import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.mil.eb.decex.ati.sisgedos.modelo.OrdemServico;
import br.mil.eb.decex.ati.sisgedos.servico.EmissaoOSService;
import br.mil.eb.decex.ati.sisgedos.util.jsf.FacesUtil;

@Named
@ViewScoped
public class EmissaoOSBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EmissaoOSService emissaoOrdemServicoService;
	
	@Inject
	@OSEdicao
	private OrdemServico ordemServico;
	
	public void emitirOS(){
		this.ordemServico.removerItemVazio();
		
		try {
			this.ordemServico = this.emissaoOrdemServicoService.emitir(this.ordemServico);
			
			FacesUtil.addInfoMessage("Orden de serviço emitida com sucesso!");
		} finally {
			this.ordemServico.adicionarItemVazio();
		}
		
	}
}
