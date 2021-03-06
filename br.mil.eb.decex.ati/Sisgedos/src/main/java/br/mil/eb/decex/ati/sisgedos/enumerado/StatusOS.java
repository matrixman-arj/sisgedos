package br.mil.eb.decex.ati.sisgedos.enumerado;

public enum StatusOS {
	
	ORCAMENTO("Orçamento"),
	EMITIDA("Emitida"),
	EM_ANALISE("Em análise"), 
	CANCELADA("Cancelada"), 
	AGUARDANDO_MATERIAL("Aguardando material"),
	PRONTO("Pronto"), 
	FINALIZADA("Finalizada");
	
	private String descricao;
	
	private StatusOS(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	
}

