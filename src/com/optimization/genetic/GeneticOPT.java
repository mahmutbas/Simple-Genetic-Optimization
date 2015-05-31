package com.optimization.genetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class GeneticOPT {

	ArrayList<GData> toplum = new ArrayList<GData>();
	GData birey;
	int sayiPopulasyon = 0, oranCaprazlama = 0, oranMutasyon = 0;
	Random rn;

	// yapýlandýrýcý
	public GeneticOPT() {

	}

	// yapýlandýrýcý
	public GeneticOPT(int sayiPopulasyon, int oranCaprazlama, int oranMutasyon) {
		this.sayiPopulasyon = sayiPopulasyon;
		this.oranCaprazlama = oranCaprazlama;
		this.oranMutasyon = oranMutasyon;

	}

	public void baslangicToplumu() {

		int kod = 0;
		rn = new Random();

		for (int kromozom = 0; kromozom < sayiPopulasyon; kromozom++) {
			birey = new GData();
			birey.birey = kromozom;
			for (int gen = 0; gen < birey.genKodu.length; gen++) {
				kod = rn.nextInt(1000) + 1;
				if (Arrays.binarySearch(birey.genKodu, kod) <= 0) {
					birey.genKodu[gen] = kod;
				} else {
					gen--;
					continue;
				}
			}
			toplum.add(birey);

		}
	}

	public void uygunlukDegeri() {

		// tüm uygunluk deðerlerini sýfýrla
		for (int i = 0; i < toplum.size(); i++) {
			toplum.get(i).uygunluk = 0;
		}

		// Asal sayý ise +4 puan kazandýrýr
		for (int i = 0; i < toplum.size(); i++) {

			for (int j = 0; j < toplum.get(i).genKodu.length; j++) {
				if (asalmi(toplum.get(i).genKodu[j])) {
					toplum.get(i).uygunluk += 4;
				}
			}

		}

		// Birey üzerinde ayný genden var ise her bir benzerlik için -3 puan
		// götürür
		for (int t = 0; t < toplum.size(); t++) {
			for (int x = 0; x < toplum.get(t).genKodu.length - 1; x++) {
				for (int y = (x + 1); y < toplum.get(t).genKodu.length; y++) {
					if (toplum.get(t).genKodu[x] == toplum.get(t).genKodu[y]) {
						toplum.get(t).uygunluk -= 3;
						break;
					}
				}
			}
		}
		// Birey üzerindeki gen 0-100 aralýðýndaki bir sayý ise her bir gen için
		// +2 puan eklenir.
		for (int t = 0; t < toplum.size(); t++) {
			for (int x = 0; x < toplum.get(t).genKodu.length; x++) {
				if (toplum.get(t).genKodu[x] < 100) {
					toplum.get(t).uygunluk += 2;
				}
			}
		}

	}

	public boolean asalmi(int sayi) {

		for (int i = 2; i <= sayi; i++) {

			int kalan = sayi % i;

			if (kalan == 0) {

				return false;
			}

			if (i == (sayi - 1)) {

				return true;
			}

		}
		return false;
	}

	public void uygunlukSirala() {

		Collections.sort(toplum, new MyComparator());
	}

	public void secCaprazla() {

		int indexOfCrssStart = (int) ((100 - oranCaprazlama) * toplum.size() / 100);
		int anneBirey = 0;
		int babaBirey = 0;
		int caprazlamaIlkNokta = 0;
		int caprazlamaIkinciNokta = 0;

		rn = new Random();

		toplum.subList(indexOfCrssStart, (toplum.size() - 1)).clear();

		while (toplum.size() < sayiPopulasyon) {
			birey = new GData();
			babaBirey = rn.nextInt(indexOfCrssStart);
			while (true) {
				anneBirey = rn.nextInt(indexOfCrssStart);
				if (anneBirey != babaBirey) {
					break;
				}
			}// end of while

			// iki noktalý çaprazlama
			caprazlamaIlkNokta = rn.nextInt(birey.genKodu.length - 1);
			while (true) {
				caprazlamaIkinciNokta = rn.nextInt(birey.genKodu.length - 1);
				if (caprazlamaIlkNokta != caprazlamaIkinciNokta) {
					break;
				}
			}// end of while

	
			System.arraycopy(toplum.get(babaBirey).genKodu, 0, birey.genKodu,
					0, toplum.get(babaBirey).genKodu.length);
			birey.jenerasyon = 1;
			birey.birey = 1000;

			System.arraycopy(toplum.get(anneBirey).genKodu, Math.min(
					caprazlamaIlkNokta, caprazlamaIkinciNokta), birey.genKodu,
					Math.min(caprazlamaIlkNokta, caprazlamaIkinciNokta),
					(Math.max(caprazlamaIlkNokta, caprazlamaIkinciNokta) - Math
							.min(caprazlamaIlkNokta, caprazlamaIkinciNokta)));

			toplum.add(mutasyon(birey));

		}// end of while

	}

	public GData mutasyon(GData birey) {

		rn = new Random();
		for (int gen = 0; gen < birey.genKodu.length; gen++) {
			if ((oranMutasyon * 10) >= (rn.nextInt(1000) + 1)) {
				birey.genKodu[gen] = rn.nextInt(1000) + 1;
			}
		}
		return birey;
	}
}
