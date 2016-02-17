package br.mil.eb.decex.ati.sisgedos.controle;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.velocity.tools.generic.NumberTool;

import br.mil.eb.decex.ati.sisgedos.modelo.OrdemServico;
import br.mil.eb.decex.ati.sisgedos.util.jsf.FacesUtil;
import br.mil.eb.decex.ati.sisgedos.util.mail.Mailer;
import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;

@Named
@RequestScoped
public class EnvioOSEmailBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Mailer mailer;
	
	@Inject
	@OSEdicao
	private OrdemServico ordemServico;
	
	public void enviarOrdemServico() {
		MailMessage message = mailer.novaMensagem();
		
		message.to(this.ordemServico.getUsuario().getEmail())
			.subject("OrdemServico " + this.ordemServico.getId())
			.bodyHtml(new VelocityTemplate(getClass().getResourceAsStream("/emails/ordemServico.template")))
			.put("ordemServico", this.ordemServico)
			.put("numberTool", new NumberTool())
			.put("locale", new Locale("pt", "BR"))
			.send();
		
		FacesUtil.addInfoMessage("Ordem de serviço enviado por e-mail com sucesso!");
	}
	
	
}
