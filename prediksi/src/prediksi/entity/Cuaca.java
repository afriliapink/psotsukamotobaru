/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prediksi.entity;

/**
 *
 * @author afrilia
 */
public class Cuaca {
    double no, suhu, kelembaban, tekanan_udara, kecepatan_angin;
    String tanggal, keadaan_cuaca;

    public Cuaca() {
    }

    public double getNo() {
        return no;
    }

    public void setNo(double no) {
        this.no = no;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }



    public double getSuhu() {
        return suhu;
    }

    public void setSuhu(double suhu) {
        this.suhu = suhu;
    }

    public double getKelembaban() {
        return kelembaban;
    }

    public void setKelembaban(double kelembaban) {
        this.kelembaban = kelembaban;
    }

    public double getTekanan_udara() {
        return tekanan_udara;
    }

    public void setTekanan_udara(double tekanan_udara) {
        this.tekanan_udara = tekanan_udara;
    }

    public double getKecepatan_angin() {
        return kecepatan_angin;
    }

    public void setKecepatan_angin(double kecepatan_angin) {
        this.kecepatan_angin = kecepatan_angin;
    }

    public String getKeadaan_cuaca() {
        return keadaan_cuaca;
    }

    public void setKeadaan_cuaca(String keadaan_cuaca) {
        this.keadaan_cuaca = keadaan_cuaca;
    }
    
}
