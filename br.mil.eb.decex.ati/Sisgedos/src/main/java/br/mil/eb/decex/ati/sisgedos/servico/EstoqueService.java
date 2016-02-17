package br.mil.eb.decex.ati.sisgedos.servico;

import java.io.Serializable;

import javax.inject.Inject;

import br.mil.eb.decex.ati.sisgedos.modelo.ItemOS;
import br.mil.eb.decex.ati.sisgedos.modelo.OrdemServico;
import br.mil.eb.decex.ati.sisgedos.repositorio.OrdensServico;
import br.mil.eb.decex.ati.sisgedos.util.jpa.Transactional;

public class EstoqueService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private OrdensServico ordensServico;
	
	@Transactional
	public void baixarItensEstoque(OrdemServico ordemServico){
		ordemServico = this.ordensServico.porId(ordemServico.getId());
		

		for (ItemOS item : ordemServico.getItens()) {
			item.getProduto().baixarEstoque(item.getQuantidade());
		}
	


		for (ItemOS item : ordemServico.getItens()){
			item.getProduto().baixarEstoque(item.getQuantidade());
		}
	}

	public void retornarItensEstoque(OrdemServico ordemServico) {
		ordemServico = this.ordensServico.porId(ordemServico.getId());
		
		for (ItemOS item : ordemServico.getItens()){
			item.getProduto().adicionarEstoque(item.getQuantidade());
		}
		
	}
	

}
