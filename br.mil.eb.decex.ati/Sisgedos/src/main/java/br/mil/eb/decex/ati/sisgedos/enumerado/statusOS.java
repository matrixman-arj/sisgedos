package br.mil.eb.decex.ati.sisgedos.enumerado;

public enum statusOS {
	
	ORCAMENTO("Orçamento"),
	EMITIDA("Emitida"),
	EM_ANALISE("Em análise"), 
	CANCELADA("Cancelada"), 
	AGUARDANDO_MATERIAL("Aguardando material"),
	PRONTO("Pronto"), 
	FINALIZADA("Finalizada");
	
	private String descricao;
	
	private statusOS(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	
}

