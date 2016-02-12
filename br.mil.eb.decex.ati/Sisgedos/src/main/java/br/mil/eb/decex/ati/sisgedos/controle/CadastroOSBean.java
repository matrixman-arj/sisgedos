package br.mil.eb.decex.ati.sisgedos.controle;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import br.mil.eb.decex.ati.sisgedos.enumerado.FormaPagamento;
import br.mil.eb.decex.ati.sisgedos.modelo.Equipamento;
import br.mil.eb.decex.ati.sisgedos.modelo.ItemOS;
import br.mil.eb.decex.ati.sisgedos.modelo.OrdemServico;
import br.mil.eb.decex.ati.sisgedos.modelo.Produto;
import br.mil.eb.decex.ati.sisgedos.modelo.Usuario;
import br.mil.eb.decex.ati.sisgedos.repositorio.Produtos;
import br.mil.eb.decex.ati.sisgedos.repositorio.Usuarios;
import br.mil.eb.decex.ati.sisgedos.servico.CadastroOsService;
import br.mil.eb.decex.ati.sisgedos.util.jsf.FacesUtil;
import br.mil.eb.decex.ati.sisgedos.validacao.SKU;

@Named
@ViewScoped
public class CadastroOSBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	@Inject
	private Usuarios usuarios;
	
	@Inject
	private Produtos produtos; 
	
	
	@Inject
	private CadastroOsService cadastroOsService;
	
	private String sku;
	
	@Produces
	@OSEdicao
	private OrdemServico ordemServico;
	
	private Equipamento equipamento;
	private List<Usuario> tecnicos;
	
	private Produto produtoLinhaEditavel;
	
	public CadastroOSBean(){
		limpar();
		
	}
	
	public void inicialisar(){
		if(FacesUtil.isNotPostback()){
			this.tecnicos = this.usuarios.tecnicos();
			
			this.ordemServico.adicionarItemVazio();
			
			this.recalcularOS();
		}
	}
	
	private void limpar(){
		ordemServico = new OrdemServico();
		equipamento = new Equipamento();
	}
	
	public void salvar(){
		this.ordemServico.removerItemVazio();
		
	try{
		this.ordemServico = this.cadastroOsService.salvar(this.ordemServico);
		
		FacesUtil.addInfoMessage("Ordem de serviço salva com sucesso!");
	}finally{
		this.ordemServico.adicionarItemVazio();
	}
 }
	
	public void recalcularOS(){
		if(this.ordemServico != null){
			this.ordemServico.recalcularValorTotal();
		}
	}
	
	public void carregarProdutoPorSku(){
		if(StringUtils.isNotEmpty(this.sku)){
			this.produtoLinhaEditavel = this.produtos.porSku(this.sku);
			this.carregarProdutoLinhaEditavel();
		}
	}
	
	public void carregarProdutoLinhaEditavel(){
		ItemOS item = this.ordemServico.getItens().get(0);
		
		if(this.produtoLinhaEditavel != null){
			if(this.existeItemComProduto(this.produtoLinhaEditavel)){
				FacesUtil.addErrorMessage("Já existe um item na ordem de serviço com o produto informado.");
			}else{
				item.setProduto(this.produtoLinhaEditavel);
				item.setValorUnitario(this.produtoLinhaEditavel.getValorUnitario());
				
				this.ordemServico.adicionarItemVazio();
				this.produtoLinhaEditavel = null;
				this.sku = null;
				
				this.ordemServico.recalcularValorTotal();
			}
		}
	}
	
	private boolean existeItemComProduto(Produto produto) {
		boolean existeItem = false;
		for(ItemOS item : this.getOrdemServico().getItens()){
			if(produto.equals(item.getProduto())){
				existeItem = true;
				break;
			}
		}
		return existeItem;
	}

	public List<Produto> completarProduto(String nome){
		return this.produtos.porNome(nome);
	}
	
	public void atualizarQuantidade(ItemOS item, int linha){
		if(item.getQuantidade() < 1){
			if(linha == 0){
				item.setQuantidade(1);
			}else{
				this.getOrdemServico().getItens().remove(linha);
			}
		}
		
		this.ordemServico.recalcularValorTotal();
	}
	
	public FormaPagamento[] getFormasPagamento(){
		return FormaPagamento.values();
	}
	
	public List<Usuario> completarUsuario(String nome){
		return this.usuarios.porNome(nome);
	}
		
	public OrdemServico getOrdemServico() {
		return ordemServico;
	}
	
	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
	}

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public List<Usuario> getTecnicos() {
		return tecnicos;
	}
	
	public boolean isEditando(){
		return this.ordemServico.getId() != null;
	}

	public Produto getProdutoLinhaEditavel() {
		return produtoLinhaEditavel;
	}

	public void setProdutoLinhaEditavel(Produto produtoLinhaEditavel) {
		this.produtoLinhaEditavel = produtoLinhaEditavel;
	}
	
	@SKU
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
	
	
}
