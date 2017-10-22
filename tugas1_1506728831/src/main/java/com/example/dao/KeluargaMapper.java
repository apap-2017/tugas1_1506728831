package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

@Mapper
public interface KeluargaMapper {
	@Select("select * from keluarga where id = #{id_keluarga}")
	KeluargaModel selectKeluarga (@Param("id_keluarga") int id_keluarga);
	
	@Select("select * from keluarga where nomor_kk = #{nomor_kk}")
	@Results(value = {
			@Result(property="nomor_kk", column="nomor_kk"),
    		@Result(property="listPenduduk", column="nomor_kk",
    				javaType = List.class,
    				many=@Many(select="selectListPenduduk"))
    })
	KeluargaModel selectKeluargaByNkk (@Param("nomor_kk") String nomor_kk);
	
    @Select("select *" +
    		"from penduduk join keluarga " +
    		"on keluarga.id = penduduk.id_keluarga " +
    		"where keluarga.nomor_kk = #{nomor_kk}")
    List<PendudukModel> selectListPenduduk (@Param("nomor_kk") String nomor_kk);
    
    @Select("select count(id) from keluarga")
	int banyakKeluarga();
    
    @Insert("INSERT INTO keluarga (id,nomor_kk,alamat,RT,RW,id_kelurahan,is_tidak_berlaku) "
    		+ "VALUES (#{id},#{nomor_kk},#{alamat},#{rt},#{rw},#{id_kelurahan},#{is_tidak_berlaku})")
    void tambahKeluarga (KeluargaModel keluarga);
}
