/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prediksi.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import prediksi.boundary.InterfaceManager;
import prediksi.entity.Cuaca;

/**
 *
 * @author afrilia
 */
public class TsukamotoManager {

    double cerah, berawan, hujan_ringan, hujan;
    InterfaceManager InterfaceManager;
    private double cuaca;
    ArrayList<ArrayList> excel_dokumen;
    ArrayList<Cuaca> data_cuaca;
    ArrayList<ArrayList> rules;
    double[] f_anggota_lama;
    double[][] f_keanggotaan_cuaca, m_keanggotaan;
    String[] hasil_cuaca;
    double[] hasil_deffuzifikasi;
    double tingkat_akurasi;
    ArrayList <double[][]> m_keanggotaan_baru;

    public TsukamotoManager() {
        f_anggota_lama = new double[12];
        f_anggota_lama[0] = 26;
        f_anggota_lama[1] = 27.5;
        f_anggota_lama[2] = 29;
        f_anggota_lama[3] = 63;
        f_anggota_lama[4] = 75;
        f_anggota_lama[5] = 85;
        f_anggota_lama[6] = 1008.5;
        f_anggota_lama[7] = 1011;
        f_anggota_lama[8] = 1013;
        f_anggota_lama[9] = 2;
        f_anggota_lama[10] = 4;
        f_anggota_lama[11] = 6.5;
        cerah = 1;
        berawan = 2.0;
        hujan_ringan = 12.0;
        hujan = 20.0;
    }

    public void init_rules(ArrayList rule) {
        this.rules = rule;
        int i, j;
        for (ArrayList k : rules) {
            System.out.println(k);
        }
        System.out.println("");
    }

    public void init_cuaca(ArrayList<Cuaca> data_cuaca) {
        this.data_cuaca = data_cuaca;
    }

