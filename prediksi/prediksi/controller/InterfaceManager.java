package prediksi.controller;

import java.util.ArrayList;
import prediksi.entity.Cuaca;
import prediks.controller.PSOTsukamotoManager;
import prediks.controller.TsukamotoManager;
import prediksi.Controller.PSOTsukamotoManager;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *  
 *  @author afrilia 
 * 
 */
public class InterfaceManager {

	ArrayList<ArrayList> excel_dokumen;

	ArrayList<ArrayList> rules_dokumen;

	DokumenManager excel_manager;

	Cuaca data_cuaca;

	ArrayList<String> newList;

	String[][] rule;

	PSOTsukamotoManager TM;

	TsukamotoManager F;

	int len;

	int testing;

	int training;

	double akurasi;

	String[] hasilcuaca;

	DokumenManager excel_manager;

	Cuaca[] data_cuaca;

	TsukamotoManager F;

	PSOTsukamotoManager TM;

	public void Load_Data(JTable tbl_data, JTextField Namafile) {

	}

	public ArrayList<Cuaca> muat_data_excel(String path) {
		return null;
	}

	public ArrayList<ArrayList> muat_rules() {
		return null;
	}

	public void do_pso(int jumlah_swarm, double c1, double c2, int jumlah_iterasi) {

	}

	public void dofuzzysaja() {

	}

	public double get_akurasi() {
		return 0;
	}

	public String[] get_prediksi() {
		return null;
	}

}
