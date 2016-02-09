package br.mil.eb.decex.ati.sisgedos.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.mil.eb.decex.ati.sisgedos.modelo.Categoria;
import br.mil.eb.decex.ati.sisgedos.repositorio.Categorias;
import br.mil.eb.decex.ati.sisgedos.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Categoria.class)
public class ConversorCategoria implements Converter {
	
	//@Inject
	private Categorias categorias;
	
	public ConversorCategoria() {
		categorias = CDIServiceLocator.getBean(Categorias.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Categoria retorno = null;
		
		if (value != null){
			Long id = new Long(value);
			retorno = categorias.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null){
			return ((Categoria)value).getId().toString();
		}
		return "";
	}

}
