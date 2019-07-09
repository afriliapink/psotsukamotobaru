/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prediksi.Controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import prediksi.boundary.InterfaceManager;
import prediksi.entity.Cuaca;

/**
 *
 * @author afrilia
 */
public class psotsukamotocobacoba {

    double[][] f_anggota_swarm;
    double[] f_anggota_lama, max_akurasi;
    int min_suhu, min_kelembaban, min_tekanan_udara;
    int min_kecepatan_angin, max_suhu, max_kelembaban;
    int max_tekanan_udara, max_kecepatan_angin;
    int cerah, berawan, hujan_ringan, hujan;
    int jumlah_swarm, jumlah_iterasi, v0, iterasi;
    double wmax, wmin, r1, r2, c1, c2;
    double w;
    double[][] v;
    double[][] pbest, pbest_before;
    double[] gbest;
    String[] Best_Hasil_Cuaca;
    InterfaceManager InterfaceManager;
    private double cuaca;
    ArrayList<ArrayList> excel_dokumen;
    ArrayList<Cuaca> data_cuaca;
    ArrayList<ArrayList> rules;
    ArrayList<double[][]> f_anggota_cuaca_swarm;
    ArrayList<ArrayList> listm_keanggotaan_swarm;

    public psotsukamotocobacoba(int jumlah_swarm, double c1, double c2, int jumlah_iterasi) {
        f_anggota_lama = new double[12];
        f_anggota_lama[0] = 26;
        f_anggota_lama[1] = 27.5;
        f_anggota_lama[2] = 29;
        f_anggota_lama[3] = 65;
        f_anggota_lama[4] = 75;
        f_anggota_lama[5] = 85;
        f_anggota_lama[6] = 1008.5;
        f_anggota_lama[7] = 1011;
        f_anggota_lama[8] = 1013.5;
        f_anggota_lama[9] = 2;
        f_anggota_lama[10] = 4.5;
        f_anggota_lama[11] = 7;
        min_suhu = 24;
        min_kelembaban = 63;
        min_tekanan_udara = 1007;
        min_kecepatan_angin = 0;
        max_suhu = 32;
        max_kelembaban = 100;
        max_tekanan_udara = 1016;
        max_kecepatan_angin = 15;
        cerah = 2;
        berawan = 8;
        hujan_ringan = 22;
        hujan = 14;
        v0 = 0;
        wmax = 0.9;
        wmin = 0.4;
        this.jumlah_swarm = jumlah_swarm;
        this.jumlah_iterasi = jumlah_iterasi;
        this.c1 = c1;
        this.c2 = c2;
    }

    public void init_data_cuaca(ArrayList<Cuaca> data_cuaca) {
        this.data_cuaca = data_cuaca;
    }

