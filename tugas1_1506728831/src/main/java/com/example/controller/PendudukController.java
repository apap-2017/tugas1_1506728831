package com.example.controller;

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
import com.example.model.PendudukModel;
import com.example.service.KecamatanService;
import com.example.service.KeluargaService;
import com.example.service.KelurahanService;
import com.example.service.KotaService;
import com.example.service.PendudukService;


@Controller
public class PendudukController {
	
	@Autowired
	PendudukService pendudukDAO;
	
	@Autowired
	KeluargaService keluargaDAO;
	
	@Autowired
	KelurahanService kelurahanDAO;

	@Autowired
	KecamatanService kecamatanDAO;
	
	@Autowired
	KotaService kotaDAO;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
    @RequestMapping("/penduduk")
    public String view (Model model,
            @RequestParam(value = "nik") String nik)
    {
        PendudukModel penduduk = pendudukDAO.selectPenduduk (nik);

        if (penduduk != null) {
            KeluargaModel keluarga = keluargaDAO.selectKeluarga(penduduk.getId_keluarga());
            KelurahanModel kelurahan = kelurahanDAO.selectKelurahan(keluarga.getId_kelurahan());
            KecamatanModel kecamatan = kecamatanDAO.selectKecamatan(kelurahan.getId_kecamatan());
            KotaModel kota = kotaDAO.selectKota(kecamatan.getId_kota());
        	model.addAttribute ("penduduk", penduduk);
        	model.addAttribute("keluarga", keluarga);
        	model.addAttribute("kelurahan", kelurahan);
        	model.addAttribute("kecamatan", kecamatan);
        	model.addAttribute("kota", kota);
            return "view-penduduk";
        } else {
            model.addAttribute ("nik", nik);
            return "not-found";
        }
    }
    
    @RequestMapping(method=RequestMethod.GET, value="/penduduk/tambah")
    public String tambahPendudukForm() {
    	return "form-tambah-penduduk";
    }
    
    @RequestMapping(method=RequestMethod.POST, value="/penduduk/tambah")
    public String tambahPenduduk(@RequestParam(value = "nama", required = false) String nama,
    		   @RequestParam(value = "tempat_lahir", required = false) String tempat_lahir,
    		   @RequestParam(value = "tanggal_lahir", required = false) String tanggal_lahir,
    		   @RequestParam(value = "golongan_darah", required = false) String golongan_darah,
    		   @RequestParam(value = "jenis_kelamin", required = false) int jenis_kelamin,
    		   @RequestParam(value = "agama", required = false) String agama,
    		   @RequestParam(value = "status_perkawinan", required = false) String status_perkawinan,
    		   @RequestParam(value = "pekerjaan", required = false) String pekerjaan,
    		   @RequestParam(value = "is_wni", required = false) int is_wni,
    		   @RequestParam(value = "is_wafat", required = false) int is_wafat,
    		   @RequestParam(value = "id_keluarga", required = false) int id_keluarga,
    		   @RequestParam(value = "status_dalam_keluarga", required = false) String status_dalam_keluarga,
    		   Model model) {
    	
        
        if(nama.equals("")||tempat_lahir.equals("")||agama.equals("")||status_perkawinan.equals("")||
        		pekerjaan.equals("")||status_dalam_keluarga.equals("")||id_keluarga <= 0) {
        	return "error-tambah-penduduk";
        }else {
        	KeluargaModel keluarga = keluargaDAO.selectKeluarga(id_keluarga);
        	KelurahanModel kelurahan = kelurahanDAO.selectKelurahan(keluarga.getId_kelurahan());
            KecamatanModel kecamatan = kecamatanDAO.selectKecamatan(kelurahan.getId_kecamatan());
            
            String kodeKecamatan = kecamatan.getKode_kecamatan().substring(0, kecamatan.getKode_kecamatan().length() - 1);
            String tahunLahir = tanggal_lahir.substring(2, 4);
            String bulanLahir = tanggal_lahir.substring(5, 7);
            String tanggalLahir = tanggal_lahir.substring(8);
            
            
            if(jenis_kelamin == 1) {
            	int tanggal = Integer.parseInt(tanggalLahir);
            	tanggal += 40;
            	tanggalLahir = "" + tanggal;
            }
            
            
            String nik = kodeKecamatan + tanggalLahir + bulanLahir + tahunLahir + "0001";
            
            
            while (pendudukDAO.selectPenduduk(nik) != null) {
            	int nikBaru = Integer.parseInt(nik);
            	nikBaru += 1;
            	nik = "" + nikBaru;
            }
            
        	int id = pendudukDAO.banyakPenduduk() + 1;
        	PendudukModel penduduk = new PendudukModel(id,nik,nama,tempat_lahir,tanggal_lahir,jenis_kelamin,is_wni,id_keluarga,
        			agama,pekerjaan,status_perkawinan,status_dalam_keluarga,golongan_darah,is_wafat);
        	pendudukDAO.tambahPenduduk(penduduk);
        	model.addAttribute("penduduk", penduduk);
        	return "tambah-sukses";
        }
    }
}
