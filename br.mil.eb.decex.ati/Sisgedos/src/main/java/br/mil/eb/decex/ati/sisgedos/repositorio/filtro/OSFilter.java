package br.mil.eb.decex.ati.sisgedos.repositorio.filtro;

import java.io.Serializable;
import java.util.Date;
import br.mil.eb.decex.ati.sisgedos.enumerado.statusOS;


public class OSFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long numeroDe;
	private Long numeroAte;
	private Date dataCriacaoDe;
	private Date dataCriacaoAte;
	private String nomeTecnico;
	private String nomeUsuario;
	private statusOS[] statusOS;
	
	
	public Long getNumeroDe() {
		return numeroDe;
	}
	
	public void setNumeroDe(Long numeroDe) {
		this.numeroDe = numeroDe;
	}
	
	public Long getNumeroAte() {
		return numeroAte;
	}
	
	public void setNumeroAte(Long numeroAte) {
		this.numeroAte = numeroAte;
	}
	
	public Date getDataCriacaoDe() {
		return dataCriacaoDe;
	}
	
	public void setDataCriacaoDe(Date dataCriacaoDe) {
		this.dataCriacaoDe = dataCriacaoDe;
	}
	
	public Date getDataCriacaoAte() {
		return dataCriacaoAte;
	}
	
	public void setDataCriacaoAte(Date dataCriacaoAte) {
		this.dataCriacaoAte = dataCriacaoAte;
	}
	
	public String getNomeTecnico() {
		return nomeTecnico;
	}
	
	public void setNomeTecnico(String nomeTecnico) {
		this.nomeTecnico = nomeTecnico;
	}
	
	public String getNomeUsuario() {
		return nomeUsuario;
	}
	
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	
	public statusOS[] getStatusOS() {
		return statusOS;
	}
	
	public void setStatusOS(statusOS[] statusOS) {
		this.statusOS = statusOS;
	}
}