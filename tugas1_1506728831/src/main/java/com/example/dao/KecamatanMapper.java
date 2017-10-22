package com.example.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.model.KecamatanModel;

@Mapper
public interface KecamatanMapper {
	@Select("select * from kecamatan where id = #{id_kecamatan}")
	KecamatanModel selectKecamatan (@Param("id_kecamatan") int id_kecamatan);
}
