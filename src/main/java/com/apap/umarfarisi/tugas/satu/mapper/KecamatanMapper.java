package com.apap.umarfarisi.tugas.satu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface KecamatanMapper {
	
	@Select("select kec.kode_kecamatan "
			+ "from keluarga k, kelurahan kel, kecamatan kec "
			+ "where k.id = 172 and k.id_kelurahan = kel.id and kel.id_kecamatan = kec.id;")
	public String getKodeKecamatanByIdKeluarga(@Param("id_keluarga") int idKeluarga);
}
