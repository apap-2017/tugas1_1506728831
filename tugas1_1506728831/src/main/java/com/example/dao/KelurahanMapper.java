package com.example.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.model.KelurahanModel;

@Mapper
public interface KelurahanMapper {
	@Select("select * from kelurahan where id = #{id_kelurahan}")
	KelurahanModel selectKelurahan (@Param("id_kelurahan") int id_kelurahan);
	
	@Select("select * from kelurahan where nama_kelurahan = #{nama_kelurahan}")
	KelurahanModel selectKelurahanByNama (@Param("nama_kelurahan") String nama_kelurahan);
}
