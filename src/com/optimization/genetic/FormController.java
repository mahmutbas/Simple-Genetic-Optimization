package com.optimization.genetic;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FormController implements Initializable {

	@FXML
	public TextField txtPopulasyon;

	@FXML
	public TextField txtJenerasyon;

	@FXML
	public TextField txtCaprazlama;

	@FXML
	public TextField txtMutasyon;

	@FXML
	public TextArea txtFlow;

	@FXML
	public Button btnRun;

	@FXML
	private AreaChart<?, ?> areaChart;
	@SuppressWarnings("rawtypes")
	private XYChart.Series serie;

	@FXML
	NumberAxis xAxis = new NumberAxis("Number saved", 1, 0, 1);

	@FXML
	NumberAxis yAxis = new NumberAxis("Calculated Value", 0, 1, 1);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtFlow.setText("--> Genetik algoritma ile optimizasyon programý açýldý...\n");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	public void btnRunHandle(ActionEvent event) {
		txtFlow.clear();
		txtFlow.appendText("--> Genetik algoritma optimizasyonu çalýþtýrýldý...\n");
		PartialResultsTask task = new PartialResultsTask(
				Integer.parseInt(txtPopulasyon.getText()),
				Integer.parseInt(txtJenerasyon.getText()),
				Integer.parseInt(txtCaprazlama.getText()),
				Integer.parseInt(txtMutasyon.getText()), txtFlow);
		serie = new XYChart.Series(task.getPartialResults());
		areaChart.getData().clear();
		areaChart.getData().add(serie);
		Thread t = new Thread(task);
		t.setDaemon(true);
		t.start();

	}

}