    //===================================================nilai x baru===================================================
    public double[][] bangkit_swarm(int swarm) {
        Random r = new Random();
        double wk = r.nextDouble();
        int pilih;
        f_anggota_swarm = new double[swarm][12];
        double[] temp, temp1;
        DecimalFormat format = new DecimalFormat("####,####");

        for (int i = 0; i < swarm; i++) {
            for (int j = 0; j < 12; j++) {
                //x suhu baru
                pilih = r.nextInt(2);
                if (j < 3) {
                    if (pilih == 0) {
                        f_anggota_swarm[i][j] = Double.valueOf(format.format(f_anggota_lama[j] + (max_suhu - f_anggota_lama[j]) * wk));
                    } else {
                        f_anggota_swarm[i][j] = Double.valueOf(format.format(f_anggota_lama[j] - (f_anggota_lama[j] - min_suhu) * wk));
                    }
                    if (j == 2) {
                        temp = new double[3];
                        temp1 = new double[3];

                        for (int k = 0; k < temp.length; k++) {
                            temp[k] = f_anggota_swarm[i][k];
                        }

                        temp1 = bubbleshort(temp);

                        for (int k = 0; k < temp1.length; k++) {
                            f_anggota_swarm[i][k] = temp1[k];
                        }
                    }
                } //x kelembaban baru
                else if (j >= 3 && j < 6) {
                    if (pilih == 0) {
                        f_anggota_swarm[i][j] = Double.valueOf(format.format(f_anggota_lama[j] + (max_kelembaban - f_anggota_lama[j]) * wk));
                    } else {
                        f_anggota_swarm[i][j] = Double.valueOf(format.format(f_anggota_lama[j] - (f_anggota_lama[j] - min_kelembaban) * wk));
                    }
                    if (j == 5) {
                        temp = new double[3];
                        temp1 = new double[3];

                        for (int k = 0; k < temp.length; k++) {
                            temp[k] = f_anggota_swarm[i][k + 3];
                        }

                        temp1 = bubbleshort(temp);

                        for (int k = 0; k < temp1.length; k++) {
                            f_anggota_swarm[i][k + 3] = temp1[k];
                        }
                    }
                } //x tekanan udara baru
                else if (j >= 6 && j < 9) {
                    if (pilih == 0) {
                        f_anggota_swarm[i][j] = Double.valueOf(format.format(f_anggota_lama[j] + (max_tekanan_udara - f_anggota_lama[j]) * wk));
                    } else {
                        f_anggota_swarm[i][j] = Double.valueOf(format.format(f_anggota_lama[j] - (f_anggota_lama[j] - min_tekanan_udara) * wk));
                    }
                    if (j == 8) {
                        temp = new double[3];
                        temp1 = new double[3];

                        for (int k = 0; k < temp.length; k++) {
                            temp[k] = f_anggota_swarm[i][k + 6];
                        }

                        temp1 = bubbleshort(temp);

                        for (int k = 0; k < temp1.length; k++) {
                            f_anggota_swarm[i][k + 6] = temp1[k];
                        }
                    }
                } //x kecepatan angin baru
                else {
                    if (pilih == 0) {
                        f_anggota_swarm[i][j] = Double.valueOf(format.format(f_anggota_lama[j] + (max_kecepatan_angin - f_anggota_lama[j]) * wk));
                    } else {
                        f_anggota_swarm[i][j] = Double.valueOf(format.format(f_anggota_lama[j] - (f_anggota_lama[j] - min_kecepatan_angin) * wk));
                    }
                    if (j == 11) {
                        temp = new double[3];
                        temp1 = new double[3];

                        for (int k = 0; k < temp.length; k++) {
                            temp[k] = f_anggota_swarm[i][k + 9];
                        }

                        temp1 = bubbleshort(temp);

                        for (int k = 0; k < temp1.length; k++) {
                            f_anggota_swarm[i][k + 9] = temp1[k];
                        }
                    }
                }
            }
        }
        for (int i = 0; i < swarm; i++) {
            System.out.print("Swarm " + (i + 1) + " : ");
            for (int j = 0; j < 12; j++) {
                System.out.print(f_anggota_swarm[i][j] + " ");
            }
            System.out.println();
        }
        return f_anggota_swarm;
    }

