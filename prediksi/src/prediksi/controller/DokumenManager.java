/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prediksi.controller;
import java.util.ArrayList;
import prediksi.entity.Dokumen;

/**
 *
 * @author afrilia
 */

public class DokumenManager {
    Dokumen dokumen_excel;

    public DokumenManager() {
        //dokumen_excel = new Dokumen();
    }
    
    public ArrayList<ArrayList> getDataExcel(String path)
    {
        dokumen_excel = new Dokumen();
        ArrayList<ArrayList> data_excel;
        
        data_excel = dokumen_excel.load_excel(path);
        return data_excel;
        
    }
    
}
