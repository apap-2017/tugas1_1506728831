package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KeluargaMapper;
import com.example.model.KeluargaModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService{
	
	@Autowired
	private KeluargaMapper keluargaMapper;

	@Override
	public KeluargaModel selectKeluarga(int id) {
		log.info ("select keluarga with id {}", id);
		return keluargaMapper.selectKeluarga(id);
	}

	@Override
	public KeluargaModel selectKeluargaByNkk(String nomor_kk) {
		log.info ("select keluarga with nkk {}", nomor_kk);
		return keluargaMapper.selectKeluargaByNkk(nomor_kk);
	}

	@Override
	public int banyakKeluarga() {
		return keluargaMapper.banyakKeluarga();
	}

	@Override
	public void tambahKeluarga(KeluargaModel keluarga) {
		// TODO Auto-generated method stub
		keluargaMapper.tambahKeluarga(keluarga);
	}
	
	
}
