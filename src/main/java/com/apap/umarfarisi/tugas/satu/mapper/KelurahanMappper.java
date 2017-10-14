package com.apap.umarfarisi.tugas.satu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface KelurahanMappper {

	@Select("select kel.id from kelurahan kel, kecamatan kec, kota kot "
			+ "where kel.id_kecamatan = kec.id and kec.id_kota = kot.id and kel.nama_kelurahan = #{nama_kelurahan} and kec.nama_kecamatan = #{nama_kecamatan} and kot.nama_kota = #{nama_kota}")
	Long getIdKelurahan(@Param("nama_kota") String namaKota, @Param("nama_kecamatan") String namaKecamatan, @Param("nama_kelurahan") String namaKelurahan);

}
