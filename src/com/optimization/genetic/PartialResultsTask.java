package com.optimization.genetic;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;

@SuppressWarnings("rawtypes")
public class PartialResultsTask extends Task<ObservableList<XYChart.Data>> {

	@FXML
	public TextArea txtFlow;

	int sayiPopulasyon = 0, sayiJenerasyon = 0, oranCaprazlama = 0,
			oranMutasyon = 0;

	public PartialResultsTask() {

	}

	public PartialResultsTask(int sayiPopulasyon, int sayiJenerasyon,
			int oranCaprazlama, int oranMutasyon, TextArea txtFlow) {
		this.sayiPopulasyon = sayiPopulasyon;
		this.sayiJenerasyon = sayiJenerasyon;
		this.oranCaprazlama = oranCaprazlama;
		this.oranMutasyon = oranMutasyon;
		this.txtFlow = txtFlow;

	}

	@SuppressWarnings("unchecked")
	private ReadOnlyObjectWrapper<ObservableList> partialResults = new ReadOnlyObjectWrapper<>(
			this, "partialResults",
			FXCollections.observableArrayList(new ArrayList()));

	public final ObservableList getPartialResults() {
		return partialResults.get();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ObservableList<XYChart.Data> call() throws Exception {

		GeneticOPT myc = new GeneticOPT(sayiPopulasyon, oranCaprazlama,
				oranMutasyon);
		myc.baslangicToplumu();
		myc.uygunlukDegeri();
		myc.uygunlukSirala();

		txtFlow.appendText("--> Baþlangýç uygunluk deðeri: "
				+ myc.toplum.get(0).uygunluk + "\n");
		txtFlow.appendText("--> Baþlantýç toplumu en iyi bireyi: "
				+ Arrays.toString(myc.toplum.get(0).genKodu) + "\n");

		for (int i = 0; i < sayiJenerasyon; i++) {
			myc.secCaprazla();
			myc.uygunlukDegeri();
			myc.uygunlukSirala();

			final Double pick = myc.toplum.get(0).uygunluk;
			final Integer count = i;

			Platform.runLater(() -> {
				partialResults.get().add(new XYChart.Data<>(count, pick));

			});

		}

		txtFlow.appendText("--> Bitiþ uygunluk deðeri: "
				+ myc.toplum.get(0).uygunluk + "\n");
		txtFlow.appendText("--> Son toplumun en iyi bireyi: "
				+ Arrays.toString(myc.toplum.get(0).genKodu) + "\n");

		txtFlow.appendText("--> Genetik algoritma optimizasyonu tamamlandý. \n");
		return partialResults.get();
	}
}