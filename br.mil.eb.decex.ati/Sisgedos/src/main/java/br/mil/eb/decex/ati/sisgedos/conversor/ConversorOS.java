package br.mil.eb.decex.ati.sisgedos.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.mil.eb.decex.ati.sisgedos.modelo.OrdemServico;
import br.mil.eb.decex.ati.sisgedos.repositorio.OrdensServico;
import br.mil.eb.decex.ati.sisgedos.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = OrdemServico.class)
public class ConversorOS implements Converter {
	
	//@Inject
	private OrdensServico ordensServico;
	
	public ConversorOS() {
		ordensServico = CDIServiceLocator.getBean(OrdensServico.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		OrdemServico retorno = null;
		
		if (value != null){
			Long id = new Long(value);
			retorno = ordensServico.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null){
			OrdemServico ordemServico = (OrdemServico)value;
			return ordemServico.getId() == null ? null : ordemServico.getId().toString();
		}
		return "";
	}

}
