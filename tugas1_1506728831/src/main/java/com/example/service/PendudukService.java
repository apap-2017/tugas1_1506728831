package com.example.service;

import com.example.model.PendudukModel;

public interface PendudukService {
	PendudukModel selectPenduduk(String nik);
	void tambahPenduduk (PendudukModel penduduk);
	int banyakPenduduk();
}
