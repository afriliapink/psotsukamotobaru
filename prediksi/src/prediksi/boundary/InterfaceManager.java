/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prediksi.boundary;

import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import prediksi.Controller.PSOTsukamotoManager;
import prediksi.controller.DokumenManager;
import prediksi.controller.TsukamotoManager;
import prediksi.entity.Cuaca;

/**
 *
 * @author afrilia
 */
public class InterfaceManager {

    ArrayList<ArrayList> excel_dokumen;
    ArrayList<ArrayList> rules_dokumen;
    DokumenManager excel_manager;
    ArrayList<Cuaca> data_cuaca;
    ArrayList<String> newList;
    String[][] rule;
    PSOTsukamotoManager TM;
    TsukamotoManager F;
    int len,testing,training;
    
    public void Load_Data(JTable tbl_data, JTextField Namafile) {
//        ArrayList<Cuaca> data_cuaca;
//        data_cuaca = new ArrayList();
        String path;
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel FILES", "xlsx", "Excel");
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new java.io.File("."));
        int result = chooser.showOpenDialog(null);
        int i,len,count=1;
        
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            path = selectedFile.getAbsolutePath();
            Namafile.setText(selectedFile.getAbsolutePath());
            //Namafile.setText(selectedFile.getAbsolutePath());
            //Namafile.setText(selectedFile.getName());
            data_cuaca = muat_data_excel(path);

            DefaultTableModel cuaca_model = (DefaultTableModel) tbl_data.getModel();

            String[] data = new String[7];
            for (i = tbl_data.getRowCount() - 1; i >= 0; i--) {
                cuaca_model.removeRow(i);
            }
            
            len = data_cuaca.size();
            System.out.println("Banyak data = "+len);
            testing = (int) Math.round(0.3 * len);
            training = (len) - testing;
            System.out.println("Training = "+training +" \nTesting = "+testing);