    public void do_fuzzyfikasi(int training, int testing) {
        //DecimalFormat format = new DecimalFormat("####,##");
        Cuaca cuaca;
        int count = training;
        f_keanggotaan_cuaca = new double[testing][12];
        for (int i = 0; i < testing; i++) {
//========================Fuzzyfikasi Suhu==========================================  
//      ++++++++++++++ Suhu Dingin ++++++++++++++++++++++++++++++++++++++++++++  
            if (data_cuaca.get(count).getSuhu() <= f_anggota_lama[0]) {
                f_keanggotaan_cuaca[i][0] = 1;
            } else if (data_cuaca.get(count).getSuhu() > f_anggota_lama[0] && data_cuaca.get(count).getSuhu() < f_anggota_lama[1]) {
                f_keanggotaan_cuaca[i][0] = (f_anggota_lama[1] - data_cuaca.get(count).getSuhu()) / (f_anggota_lama[1] - f_anggota_lama[0]);
            } else {
                f_keanggotaan_cuaca[i][0] = 0;
            }

//      +++++++++++++ Suhu Hangat ++++++++++++++++++++++++++++++++++++++++++++++                    
            if (data_cuaca.get(count).getSuhu() > f_anggota_lama[0] && data_cuaca.get(count).getSuhu() <= f_anggota_lama[1]) {
                f_keanggotaan_cuaca[i][1] = (data_cuaca.get(count).getSuhu() - f_anggota_lama[0]) / (f_anggota_lama[1] - f_anggota_lama[0]);
            } else if (data_cuaca.get(count).getSuhu() > f_anggota_lama[1] && data_cuaca.get(count).getSuhu() <= f_anggota_lama[2]) {
                f_keanggotaan_cuaca[i][1] = (f_anggota_lama[2] - data_cuaca.get(count).getSuhu()) / (f_anggota_lama[2] - f_anggota_lama[1]);
            } else {
                f_keanggotaan_cuaca[i][1] = 0;
            }

//      +++++++++++++ Suhu Panas ++++++++++++++++++++++++++++++++++++++++++++++                 
            if (data_cuaca.get(count).getSuhu() <= f_anggota_lama[1]) {
                f_keanggotaan_cuaca[i][2] = 0;
            } else if (data_cuaca.get(count).getSuhu() > f_anggota_lama[1] && data_cuaca.get(count).getSuhu() <= f_anggota_lama[2]) {
                f_keanggotaan_cuaca[i][2] = (data_cuaca.get(count).getSuhu() - f_anggota_lama[1]) / (f_anggota_lama[2] - f_anggota_lama[1]);
            } else {
                f_keanggotaan_cuaca[i][2] = 1;
            }

//      +++++++++++++ Kelembaban Dry ++++++++++++++++++++++++++++++++++++++++++++++ 
            if (data_cuaca.get(count).getKelembaban() <= f_anggota_lama[3]) {
                f_keanggotaan_cuaca[i][3] = 1;
            } else if (data_cuaca.get(count).getKelembaban() > f_anggota_lama[3] && data_cuaca.get(count).getKelembaban() < f_anggota_lama[4]) {
                f_keanggotaan_cuaca[i][3] = (f_anggota_lama[4] - data_cuaca.get(count).getKelembaban()) / (f_anggota_lama[4] - f_anggota_lama[3]);
            } else {
                f_keanggotaan_cuaca[i][3] = 0;
            }

//      +++++++++++++ Kelembaban Wet ++++++++++++++++++++++++++++++++++++++++++++++ 
            if (data_cuaca.get(count).getKelembaban() > f_anggota_lama[3] && data_cuaca.get(count).getKelembaban() <= f_anggota_lama[4]) {
                f_keanggotaan_cuaca[i][4] = (data_cuaca.get(count).getKelembaban() - f_anggota_lama[3]) / (f_anggota_lama[4] - f_anggota_lama[3]);
            } else if (data_cuaca.get(count).getKelembaban() > f_anggota_lama[4] && data_cuaca.get(count).getKelembaban() <= f_anggota_lama[5]) {
                f_keanggotaan_cuaca[i][4] = (f_anggota_lama[5] - data_cuaca.get(count).getKelembaban()) / (f_anggota_lama[5] - f_anggota_lama[4]);
            } else {
                f_keanggotaan_cuaca[i][4] = 0;
            }

//      +++++++++++++ Kelembaban Moist ++++++++++++++++++++++++++++++++++++++++++++++ 
            if (data_cuaca.get(count).getKelembaban() <= f_anggota_lama[4]) {
                f_keanggotaan_cuaca[i][5] = 0;
            } else if (data_cuaca.get(count).getKelembaban() > f_anggota_lama[4] && data_cuaca.get(count).getKelembaban() <= f_anggota_lama[5]) {
                f_keanggotaan_cuaca[i][5] = (data_cuaca.get(count).getKelembaban() - f_anggota_lama[4]) / (f_anggota_lama[5] - f_anggota_lama[4]);
            } else {
                f_keanggotaan_cuaca[i][5] = 1;
            }

//      +++++++++++++ Tekanan Udara Rendah ++++++++++++++++++++++++++++++++++++++++++++++ 
            if (data_cuaca.get(count).getTekanan_udara() <= f_anggota_lama[6]) {
                f_keanggotaan_cuaca[i][6] = 1;
            } else if (data_cuaca.get(count).getTekanan_udara() > f_anggota_lama[6] && data_cuaca.get(count).getTekanan_udara() < f_anggota_lama[7]) {
                f_keanggotaan_cuaca[i][6] = (f_anggota_lama[7] - data_cuaca.get(count).getTekanan_udara()) / (f_anggota_lama[7] - f_anggota_lama[6]);
            } else {
                f_keanggotaan_cuaca[i][6] = 0;
            }

//      +++++++++++++ Tekanan Udara Sedang ++++++++++++++++++++++++++++++++++++++++++++++ 
            if (data_cuaca.get(count).getTekanan_udara() > f_anggota_lama[6] && data_cuaca.get(count).getTekanan_udara() <= f_anggota_lama[7]) {
                f_keanggotaan_cuaca[i][7] = (data_cuaca.get(count).getTekanan_udara() - f_anggota_lama[6]) / (f_anggota_lama[7] - f_anggota_lama[6]);
            } else if (data_cuaca.get(count).getTekanan_udara() > f_anggota_lama[7] && data_cuaca.get(count).getTekanan_udara() <= f_anggota_lama[8]) {
                f_keanggotaan_cuaca[i][7] = (f_anggota_lama[8] - data_cuaca.get(count).getTekanan_udara()) / (f_anggota_lama[8] - f_anggota_lama[7]);
            } else {
                f_keanggotaan_cuaca[i][7] = 0;
            }

//      +++++++++++++ Tekanan Udara Tinggi ++++++++++++++++++++++++++++++++++++++++++++++ 
            if (data_cuaca.get(count).getTekanan_udara() <= f_anggota_lama[7]) {
                f_keanggotaan_cuaca[i][8] = 0;
            } else if (data_cuaca.get(count).getTekanan_udara() > f_anggota_lama[7] && data_cuaca.get(count).getTekanan_udara() <= f_anggota_lama[8]) {
                f_keanggotaan_cuaca[i][8] = (data_cuaca.get(count).getTekanan_udara() - f_anggota_lama[7]) / (f_anggota_lama[8] - f_anggota_lama[7]);
            } else {
                f_keanggotaan_cuaca[i][8] = 1;
            }

//      +++++++++++++ Kecapatan Angin Sedang ++++++++++++++++++++++++++++++++++++++++++++++ 
            if (data_cuaca.get(count).getKecepatan_angin() <= f_anggota_lama[9]) {
                f_keanggotaan_cuaca[i][9] = 1;
            } else if (data_cuaca.get(count).getKecepatan_angin() > f_anggota_lama[9] && data_cuaca.get(count).getKecepatan_angin() < f_anggota_lama[10]) {
                f_keanggotaan_cuaca[i][9] = (f_anggota_lama[10] - data_cuaca.get(count).getKecepatan_angin()) / (f_anggota_lama[10] - f_anggota_lama[9]);
            } else {
                f_keanggotaan_cuaca[i][9] = 0;
            }

//      +++++++++++++ Kecepatan Angin Kencang ++++++++++++++++++++++++++++++++++++++++++++++ 
            if (data_cuaca.get(count).getKecepatan_angin() > f_anggota_lama[9] && data_cuaca.get(count).getKecepatan_angin() <= f_anggota_lama[10]) {
                f_keanggotaan_cuaca[i][10] = (data_cuaca.get(count).getKecepatan_angin() - f_anggota_lama[9]) / (f_anggota_lama[10] - f_anggota_lama[9]);
            } else if (data_cuaca.get(count).getKecepatan_angin() > f_anggota_lama[10] && data_cuaca.get(count).getKecepatan_angin() <= f_anggota_lama[11]) {
                f_keanggotaan_cuaca[i][10] = (f_anggota_lama[11] - data_cuaca.get(count).getKecepatan_angin()) / (f_anggota_lama[11] - f_anggota_lama[10]);
            } else {
                f_keanggotaan_cuaca[i][10] = 0;
            }

//      +++++++++++++ Kecepatan Angin Sangat Kencang ++++++++++++++++++++++++++++++++++++++++++++++ 
            if (data_cuaca.get(count).getKecepatan_angin() <= f_anggota_lama[10]) {
                f_keanggotaan_cuaca[i][11] = 0;
            } else if (data_cuaca.get(count).getKecepatan_angin() > f_anggota_lama[10] && data_cuaca.get(count).getKecepatan_angin() <= f_anggota_lama[11]) {
                f_keanggotaan_cuaca[i][11] = (data_cuaca.get(count).getKecepatan_angin() - f_anggota_lama[10]) / (f_anggota_lama[11] - f_anggota_lama[10]);
            } else {
                f_keanggotaan_cuaca[i][11] = 1;
            }
            count++;
            //f_anggota_cuaca_cuaca.add(f_keanggotaan_cuaca);
        }
        System.out.println("Fuzzifikasi : ");
        for (int j = 0; j < testing; j++) {
           // System.out.print("Data " + (j + 1) + " ");
            for (int k = 0; k < 12; k++) {
                System.out.print(String.format("%.2f", f_keanggotaan_cuaca[j][k]).replace(",", ".") + " ");
            }
            System.out.println();
        }
    }

