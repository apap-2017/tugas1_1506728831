package com.example.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.service.KecamatanService;
import com.example.service.KeluargaService;
import com.example.service.KelurahanService;
import com.example.service.KotaService;

@Controller
public class KeluargaController {
	
	@Autowired
	KeluargaService keluargaDAO;
	
	@Autowired
	KelurahanService kelurahanDAO;

	@Autowired
	KecamatanService kecamatanDAO;
	
	@Autowired
	KotaService kotaDAO;
	
	@RequestMapping("/keluarga")
	public String viewKeluarga (Model model,
            @RequestParam(value = "nkk") String nomor_kk)
    {
		KeluargaModel keluarga = keluargaDAO.selectKeluargaByNkk(nomor_kk);
		if(keluarga != null) {
			KelurahanModel kelurahan = kelurahanDAO.selectKelurahan(keluarga.getId_kelurahan());
            KecamatanModel kecamatan = kecamatanDAO.selectKecamatan(kelurahan.getId_kecamatan());
            KotaModel kota = kotaDAO.selectKota(kecamatan.getId_kota());
			model.addAttribute("keluarga", keluarga);
			model.addAttribute("kelurahan", kelurahan);
        	model.addAttribute("kecamatan", kecamatan);
        	model.addAttribute("kota", kota);
			return "view-keluarga";			
		} else {
			model.addAttribute("nomor_kk", nomor_kk);
			return "keluarga-not-found";
		}
    }
	
	@RequestMapping(method=RequestMethod.GET, value="/keluarga/tambah")
    public String tambahKeluargaForm() {
    	return "form-tambah-keluarga";
    }
	
	@RequestMapping(method=RequestMethod.POST, value="/keluarga/tambah")
    public String tambahPenduduk(@RequestParam(value = "alamat", required = false) String alamat,
    		@RequestParam(value = "rt", required = false) String rt,
    		@RequestParam(value = "rw", required = false) String rw,
    		@RequestParam(value = "nama_kelurahan", required = false) String nama_kelurahan,
    		@RequestParam(value = "nama_kecamatan", required = false) String nama_kecamatan,
    		@RequestParam(value = "nama_kota", required = false) String nama_kota,
    		Model model) {
		
        
        if(alamat.equals("")||rt.equals("")||rw.equals("")||nama_kelurahan.equals("")||
        		nama_kecamatan.equals("")||nama_kota.equals("")) {
        	return "error-tambah-keluarga";
        } else {
        	KelurahanModel kelurahan = kelurahanDAO.selectKelurahanByNama(nama_kelurahan);
    		KecamatanModel kecamatan = kecamatanDAO.selectKecamatan(kelurahan.getId_kecamatan());
    		String kode_kecamatan = kecamatan.getKode_kecamatan().substring(0, kecamatan.getKode_kecamatan().length() - 1);
    		
    		DateFormat dateFormat = new SimpleDateFormat("ddMMyy");  
            Date date = new Date();  
            String tanggalTerbit = dateFormat.format(date);
            int akhiranNkk = 0001;
            
            String nkk = kode_kecamatan + tanggalTerbit + akhiranNkk;
            
            while (keluargaDAO.selectKeluargaByNkk(nkk) != null) {
            	int nkkBaru = Integer.parseInt(nkk);
            	nkkBaru += 1;
            	nkk = "" + nkkBaru;
            }
            
        	int id = keluargaDAO.banyakKeluarga() + 1;
        	KeluargaModel keluarga = new KeluargaModel(id,nkk,alamat,rt,rw,kelurahan.getId(),0,null);
        	keluargaDAO.tambahKeluarga(keluarga);
        	model.addAttribute("keluarga",keluarga);
        	return "tambah-keluarga-sukses";
        }
	}
}