            for (i = training; i < data_cuaca.size(); i++) {
                data[0] = String.valueOf(count);
                data[1] = data_cuaca.get(i).getTanggal();
                data[2] = String.valueOf((int) data_cuaca.get(i).getSuhu());
                data[3] = String.valueOf((int) data_cuaca.get(i).getKelembaban());
                data[4] = String.valueOf((int) data_cuaca.get(i).getTekanan_udara());
                data[5] = String.valueOf(data_cuaca.get(i).getKecepatan_angin());
                data[6] = data_cuaca.get(i).getKeadaan_cuaca();
                count++;
                cuaca_model.addRow(data);
            }            
            JOptionPane.showMessageDialog(null, "Data berhasil dimuat");

        } else {
            JOptionPane.showMessageDialog(null, "Muat data gagal", "Pesan Error", JOptionPane.ERROR_MESSAGE);
            //JOptionPane.showMessageDialog(null, "Muat Data Gagal");
        }
    }

    public ArrayList<Cuaca> muat_data_excel(String path) {
        int baris_excel, kolom_excel;
        Cuaca cuaca;

        excel_manager = new DokumenManager();
        excel_dokumen = excel_manager.getDataExcel(path);

        baris_excel = excel_dokumen.size()-1;
        kolom_excel = excel_dokumen.get(0).size();

        data_cuaca = new ArrayList<>();
        
//        System.out.println("baris : "+baris_excel);
//        System.out.println("kolom : "+kolom_excel);

        for (int i = 0; i < baris_excel; i++) {
            cuaca = new Cuaca();

            cuaca.setNo(Double.parseDouble(excel_dokumen.get(i + 1).get(0).toString()));
            cuaca.setTanggal(excel_dokumen.get(i + 1).get(1).toString());
            cuaca.setSuhu(Double.parseDouble(excel_dokumen.get(i + 1).get(2).toString()));
            cuaca.setKelembaban(Double.parseDouble(excel_dokumen.get(i + 1).get(3).toString()));
            cuaca.setTekanan_udara(Double.parseDouble(excel_dokumen.get(i + 1).get(4).toString()));
            cuaca.setKecepatan_angin(Double.parseDouble(excel_dokumen.get(i + 1).get(5).toString()));
            cuaca.setKeadaan_cuaca(excel_dokumen.get(i + 1).get(6).toString());
            data_cuaca.add(cuaca);
        }
        return data_cuaca;

    }

    public ArrayList<ArrayList> muat_rules() {
        ArrayList<ArrayList> dokumen;
        ArrayList<String> kolom_list;

        int baris_excel, kolom_excel;

        String path = System.getProperty("user.dir") + "\\data\\rulescuaca.xlsx";
        System.out.println(path);
        excel_manager = new DokumenManager();
        dokumen = excel_manager.getDataExcel(path);
        rules_dokumen = new ArrayList<>();

        baris_excel = dokumen.size();
        kolom_excel = dokumen.get(0).size();

        for (int i = 0; i < baris_excel; i++) {
            kolom_list = new ArrayList<>();
            for (int j = 0; j < kolom_excel; j++) {
                kolom_list.add(dokumen.get(i).get(j).toString());
            }
            rules_dokumen.add(kolom_list);
        }
        return rules_dokumen;
    }

    public void do_pso(int jumlah_swarm, double c1, double c2, int jumlah_iterasi, JLabel persentase, JTable tbl_data) {
        muat_rules();
        Cuaca cuaca;
        int i;

        TM = new PSOTsukamotoManager(jumlah_swarm, c1, c2, jumlah_iterasi);
        F=new TsukamotoManager();
//        TM.bangkit_swarm(jumlah_swarm);
        TM.init_cuaca(data_cuaca);
//        TM.do_fuzzyfikasi();
        TM.init_rules(rules_dokumen);
//        TM.do_hitung_fuzzy_tsukamoto();
//        TM.agregasi();
        TM.pso_fuzzzifikasi(training,testing);
        
        
        F.init_rules(rules_dokumen); //passing value to F
        F.init_cuaca(data_cuaca);
        F.set_anggota_lama(TM.get_anggota_lama());
        F.do_fuzzyfikasi(training,testing);
        F.do_hitung_fuzzy_tsukamoto(training,testing);
        persentase.setText(String.valueOf(F.agregasi(training,testing) + " %"));
        String[] hasilcuaca = F.get_hasil_cuaca();
                //persentase.setText(String.valueOf(TM.get_akurasi() + " %"));
                //String[] hasil_cuaca = TM.Get_Hasil_Cuaca();
        //show to table
                //DefaultTableModel cuaca_model = (DefaultTableModel) tbl_data.getModel();
        
//        len = hasil_cuaca.length;
//        System.out.println("Banyak data = "+len);
//        testing = (int) Math.round(0.3 * len);
//        training = (len) - testing;
//        System.out.println("Training = "+training +" /n Testing = "+testing);
            //    for(i=0;i<hasil_cuaca.length;i++){
//            System.out.println(hasil_cuaca[i]);
            //        cuaca_model.setValueAt(hasil_cuaca[i], i, 8);
            //}
            
            DefaultTableModel cuaca_model = (DefaultTableModel) tbl_data.getModel();
       
        for(i=0;i<hasilcuaca.length;i++){
//            System.out.println(hasilcuaca[i]);
            cuaca_model.setValueAt(hasilcuaca[i], i, 8);
        }
    }

    public void dofuzzysaja(JLabel persentase, JTable tbl_data) {
        muat_rules();
        //Cuaca cuaca;
        int i;
        F = new TsukamotoManager();
        F.init_rules(rules_dokumen); //passing value to F
        F.init_cuaca(data_cuaca);
        F.do_fuzzyfikasi(training,testing);
        F.do_hitung_fuzzy_tsukamoto(training,testing);
        //F.agregasi();
        persentase.setText(String.valueOf(F.agregasi(training,testing) + " %"));
        String[] hasilcuaca = F.get_hasil_cuaca();
        //show to table
        DefaultTableModel cuaca_model = (DefaultTableModel) tbl_data.getModel();
       
        for(i=0;i<hasilcuaca.length;i++){
//            System.out.println(hasilcuaca[i]);
            cuaca_model.setValueAt(hasilcuaca[i], i, 7);
        }
       
    }

}
