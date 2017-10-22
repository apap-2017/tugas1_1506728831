package com.example.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.model.PendudukModel;

@Mapper
public interface PendudukMapper {
	@Select("select * from penduduk where nik = #{nik}")
	PendudukModel selectPenduduk (@Param("nik") String nik);
	
	@Insert("INSERT INTO penduduk (id,nik,nama,tempat_lahir,tanggal_lahir,jenis_kelamin,is_wni,id_keluarga,"
			+ "agama,pekerjaan,status_perkawinan,status_dalam_keluarga,golongan_darah,is_wafat) "
			+ "VALUES (#{id}, #{nik}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin}, #{is_wni}, #{id_keluarga}, "
			+ "#{agama}, #{pekerjaan}, #{status_perkawinan}, #{status_dalam_keluarga}, #{golongan_darah}, #{is_wafat})")
    void tambahPenduduk (PendudukModel penduduk);
	
	@Select("select count(id) from penduduk")
	int banyakPenduduk();
	
	
}
