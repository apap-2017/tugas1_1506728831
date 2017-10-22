package com.example.service;

import com.example.model.KeluargaModel;

public interface KeluargaService {
	KeluargaModel selectKeluarga(int id);
	KeluargaModel selectKeluargaByNkk(String nomor_kk);
	void tambahKeluarga (KeluargaModel keluarga);
	int banyakKeluarga();
}