    public void do_hitung_fuzzy_tsukamoto(int training, int testing) {
        //DecimalFormat format = new DecimalFormat("####,##");
        double[][] f_anggota_cuaca;
        double tukar;

        ArrayList<Double[][]> listm_keanggotan;
        f_anggota_cuaca = new double[f_keanggotaan_cuaca.length][f_keanggotaan_cuaca[0].length];
        System.out.println("f_anggota_cuaca" + f_keanggotaan_cuaca.length);
        System.out.println("f_anggota_cuaca" + f_keanggotaan_cuaca[0].length);
        m_keanggotaan_baru = new ArrayList<>();
        hasil_cuaca = new String[testing];
        for (int i = 0; i < testing; i++) {
            for (int j = 0; j < 12; j++) {
                f_anggota_cuaca[i][j] = f_keanggotaan_cuaca[i][j];
            }
        }
        
        int a = 0;
        for (int i = 0; i < f_anggota_cuaca.length; i++) {
            m_keanggotaan = new double[rules.size()][rules.get(0).size()];
            //System.out.println("data ke : "+(i+1));
            //System.out.println("rule kolom : "+rules.get(0).size());
            for (int j = 1; j < rules.size(); j++) {
                double min = 10;
                for (int k = 0; k < rules.get(j).size(); k++) {
                    //if (l==1){
                    if (rules.get(j).get(k).equals("Cold")) {
                        m_keanggotaan[j][k] = f_anggota_cuaca[i][0];
                    } else if (rules.get(j).get(k).equals("Warm")) {
                        m_keanggotaan[j][k] = f_anggota_cuaca[i][1];
                    } else if (rules.get(j).get(k).equals("Hot")) {
                        m_keanggotaan[j][k] = f_anggota_cuaca[i][2];
                    }
                    //System.out.println("");
                    // }
                    // else if (l==2){
                    if (rules.get(j).get(k).equals("Dry")) {
                        m_keanggotaan[j][k] = f_anggota_cuaca[i][3];
                    } else if (rules.get(j).get(k).equals("Wet")) {
                        m_keanggotaan[j][k] = f_anggota_cuaca[i][4];
                    } else if (rules.get(j).get(k).equals("Moist")) {
                        m_keanggotaan[j][k] = f_anggota_cuaca[i][5];
                    }
                    //  }
                    //   else if (l==3){
                    if (rules.get(j).get(k).equals("Low")) {
                        m_keanggotaan[j][k] = f_anggota_cuaca[i][6];
                    } else if (rules.get(j).get(k).equals("Medium")) {
                        m_keanggotaan[j][k] = f_anggota_cuaca[i][7];
                    } else if (rules.get(j).get(k).equals("High")) {
                        m_keanggotaan[j][k] = f_anggota_cuaca[i][8];
                    }
                    // }
                    //  else if (l==4){
                    if (rules.get(j).get(k).equals("Sedang")) {
                        m_keanggotaan[j][k] = f_anggota_cuaca[i][9];
                    } else if (rules.get(j).get(k).equals("Kencang")) {
                        m_keanggotaan[j][k] = f_anggota_cuaca[i][10];
                    } else if (rules.get(j).get(k).equals("Skencang")) {
                        m_keanggotaan[j][k] = f_anggota_cuaca[i][11];
                    }

                    if (k == 4) {
                        m_keanggotaan[j][k] = min;
                        min = m_keanggotaan[j][k];
                        tukar = min;
                        min = m_keanggotaan[j][k];
                        m_keanggotaan[j][k] = tukar;
//                        System.out.println("min result : "+m_keanggotaan[j][k]);
                    } else {
                        if (min > m_keanggotaan[j][k]) {
                            min = m_keanggotaan[j][k];
                            tukar = min;
                            min = m_keanggotaan[j][k];
                            m_keanggotaan[j][k] = tukar;
                        }
//                        System.out.println("nilai min = " + min);
                    }
                    //System.out.println("Rule " + (j) + " : " + rules.get(j).get(k) + " f_anggota : " + m_keanggotaan[j][k]);
                }
            }
            m_keanggotaan_baru.add(m_keanggotaan);
        }
    }

