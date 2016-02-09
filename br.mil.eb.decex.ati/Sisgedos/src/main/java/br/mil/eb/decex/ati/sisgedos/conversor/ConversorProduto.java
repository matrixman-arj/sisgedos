package br.mil.eb.decex.ati.sisgedos.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.mil.eb.decex.ati.sisgedos.modelo.Produto;
import br.mil.eb.decex.ati.sisgedos.repositorio.Produtos;
import br.mil.eb.decex.ati.sisgedos.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Produto.class)
public class ConversorProduto implements Converter {
	
	//@Inject
	private Produtos produtos;
	
	public ConversorProduto() {
		produtos = CDIServiceLocator.getBean(Produtos.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Produto retorno = null;
		
		if (value != null){
			Long id = new Long(value);
			retorno = produtos.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null){
			Produto produto = (Produto)value;
			return produto.getId() == null ? null : ((Produto)value).getId().toString();
		}
		return "";
	}

}
