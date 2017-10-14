package com.apap.umarfarisi.tugas.satu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface KecamatanMapper {
	
	@Select("select kec.kode_kecamatan "
			+ "from keluarga k, kelurahan kel, kecamatan kec "
			+ "where k.id = ${id_keluarga} and k.id_kelurahan = kel.id and kel.id_kecamatan = kec.id;")
	public String getKodeKecamatanByIdKeluarga(@Param("id_keluarga") int idKeluarga);
	
	@Select("select kec.kode_kecamatan "
			+ "from kecamatan kec, kota kot "
			+ "where kec.id_kota = kot.id and kec.nama_kecamatan = #{nama_kecamatan} and kot.nama_kota = #{nama_kota}")
	public String getKodeKecamatan(@Param("nama_kecamatan") String namaKecamatan
			, @Param("nama_kota") String namaKota);
}
