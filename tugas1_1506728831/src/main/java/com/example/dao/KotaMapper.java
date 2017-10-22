package com.example.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.model.KotaModel;

@Mapper
public interface KotaMapper {
	@Select("select * from kota where id = #{id_kota}")
	KotaModel selectKota (@Param("id_kota") int id_kota);
}
