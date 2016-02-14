package br.mil.eb.decex.ati.sisgedos.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.mil.eb.decex.ati.sisgedos.enumerado.StatusOS;
import br.mil.eb.decex.ati.sisgedos.modelo.OrdemServico;
import br.mil.eb.decex.ati.sisgedos.repositorio.OrdensServico;
import br.mil.eb.decex.ati.sisgedos.repositorio.filtro.OSFilter;

@Named
@ViewScoped
public class PesquisaOSBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private OrdensServico ordensServico;
	
	private OSFilter filtro;
	private List<OrdemServico> osFiltradas;
	
	public PesquisaOSBean(){
		filtro = new OSFilter();
		osFiltradas = new ArrayList<>();
	}
	
	public void pesquisar(){
		osFiltradas = ordensServico.filtrados(filtro);
	}
	
	public StatusOS[] getStatusOS(){
		return StatusOS.values();
	}
	

	public List<OrdemServico> getOsFiltradas() {
		return osFiltradas;
	}

	public OSFilter getFiltro() {
		return filtro;
	}
	
	
}
