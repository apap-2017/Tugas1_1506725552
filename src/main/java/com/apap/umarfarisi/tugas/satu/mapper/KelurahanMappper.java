package com.apap.umarfarisi.tugas.satu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.apap.umarfarisi.tugas.satu.model.KelurahanDBModel;

@Mapper
public interface KelurahanMappper {

	@Select("select kel.id from kelurahan kel, kecamatan kec, kota kot "
			+ "where kel.id_kecamatan = kec.id and kec.id_kota = kot.id and kel.nama_kelurahan = #{nama_kelurahan} and kec.nama_kecamatan = #{nama_kecamatan} and kot.nama_kota = #{nama_kota}")
	Long getIdKelurahan(@Param("nama_kota") String namaKota, @Param("nama_kecamatan") String namaKecamatan, @Param("nama_kelurahan") String namaKelurahan);
	
	@Select("select id, id_kecamatan as idKecamatan, kode_kelurahan as kodeKelurahan, nama_kelurahan as namaKelurahan, kode_pos as kodePos "
			+ "from kelurahan where id_kecamatan = #{id_kecamatan}")
	List<KelurahanDBModel> getAllKelurahanByIdKecamatan(@Param("id_kecamatan") long idKecamatan);

	@Select("select id, id_kecamatan as idKecamatan, kode_kelurahan as kodeKelurahan, nama_kelurahan as namaKelurahan, kode_pos as kodePos "
			+ "from kelurahan where id = #{id_kelurahan};")
	KelurahanDBModel getKelurahan(@Param("id_kelurahan") Long idKelurahan);

}