    public double agregasi(int training, int testing) {
        //DecimalFormat format = new DecimalFormat("####,##");
        ArrayList<double[][]> listm_keanggotaan;
        double[][] m_keanggotan_rule;
        double total, z = 0, x1, x2, x3, x4;
        double tingkat_akurasi;

        tingkat_akurasi = 0;
        hasil_cuaca = new String[testing];

        x1 = 0;
        x2 = 0;
        x3 = 0;
        x4 = 0;

        
        System.out.println("m_keanggotaan rule1 : " + m_keanggotaan.length);
        System.out.println("m_keanggotaan rule2 : " + m_keanggotaan[0].length);
//        for (int a = 0; a < testing; a++) {
//            m_keanggotan_rule = m_keanggotaan_baru.get(a);
//            System.out.println("data ke" + (a+1));
//        for (int i = 0; i < m_keanggotaan.length; i++) {
//            for (int j = 0; j < m_keanggotaan[0].length; j++) {
//                m_keanggotan_rule[i][j] = m_keanggotaan[i][j];
//               // System.out.println("data ke " + i + " : " + m_keanggotan_rule[i][j]);
//            }
//            System.out.println("rule ke " + i + " : " + m_keanggotan_rule[i][4]);
//        }
//        }
        int count = training;
        for (int i = 0; i < testing; i++) {
            System.out.println("data ke : " + (i+1));
            total = 0;
            z = 0;
            m_keanggotan_rule = m_keanggotaan_baru.get(i);
            for (int j = 0; j < m_keanggotan_rule.length; j++) {//[82][4]
                //System.out.println("rule ke " + (j+1) + " : " + m_keanggotan_rule[j][4]);
                if (rules.get(j).get(4).equals("Rain")) {
                    z = (m_keanggotan_rule[j][4] * hujan);
                } else if (rules.get(j).get(4).equals("Light Rain")) {
                    z = (m_keanggotan_rule[j][4] * hujan_ringan);
                } else if (rules.get(j).get(4).equals("Cloudy")) {
                    z = (m_keanggotan_rule[j][4] * berawan);
                } else {
                    z = (m_keanggotan_rule[j][4] * cerah);
                }
                //System.out.println("keadaan cuaca : " + data_cuaca.get(count).getKeadaan_cuaca());
                //System.out.println("z :" +z);
                total = total + z;
            }
//            System.out.println("panjang rule = "+m_keanggotaan.length); 
            double temp = 0;

            for (int k = 0; k < m_keanggotan_rule.length; k++) {
//                System.out.println("m = "+m_keanggotaan[k][4]);
                temp = temp + m_keanggotan_rule[k][4];
            }

            System.out.println("total = " + total);
            System.out.println("temp = " + temp);
//            z = Double.parseDouble(String.format("%.2f", z).replace(",", "."));
//            total = Double.parseDouble(String.format("%.2f", total).replace(",", "."));

            z = total / temp;

            System.out.println("nilai z : " + z);
//            if (z <= 0 && z <= 4) {
//                if (z <= 2) {
//                    x1 = 1;
//                } else if (z > 2 && z < 4) {
//                    x1 = (4 - z) / (4 - 2);
//                } else {
//                    x1 = 0;
//                }
//            } if (z >= 2 && z <= 14) {
//                if (z > 2 && z <= 8) {
//                    x2 = (z - 2) / (8 - 2);
//                } else if (z > 8 && z <= 14) {
//                    x2 = (14 - z) / (14 - 8);
//                } else {
//                    x2 = 0;
//                }
//            } if (z > 6 && z <= 22) {
//                if (z > 6 && z <= 14) {
//                    x3 = (z - 6) / (14 - 6);
//                } else if (z > 14 && z <= 22) {
//                    x3 = (22 - z) / (22 - 14);
//                } else {
//                    x3 = 0;
//                }
//            } else {
//                if (z >= 14) {
//                    x4 = 0;
//                } else if (z > 14 && z <= 22) {
//                    x4 = (z - 14) / (22 / 14);
//                } else {
//                    x4 = 1;
//                }
//            }

            if (z >= 0 && z < 2) {
                if (z <= 0) {
                    x1 = 1;
                } else if (z > 0 && z < 2) {
                    x1 = (2 - z) / (2 - 0);
                } else {
                    x1 = 0;
                }
            } if (z >= 0 && z <= 12) {
                if (z > 0 && z <= 2) {
                    x2 = (z - 0) / (2 - 0);
                } else if (z > 2 && z < 12) {
                    x2 = (12 - z) / (12 - 2);
                } else {
                    x2 = 0;
                }
            } if (z > 2 && z <= 20) {
                if (z > 2 && z <= 12) {
                    x3 = (z - 2) / (12 - 2);
                } else if (z > 12 && z < 20) {
                    x3 = (20 - z) / (20 - 12);
                } else {
                    x3 = 0;
                }
            } if ( z > 12 && z < 50) {
                if (z > 12 && z <=20) {
                    x4 = (z - 12) / (20 - 12);
                } else if (z > 20 && z < 50) {
                    x4 = (50 - z) / (50 / 20);
                } else {
                    x4 = 1;
                }
            }

            if (x1 > x2 && x1 > x3 && x1 > x4) {
                hasil_cuaca[i] = "Sunny";

            } else if (x2 > x1 && x2 > x3 && x2 > x4) {
                hasil_cuaca[i] = "Cloudy";
            } else if (x3 > x1 && x3 > x2 && x3 > x4) {
                hasil_cuaca[i] = "Light Rain";
            } else {
                hasil_cuaca[i] = "Rain";
            }
            System.out.println(hasil_cuaca[i]);
            if (data_cuaca.get(count).getKeadaan_cuaca().equals(hasil_cuaca[i])) {
                tingkat_akurasi++;
            }
            count++;
        }
        tingkat_akurasi = (tingkat_akurasi / testing) * 100;
        tingkat_akurasi = Double.parseDouble(String.format("%.3f", tingkat_akurasi).replace(",", "."));
        System.out.println("tingkat akurasi = " + tingkat_akurasi);
        return tingkat_akurasi;
    }

    public String[] get_hasil_cuaca() {
        return hasil_cuaca;
    }

}
