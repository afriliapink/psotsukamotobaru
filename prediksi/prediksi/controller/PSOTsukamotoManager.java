package prediksi.Controller;

import java.util.ArrayList;
import prediksi.entity.Cuaca;
import prediksi.controller.InterfaceManager;

/**
 *  
 *  @author afrilia 
 * 
 */
public class PSOTsukamotoManager {

	double[][] f_anggota_swarm;

	double[] f_anggota_lama;

	int min_suhu;

	int min_kelembaban;

	int min_tekanan_udara;

	int min_kecepatan_angin;

	int max_suhu;

	int max_kelembaban;

	int max_tekanan_udara;

	int max_kecepatan_angin;

	int cerah;

	int berawan;

	int hujan_ringan;

	int hujan;

	int jumlah_swarm;

	int jumlah_iterasi;

	int v0;

	int iterasi;

	int panjang_partikel;

	double wmax;

	double wmin;

	double r1;

	double r2;

	double c1;

	double c2;

	double w;

	double max_akurasi;

	double[][] v;

	double[][] pbest;

	double[][] pbest_before;

	double[] gbest;

	String[] Best_Hasil_Cuaca;

	private double cuaca;

	ArrayList<ArrayList> excel_dokumen;

	ArrayList<ArrayList> rules;

	ArrayList<double[][]> f_anggota_cuaca_swarm;

	ArrayList<double[][]> f_anggota_cuaca_baru;

	ArrayList<ArrayList> listm_keanggotaan_swarm;

	Cuaca[] data_cuaca;

	InterfaceManager InterfaceManager;

	public PSOTsukamotoManager(int jumlah_swarm, double c1, double c2, int jumlah_iterasi) {

	}

	public void init_data_cuaca(ArrayList<Cuaca> data_cuaca) {

	}

	public double[][] bangkit_swarm(int swarm) {
		return null;
	}

	public double[] bubbleshort(double[] arr) {
		return null;
	}

	public void init_cuaca(ArrayList<Cuaca> data_cuaca) {

	}

	public void do_fuzzyfikasi(int training, int testing) {

	}

	public void init_rules(ArrayList<ArrayList> rule) {

	}

	public void do_hitung_fuzzy_tsukamoto() {

	}

	public double[] agregasi() {
		return null;
	}

	public String[] Get_Hasil_Cuaca() {
		return null;
	}

	public void pso_fuzzzifikasi(int training, int testing) {

	}

	public double[] get_anggota_lama() {
		return null;
	}

	public double get_akurasi() {
		return 0;
	}

	public void menghitung_nilai_w(int current_iterasi) {

	}

	public void menghitung_nilai_r() {

	}

	public void init_kecepatan() {

	}

	public void init_pbest() {

	}

	public void init_gbest(double[] tingkat_akurasi) {

	}

	public void menentukan_pbest(double[] akurasi_before, double[] akurasi_current) {

	}

	public void menentukan_gbest(double[] akurasi_before, double[] akurasi_current) {

	}

	public void menghitung_v() {

	}

	public void menghitung_x() {

	}

}