    public double[] bubbleshort(double arr[]) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    double temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        return arr;
    }

    public void init_cuaca(ArrayList<Cuaca> data_cuaca) {
        this.data_cuaca = data_cuaca;
    }

    public void do_fuzzyfikasi() {
        double[][] f_keanggotaan_cuaca;
        DecimalFormat format = new DecimalFormat("####,##");
        Cuaca cuaca;

        f_anggota_cuaca_swarm = new ArrayList<>();

//+++++++++++++++ Perulangan untuk setiap populasi pada setiap data ++++++++++++++++            
        for (int i = 0; i < jumlah_swarm; i++) {
            f_keanggotaan_cuaca = new double[data_cuaca.size()][12];
            for (int j = 0; j < data_cuaca.size(); j++) {
//                cuaca = data_cuaca.get(i);

//========================Fuzzyfikasi Suhu==========================================  
//      ++++++++++++++ Suhu Dingin ++++++++++++++++++++++++++++++++++++++++++++  
                if (data_cuaca.get(j).getSuhu() <= f_anggota_swarm[i][0]) {
                    f_keanggotaan_cuaca[j][0] = 1;
                } else if (data_cuaca.get(j).getSuhu() > f_anggota_swarm[i][0] && data_cuaca.get(j).getSuhu() < f_anggota_swarm[i][1]) {
                    f_keanggotaan_cuaca[j][0] = Double.parseDouble(String.format("%.2f", (f_anggota_swarm[i][1] - data_cuaca.get(j).getSuhu()) / (f_anggota_swarm[i][1] - f_anggota_swarm[i][0])).replace(",", "."));
                } else {
                    f_keanggotaan_cuaca[j][0] = 0;
                }

//      +++++++++++++ Suhu Hangat ++++++++++++++++++++++++++++++++++++++++++++++                    
                if (data_cuaca.get(j).getSuhu() > f_anggota_swarm[i][0] && data_cuaca.get(j).getSuhu() <= f_anggota_swarm[i][1]) {
                    f_keanggotaan_cuaca[j][1] = Double.parseDouble(String.format("%.2f", (data_cuaca.get(j).getSuhu() - f_anggota_swarm[i][0]) / (f_anggota_swarm[i][1] - f_anggota_swarm[i][0])).replace(",", "."));
                } else if (data_cuaca.get(j).getSuhu() > f_anggota_swarm[i][1] && data_cuaca.get(j).getSuhu() <= f_anggota_swarm[i][2]) {
                    f_keanggotaan_cuaca[j][1] = Double.parseDouble(String.format("%.2f", (f_anggota_swarm[i][2] - data_cuaca.get(j).getSuhu()) / (f_anggota_swarm[i][2] - f_anggota_swarm[i][1])).replace(",", "."));
                } else {
                    f_keanggotaan_cuaca[j][1] = 0;
                }

//      +++++++++++++ Suhu Panas ++++++++++++++++++++++++++++++++++++++++++++++                 
                if (data_cuaca.get(j).getSuhu() <= f_anggota_swarm[i][1]) {
                    f_keanggotaan_cuaca[j][2] = 0;
                } else if (data_cuaca.get(j).getSuhu() > f_anggota_swarm[i][1] && data_cuaca.get(j).getSuhu() <= f_anggota_swarm[i][2]) {
                    f_keanggotaan_cuaca[j][2] = Double.parseDouble(String.format("%.2f", (data_cuaca.get(j).getSuhu() - f_anggota_swarm[i][1]) / (f_anggota_swarm[i][2] - f_anggota_swarm[i][1])).replace(",", "."));
                } else {
                    f_keanggotaan_cuaca[j][2] = 1;
                }

//      +++++++++++++ Kelembaban Dry ++++++++++++++++++++++++++++++++++++++++++++++ 
                if (data_cuaca.get(j).getKelembaban() <= f_anggota_swarm[i][3]) {
                    f_keanggotaan_cuaca[j][3] = 1;
                } else if (data_cuaca.get(j).getKelembaban() > f_anggota_swarm[i][3] && data_cuaca.get(j).getKelembaban() < f_anggota_swarm[i][4]) {
                    f_keanggotaan_cuaca[j][3] = Double.parseDouble(String.format("%.2f", (f_anggota_swarm[i][4] - data_cuaca.get(j).getKelembaban()) / (f_anggota_swarm[i][4] - f_anggota_swarm[i][3])).replace(",", "."));
                } else {
                    f_keanggotaan_cuaca[j][3] = 0;
                }

//      +++++++++++++ Kelembaban Wet ++++++++++++++++++++++++++++++++++++++++++++++ 
                if (data_cuaca.get(j).getKelembaban() > f_anggota_swarm[i][3] && data_cuaca.get(j).getKelembaban() <= f_anggota_swarm[i][4]) {
                    f_keanggotaan_cuaca[j][4] = Double.parseDouble(String.format("%.2f", (data_cuaca.get(j).getKelembaban() - f_anggota_swarm[i][3]) / (f_anggota_swarm[i][4] - f_anggota_swarm[i][3])).replace(",", "."));
                } else if (data_cuaca.get(j).getKelembaban() > f_anggota_swarm[i][4] && data_cuaca.get(j).getKelembaban() <= f_anggota_swarm[i][5]) {
                    f_keanggotaan_cuaca[j][4] = Double.parseDouble(String.format("%.2f", (f_anggota_swarm[i][5] - data_cuaca.get(j).getKelembaban()) / (f_anggota_swarm[i][5] - f_anggota_swarm[i][4])).replace(",", "."));
                } else {
                    f_keanggotaan_cuaca[j][4] = 0;
                }

//      +++++++++++++ Kelembaban Moist ++++++++++++++++++++++++++++++++++++++++++++++ 
                if (data_cuaca.get(j).getKelembaban() <= f_anggota_swarm[i][4]) {
                    f_keanggotaan_cuaca[j][5] = 0;
                } else if (data_cuaca.get(j).getKelembaban() > f_anggota_swarm[i][4] && data_cuaca.get(j).getKelembaban() <= f_anggota_swarm[i][5]) {
                    f_keanggotaan_cuaca[j][5] = Double.parseDouble(String.format("%.2f", (data_cuaca.get(j).getKelembaban() - f_anggota_swarm[i][4]) / (f_anggota_swarm[i][5] - f_anggota_swarm[i][4])).replace(",", "."));
                } else {
                    f_keanggotaan_cuaca[j][5] = 1;
                }

//      +++++++++++++ Tekanan Udara Rendah ++++++++++++++++++++++++++++++++++++++++++++++ 
                if (data_cuaca.get(j).getTekanan_udara() <= f_anggota_swarm[i][6]) {
                    f_keanggotaan_cuaca[j][6] = 1;
                } else if (data_cuaca.get(j).getTekanan_udara() > f_anggota_swarm[i][6] && data_cuaca.get(j).getTekanan_udara() < f_anggota_swarm[i][7]) {
                    f_keanggotaan_cuaca[j][6] = Double.parseDouble(String.format("%.2f", (f_anggota_swarm[i][7] - data_cuaca.get(j).getTekanan_udara()) / (f_anggota_swarm[i][7] - f_anggota_swarm[i][6])).replace(",", "."));
                } else {
                    f_keanggotaan_cuaca[j][6] = 0;
                }

//      +++++++++++++ Tekanan Udara Sedang ++++++++++++++++++++++++++++++++++++++++++++++ 
                if (data_cuaca.get(j).getTekanan_udara() > f_anggota_swarm[i][6] && data_cuaca.get(j).getTekanan_udara() <= f_anggota_swarm[i][7]) {
                    f_keanggotaan_cuaca[j][7] = Double.parseDouble(String.format("%.2f", (data_cuaca.get(j).getTekanan_udara() - f_anggota_swarm[i][6]) / (f_anggota_swarm[i][7] - f_anggota_swarm[i][6])).replace(",", "."));
                } else if (data_cuaca.get(j).getTekanan_udara() > f_anggota_swarm[i][7] && data_cuaca.get(j).getTekanan_udara() <= f_anggota_swarm[i][8]) {
                    f_keanggotaan_cuaca[j][7] = Double.parseDouble(String.format("%.2f", (f_anggota_swarm[i][8] - data_cuaca.get(j).getTekanan_udara()) / (f_anggota_swarm[i][8] - f_anggota_swarm[i][7])).replace(",", "."));
                } else {
                    f_keanggotaan_cuaca[j][7] = 0;
                }

//      +++++++++++++ Tekanan Udara Tinggi ++++++++++++++++++++++++++++++++++++++++++++++ 
                if (data_cuaca.get(j).getTekanan_udara() <= f_anggota_swarm[i][7]) {
                    f_keanggotaan_cuaca[j][8] = 0;
                } else if (data_cuaca.get(j).getTekanan_udara() > f_anggota_swarm[i][7] && data_cuaca.get(j).getTekanan_udara() <= f_anggota_swarm[i][8]) {
                    f_keanggotaan_cuaca[j][8] = Double.parseDouble(String.format("%.2f", (data_cuaca.get(j).getTekanan_udara() - f_anggota_swarm[i][7]) / (f_anggota_swarm[i][8] - f_anggota_swarm[i][7])).replace(",", "."));
                } else {
                    f_keanggotaan_cuaca[j][8] = 1;
                }

//      +++++++++++++ Kecapatan Angin Sedang ++++++++++++++++++++++++++++++++++++++++++++++ 
                if (data_cuaca.get(j).getKecepatan_angin() <= f_anggota_swarm[i][9]) {
                    f_keanggotaan_cuaca[j][9] = 1;
                } else if (data_cuaca.get(j).getKecepatan_angin() > f_anggota_swarm[i][9] && data_cuaca.get(j).getKecepatan_angin() < f_anggota_swarm[i][10]) {
                    f_keanggotaan_cuaca[j][9] = Double.parseDouble(String.format("%.2f", (f_anggota_swarm[i][10] - data_cuaca.get(j).getKecepatan_angin()) / (f_anggota_swarm[i][10] - f_anggota_swarm[i][9])).replace(",", "."));
                } else {
                    f_keanggotaan_cuaca[j][9] = 0;
                }

//      +++++++++++++ Kecepatan Angin Kencang ++++++++++++++++++++++++++++++++++++++++++++++ 
                if (data_cuaca.get(j).getKecepatan_angin() > f_anggota_swarm[i][9] && data_cuaca.get(j).getKecepatan_angin() <= f_anggota_swarm[i][10]) {
                    f_keanggotaan_cuaca[j][10] = Double.parseDouble(String.format("%.2f", (data_cuaca.get(j).getKecepatan_angin() - f_anggota_swarm[i][9]) / (f_anggota_swarm[i][10] - f_anggota_swarm[i][9])).replace(",", "."));
                } else if (data_cuaca.get(j).getKecepatan_angin() > f_anggota_swarm[i][10] && data_cuaca.get(j).getKecepatan_angin() <= f_anggota_swarm[i][11]) {
                    f_keanggotaan_cuaca[j][10] = Double.parseDouble(String.format("%.2f", (f_anggota_swarm[i][11] - data_cuaca.get(j).getKecepatan_angin()) / (f_anggota_swarm[i][11] - f_anggota_swarm[i][10])).replace(",", "."));
                } else {
                    f_keanggotaan_cuaca[j][10] = 0;
                }

//      +++++++++++++ Kecepatan Angin Sangat Kencang ++++++++++++++++++++++++++++++++++++++++++++++ 
                if (data_cuaca.get(j).getKecepatan_angin() <= f_anggota_swarm[i][10]) {
                    f_keanggotaan_cuaca[j][11] = 0;
                } else if (data_cuaca.get(j).getKecepatan_angin() > f_anggota_swarm[i][10] && data_cuaca.get(j).getKecepatan_angin() <= f_anggota_swarm[i][11]) {
                    f_keanggotaan_cuaca[j][11] = Double.parseDouble(String.format("%.2f", (data_cuaca.get(j).getKecepatan_angin() - f_anggota_swarm[i][10]) / (f_anggota_swarm[i][11] - f_anggota_swarm[i][10])).replace(",", "."));
                } else {
                    f_keanggotaan_cuaca[j][11] = 1;
                }
            }
            System.out.println("swarm ke-" + (i + 1) + " ");
            System.out.println("Fuzzifikasi : ");
            for (int j = 0; j < data_cuaca.size(); j++) {
                System.out.print("Data " + (j + 1) + " ");
                for (int k = 0; k < 12; k++) {
                    System.out.print(f_keanggotaan_cuaca[j][k] + " ");
                }
                System.out.println();
            }
            f_anggota_cuaca_swarm.add(f_keanggotaan_cuaca);
        }
        System.out.println();
    }

    public void init_rules(ArrayList<ArrayList> rule) {
        this.rules = rule;
    }

    public void do_hitung_fuzzy_tsukamoto() {
        //rules = new ArrayList<>();
        double[][] m_keanggotaan, f_anggota_cuaca;
        double[][] f_keanggotaan_cuaca;
        ArrayList<double[][]> listm_keanggotaan;
        double tukar;

        listm_keanggotaan_swarm = new ArrayList<>();

        for (int i = 0; i < f_anggota_cuaca_swarm.size(); i++) {
            f_anggota_cuaca = f_anggota_cuaca_swarm.get(i);
            listm_keanggotaan = new ArrayList<>();

            for (int j = 0; j < f_anggota_cuaca.length; j++) {
                m_keanggotaan = new double[rules.size()][rules.get(0).size()];

                for (int k = 0; k < rules.size(); k++) {
                    double min = 10;
                    for (int l = 0; l < rules.get(k).size(); l++) {
                        //if (l==1){
                        if (rules.get(k).get(l).equals("Cold")) {
                            m_keanggotaan[k][l] = f_anggota_cuaca[j][0];
                        } else if (rules.get(k).get(l).equals("Warm")) {
                            m_keanggotaan[k][l] = f_anggota_cuaca[j][1];
                        } else if (rules.get(k).get(l).equals("Hot")) {
                            m_keanggotaan[k][l] = f_anggota_cuaca[j][2];
                        }
                        //System.out.println("");
                        // }
                        // else if (l==2){
                        if (rules.get(k).get(l).equals("Dry")) {
                            m_keanggotaan[k][l] = f_anggota_cuaca[j][3];
                        } else if (rules.get(k).get(l).equals("Wet")) {
                            m_keanggotaan[k][l] = f_anggota_cuaca[j][4];
                        } else if (rules.get(k).get(l).equals("Moist")) {
                            m_keanggotaan[k][l] = f_anggota_cuaca[j][5];
                        }
                        //  }
                        //   else if (l==3){
                        if (rules.get(k).get(l).equals("Low")) {
                            m_keanggotaan[k][l] = f_anggota_cuaca[j][6];
                        } else if (rules.get(k).get(l).equals("Medium")) {
                            m_keanggotaan[k][l] = f_anggota_cuaca[j][7];
                        } else if (rules.get(k).get(l).equals("High")) {
                            m_keanggotaan[k][l] = f_anggota_cuaca[j][8];
                        }
                        // }
                        //  else if (l==4){
                        if (rules.get(k).get(l).equals("Sedang")) {
                            m_keanggotaan[k][l] = f_anggota_cuaca[j][9];
                        } else if (rules.get(k).get(l).equals("Kencang")) {
                            m_keanggotaan[k][l] = f_anggota_cuaca[j][10];
                        } else if (rules.get(k).get(l).equals("Skencang")) {
                            m_keanggotaan[k][l] = f_anggota_cuaca[j][11];
                        }

                        if (l == 4) {
                            m_keanggotaan[k][1] = min;
                            min = m_keanggotaan[k][1];

                            tukar = min;
                            min = m_keanggotaan[k][l];
                            m_keanggotaan[k][l] = tukar;
                            //System.out.println("min result : "+m_keanggotaan[k][l]);
                        } else {

                            if (min > m_keanggotaan[k][l]) {
                                min = m_keanggotaan[k][l];
                                tukar = min;
                                min = m_keanggotaan[k][l];
                                m_keanggotaan[k][l] = tukar;
                            }
                            //System.out.println("nilai min" + min);
                        }
                        //System.out.println("Rule "+(k+1)+" : "+rules.get(k).get(l)+" f_anggota : "+m_keanggotaan[k][l]);
                    }
                }
                listm_keanggotaan.add(m_keanggotaan);
            }

            listm_keanggotaan_swarm.add(listm_keanggotaan);
        }

        //System.out.println("daftar rule : "+rules.size());
    }

    public double[] agregasi() {
        Cuaca cuaca;
        double[] tingkat_akurasi;
        double[][] m_keanggotaan, f_anggota_cuaca;
        double[][] f_keanggotaan_cuaca;
        ArrayList<double[][]> listm_keanggotaan;
        String[][] hasil_cuaca;
        double[][] m_keanggotan_rule;
        double total, z, x1, x2, x3, x4;

        hasil_cuaca = new String[listm_keanggotaan_swarm.size()][listm_keanggotaan_swarm.get(0).size()];
        tingkat_akurasi = new double[jumlah_swarm];

        for (int i = 0; i < listm_keanggotaan_swarm.size(); i++) {
            listm_keanggotaan = listm_keanggotaan_swarm.get(i);

            tingkat_akurasi[i] = 0;
            for (int j = 0; j < listm_keanggotaan.size(); j++) {
                m_keanggotan_rule = listm_keanggotaan.get(j);

//                System.out.println("matriks keanggotaan data ke "+(j+1)+" popukasi ke "+(i+1)+" : ");
//                for (int h = 0; h < m_keanggotan_rule.length; h++) {
//                    for (int m = 0; m < m_keanggotan_rule[h].length; m++) {
//                        System.out.print(m_keanggotan_rule[h][m]+" ");
//                    }
//                    System.out.println();
//                }
                total = 0;
                z = 0;
                x1 = 0;
                x2 = 0;
                x3 = 0;
                x4 = 0;
                for (int k = 0; k < m_keanggotan_rule.length; k++) {
                    if (data_cuaca.get(j).getKeadaan_cuaca().equals("Rain")) {
                        z = z + (m_keanggotan_rule[k][4] * hujan);
                    } else if (data_cuaca.get(j).getKeadaan_cuaca().equals("Light rain")) {
                        z = z + (m_keanggotan_rule[k][4] * hujan_ringan);
                    } else if (data_cuaca.get(j).getKeadaan_cuaca().equals("Cloudy")) {
                        z = z + (m_keanggotan_rule[k][4] * berawan);
                    } else {
                        z = z + (m_keanggotan_rule[k][4] * cerah);
                    }

                    total = total + m_keanggotan_rule[k][4];
                }

                z = Double.parseDouble(String.format("%.2f", z).replace(",", "."));
                total = Double.parseDouble(String.format("%.2f", total).replace(",", "."));

                System.out.println("nilai z : " + z);
                System.out.println("nilai total : " + total);

                z = z / total;
                System.out.println("nilai z : " + z);

                if (z <= 0 && z <= 4) {
                    if (z <= 2) {
                        x1 = 1;
                    } else if (z > 2 && z < 4) {
                        x1 = (4 - z) / (4 - 2);
                    } else {
                        x1 = 0;
                    }
                } else if (z >= 2 && z <= 14) {
                    if (z > 2 && z <= 8) {
                        x2 = (z - 2) / (8 - 2);
                    } else if (z > 8 && z <= 14) {
                        x2 = (14 - z) / (14 - 8);
                    } else {
                        x2 = 0;
                    }
                } else if (z > 6 && z <= 22) {
                    if (z > 6 && z <= 14) {
                        x3 = (z - 6) / (14 - 6);
                    } else if (z > 14 && z <= 22) {
                        x3 = (22 - z) / (22 - 14);
                    } else {
                        x3 = 0;
                    }
                } else {
                    if (z >= 14) {
                        x4 = 0;
                    } else if (z > 14 && z <= 22) {
                        x4 = (z - 14) / (22 / 14);
                    } else {
                        x4 = 1;
                    }
                }

                if (x1 > x2 && x1 > x3 && x1 > x4) {
                    hasil_cuaca[i][j] = "Sunny";
                } else if (x2 > x1 && x2 > x3 && x2 > x4) {
                    hasil_cuaca[i][j] = "Cloudy";
                } else if (x3 > x1 && x3 > x2 && x3 > x4) {
                    hasil_cuaca[i][j] = "Light Rain";
                } else {
                    hasil_cuaca[i][j] = "Rain";
                }
                System.out.println(hasil_cuaca[i][j]);
                if (data_cuaca.get(j).getKeadaan_cuaca().equals(hasil_cuaca[i][j])) {
                    tingkat_akurasi[i]++;
                }
            }
            tingkat_akurasi[i] = (tingkat_akurasi[i] / data_cuaca.size()) * 100;
            System.out.println("tingkat akurasi = " + tingkat_akurasi[i]);
        }
        
        int i,j, tukar3, index=0;
        int length=0,counter=0;
        double max=0;
        double tukar2;
        
//        if (min > m_keanggotaan[k][l]) {
//                                min = m_keanggotaan[k][l];
//                                tukar = min;
//                                min = m_keanggotaan[k][l];
//                                m_keanggotaan[k][l] = tukar;
        
        for(i=1;i<tingkat_akurasi.length;i++){
            if (tingkat_akurasi[i] > max) {
                tingkat_akurasi[i] = max;
                tukar2 = tingkat_akurasi[i];
                tingkat_akurasi[i] = max;
                tukar3 = i;
                i = index;
                index = tukar3;
            }
            
            
            
//            if(tingkat_akurasi[i] > max){
//                max = tingkat_akurasi[i];
//                index = i;
 //               System.out.println("index : "+index);
//            }
        }
        
        
        System.out.println("Best in = "+index);
        length = hasil_cuaca[0].length;
        System.out.println("L = "+length);
        Best_Hasil_Cuaca = new String[length];
        for(j=0;j<hasil_cuaca[0].length;j++){
            Best_Hasil_Cuaca[j] = hasil_cuaca[j][index];
            counter++;
        }

        return tingkat_akurasi;
    }
    
    public String[] Get_Hasil_Cuaca(){
        return Best_Hasil_Cuaca;
    }
    
    
    public void pso_fuzzzifikasi() {
        double[] akurasi_before, akurasi_current;
        bangkit_swarm(jumlah_swarm);
        do_fuzzyfikasi();
        do_hitung_fuzzy_tsukamoto();
        //akurasi_before = agregasi();
        double temp = 0;
        int i;
        init_kecepatan();
        init_pbest();
        //init_gbest(akurasi_before);
        init_gbest(agregasi());

        for (i = 0; i < jumlah_iterasi; i++) {
            menghitung_nilai_w(i);
            menghitung_nilai_r();
            menghitung_v();
            menghitung_x();

            do_fuzzyfikasi();
            do_hitung_fuzzy_tsukamoto();
            akurasi_current = agregasi();

//            menentukan_pbest(akurasi_before, akurasi_current);
//            menentukan_gbest(akurasi_before, akurasi_current);
            menentukan_pbest(agregasi(), akurasi_current);
            menentukan_gbest(agregasi(), akurasi_current);
            System.out.println("nilai w : " + w);
            System.out.println("nilai r1 : " + r1);
            System.out.println("nilai r2 : " + r2);
            max_akurasi = akurasi_current;
        }

    }

    public double get_akurasi() {
        double temp = 0;
        int i;

        for (i = 0; i < max_akurasi.length; i++) {
            if (max_akurasi[i] > temp) {
                temp = max_akurasi[i]; 
           }
        }

        return temp;
    }

    public void menghitung_nilai_w(int current_iterasi) {
        w = (wmax - ((wmax - wmin) / jumlah_iterasi) + current_iterasi);
    }

    public void menghitung_nilai_r() {

        double maxr = 1;
        double minr = 0.1;
        Random random = new Random();
//        double range = maxr - minr;
//        r1 =0;
//        r2=0;
//        while(r1 !=0 && r2 !=0){
        r1 = minr + (maxr - minr) * random.nextDouble();
        r2 = minr + (maxr - minr) * random.nextDouble();
//        }
    }

    public void init_kecepatan() {
        v = new double[jumlah_swarm][12];
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v[0].length; j++) {
                v[i][j] = 0;
            }
        }

    }

    public void init_pbest() {
        pbest = new double[jumlah_swarm][12];

        for (int i = 0; i < jumlah_swarm; i++) {
            for (int j = 0; j < 12; j++) {
                pbest[i][j] = f_anggota_swarm[i][j];
                System.out.println("nilai pbest[ " + i + "] :" + pbest[i][j]);
            }
        }

        pbest_before = pbest;
    }

    public void init_gbest(double[] tingkat_akurasi) {
        int idx_max;
        double max;

        gbest = new double[12];

        max = tingkat_akurasi[0];
        idx_max = 0;
        for (int i = 0; i < tingkat_akurasi.length; i++) {
            if (max < tingkat_akurasi[i]) {
                max = tingkat_akurasi[i];
                idx_max = i;
            }
        }

        for (int i = 0; i < f_anggota_swarm[0].length; i++) {
            gbest[i] = f_anggota_swarm[idx_max][i];
            System.out.println("nilai gbest[ " + i + "] :" + gbest[i]);
        }
    }

    public void menentukan_pbest(double[] akurasi_before, double[] akurasi_current) {
        pbest_before = new double[jumlah_swarm][12];

        for (int i = 0; i < jumlah_swarm; i++) {
            for (int j = 0; j < 12; j++) {
                if (akurasi_before[i] < akurasi_current[i]) {
                    pbest[i][j] = pbest[i][j];
                    System.out.println("nilai pbest[ " + i + "] :" + pbest[i][j]);
                }
            }
        }
        pbest_before = pbest;
    }

    public void menentukan_gbest(double[] akurasi_before, double[] akurasi_current) {
        int idx_max;
        double max;

        max = akurasi_before[0];
        idx_max = 0;
        for (int i = 0; i < akurasi_current.length; i++) {
            if (max < akurasi_current[i]) {
                max = akurasi_current[i];
                idx_max = i;
            }
        }

        for (int i = 0; i < f_anggota_swarm[0].length; i++) {
            gbest[i] = pbest[idx_max][i];
            System.out.println("nilai gbest[" + i + "] : " + gbest[i]);
        }
    }

    public void menghitung_v() {
//+++++++++++++++ Perulangan untuk setiap populasi pada setiap data ++++++++++++++++            
        for (int i = 0; i < jumlah_swarm; i++) {
            for (int j = 0; j < 12; j++) {
                v[i][j] = (v[i][j] * w) + ((c1 * r1) * (pbest[i][j] - f_anggota_swarm[i][j])) + ((c2 * r2) * (gbest[j] - f_anggota_swarm[i][j]));
                System.out.println("nilai v = " + v[i][j]);
            }
        }
    }

    public void menghitung_x() {
//+++++++++++++++ Perulangan untuk setiap populasi pada setiap data ++++++++++++++++            
        for (int i = 0; i < jumlah_swarm; i++) {
            for (int j = 0; j < 12; j++) {
                f_anggota_swarm[i][j] = f_anggota_swarm[i][j] + v[i][j];
                System.out.println("nilai anggota swarm[" + i + "][" + j + "] :" + f_anggota_swarm[i][j]);
            }
        }
    }
}
