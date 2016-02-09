package br.mil.eb.decex.ati.sisgedos.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.mil.eb.decex.ati.sisgedos.modelo.Usuario;
import br.mil.eb.decex.ati.sisgedos.repositorio.Usuarios;
import br.mil.eb.decex.ati.sisgedos.util.cdi.CDIServiceLocator;

@FacesConverter(forClass=Usuario.class)
public class ConversorUsuario implements Converter {

	//@Inject
	private Usuarios usuarios;
	
	public ConversorUsuario() {
		this.usuarios = (Usuarios) CDIServiceLocator.getBean(Usuarios.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Usuario retorno = null;

		if (value != null) {
			retorno = this.usuarios.porId(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			return ((Usuario) value).getId().toString();
		}
		return "";
	}

}