package prediksi.controller;

import java.util.ArrayList;
import prediksi.entity.Cuaca;

/**
 *  
 *  @author afrilia 
 * 
 */
public class TsukamotoManager {

	double cerah;

	double berawan;

	double hujan_ringan;

	double hujan;

	private double cuaca;

	ArrayList<ArrayList> excel_dokumen;

	ArrayList<ArrayList> rules;

	double[] f_anggota_lama;

	double[][] f_keanggotaan_cuaca;

	double[][] m_keanggotaan;

	String[] hasil_cuaca;

	double[] hasil_deffuzifikasi;

	double tingkat_akurasi;

	ArrayList<double[][]> m_keanggotaan_baru;

	InterfaceManager InterfaceManager;

	Cuaca[] data_cuaca;

	public TsukamotoManager() {

	}

	public void init_rules(ArrayList rule) {

	}

	public void init_cuaca(ArrayList<Cuaca> data_cuaca) {

	}

	public void set_anggota_lama(double[] f_anggota_lama) {

	}

	public void do_fuzzyfikasi(int training, int testing) {

	}

	public void do_hitung_fuzzy_tsukamoto(int training, int testing) {

	}

	public double agregasi(int training, int testing) {
		return 0;
	}

	public String[] get_hasil_cuaca() {
		return null;
	}

}
