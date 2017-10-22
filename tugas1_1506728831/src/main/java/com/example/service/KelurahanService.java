package com.example.service;

import com.example.model.KelurahanModel;

public interface KelurahanService {
	KelurahanModel selectKelurahan(int id);
	KelurahanModel selectKelurahanByNama(String nama_kelurahan);
}
