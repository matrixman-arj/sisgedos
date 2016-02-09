package br.mil.eb.decex.ati.sisgedos.enumerado;

/**
 * Identificação de postos e graduações.<p/>
 * <ul>
 * 	<li>Gen Ex - General de Exercito</li>
 * 	<li>Gen Div - General de Divisão</li>
 * 	<li>Gen Bda - General de Brigada</li>
 * 	<li>Cel - Coronel</li>
 * 	<li>Ten Cel - Tenente Coronel</li>
 * 	<li>Maj - Major</li>
 * 	<li>Cap - Capitão</li>
 * 	<li>1º Ten - 1º Tenente</li>
 * 	<li>2º Ten - 2º Tenente</li>
 * 	<li>Asp - Aspirante</li>
 * 	<li>S Ten - Subtenente</li>
 * 	<li>1º Sgt - 1º Sargento</li>
 * 	<li>2º Sgt - 2º Sargento</li>
 * 	<li>3º Sgt - 3º Sargento</li>
 * 	<li>Cb - Cabo</li>
 * 	<li>Sd - Soldado</li>
 * </ul>
 * 
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 */
public enum Posto {
	
	GEN_EX("General de Exército"),
	GEN_DIV("General de Divisão"),
	GEN_BDA("General de Brigada"),
	CEL("Coronel"),
	TEN_CEL("Tenente Coronel"),
	MAJ("Major"),
	CAP("Capitão"),
	PRIMEIRO_TEN("1º Tenente"),
	SEGUNDO_TEN("2º Tenente"),
	ASP("Aspirante"),
	STEN("Subtenente"),
	PRIMEIRO_SGT("1º Sargento"),
	SEGUNDO_SGT("2º Sargento"),
	TERCEIRO_SGT("3º Sargento"),
	CB("Cabo"),
	SD("Soldado");		
	
	@SuppressWarnings("unused")	
	private String value;
	
	private Posto(String value) {
		this.value = value;
	}
	
}