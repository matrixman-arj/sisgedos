package br.mil.eb.decex.ati.sisgedos.controle;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.mil.eb.decex.ati.sisgedos.modelo.Usuario;
import br.mil.eb.decex.ati.sisgedos.repositorio.OrdensServico;
import br.mil.eb.decex.ati.sisgedos.seguranca.UsuarioLogado;
import br.mil.eb.decex.ati.sisgedos.seguranca.UsuarioSistema;

@Named
@RequestScoped
public class GraficoOSCriadasBean {
	
	private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM");
	
	@Inject
	private OrdensServico ordensServico;
	
	@Inject
	@UsuarioLogado
	private UsuarioSistema usuarioLogado;
	
	private CartesianChartModel model;
	
	public void preRender(){
		this.model = new CartesianChartModel();
		
		adicionarSerie("Todos os pedidos", null);
		adicionarSerie("Meus pedidos", usuarioLogado.getUsuario());
	}
	
	
	
	private void adicionarSerie(String rotulo, Usuario criadoPor) {
		Map<Date, BigDecimal> valoresPorData = this.ordensServico.valoresTotaisPorData(15, criadoPor);
		
		
		ChartSeries series = new ChartSeries(rotulo);
		
		for (Date data : valoresPorData.keySet()){
			series.set(DATE_FORMAT.format(data), valoresPorData.get(data));
		}
		
		this.model.addSeries(series);
	}

	public CartesianChartModel getModel() {
		return model;
	}
	
}
