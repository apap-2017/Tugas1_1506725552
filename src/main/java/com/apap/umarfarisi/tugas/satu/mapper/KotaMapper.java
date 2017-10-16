package com.apap.umarfarisi.tugas.satu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.apap.umarfarisi.tugas.satu.model.KotaDBModel;

@Mapper
public interface KotaMapper {
	
	@Select("select id, kode_kota as kodeKota, nama_kota as namaKota "
			+ "from kota;")
	public List<KotaDBModel> getAllKota();

	@Select("select id, kode_kota as kodeKota, nama_kota as namaKota "
			+ "from kota where id = #{id_kota};")
	public KotaDBModel getKota(@Param("id_kota") Long idKota);
	
}
