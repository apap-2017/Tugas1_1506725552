package com.apap.umarfarisi.tugas.satu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.apap.umarfarisi.tugas.satu.model.KecamatanDBModel;

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
	
	@Select("select id, id_kota as idKota, kode_kecamatan as kodeKecamatan, nama_kecamatan as namaKecamatan "
			+ "from kecamatan where id_kota = #{id_kota}")
	public List<KecamatanDBModel> getAllKecamatanByIdKota(@Param("id_kota") long idKota);

	@Select("select id, id_kota as idKota, kode_kecamatan as kodeKecamatan, nama_kecamatan as namaKecamatan "
			+ "from kecamatan where id = #{id_kecamatan};")
	public KecamatanDBModel getKecamatan(@Param("id_kecamatan") Long idKecamatan);
	
}
