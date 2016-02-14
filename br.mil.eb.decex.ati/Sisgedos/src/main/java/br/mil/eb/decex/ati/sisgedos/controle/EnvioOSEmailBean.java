package br.mil.eb.decex.ati.sisgedos.controle;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.mil.eb.decex.ati.sisgedos.modelo.OrdemServico;
import br.mil.eb.decex.ati.sisgedos.util.jsf.FacesUtil;
import br.mil.eb.decex.ati.sisgedos.util.mail.Mailer;
import com.outjected.email.api.MailMessage;

@Named
@RequestScoped
public class EnvioOSEmailBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Mailer mailer;
	
	@Inject
	@OrdemServicoEdicao
	private OrdemServico ordemServico;
	
	public void enviarOrdemServico() {
		MailMessage message = mailer.novaMensagem();
		
		message.to(this.ordemServico.getUsuario().getEmail())
			.subject("OrdemServico " + this.ordemServico.getId())
			.bodyHtml("<strong>Valor total:</strong> " + this.ordemServico.getValorTotal())
			.send();
		
		FacesUtil.addInfoMessage("Ordem de serviço enviado por e-mail com sucesso!");
	}
	
	
}
