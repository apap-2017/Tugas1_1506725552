package com.apap.umarfarisi.tugas.satu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.apap.umarfarisi.tugas.satu.model.KeluargaViewModel;

@Mapper
public interface KeluargaMapper {

	@Select("select k.id, k.nomor_kk as nkk, k.alamat, k.rt, k.rw, kel.nama_kelurahan as namaKelurahan, kec.nama_kecamatan as namaKecamatan, ko.nama_kota as namaKota "
			+ "from keluarga k, kelurahan kel, kecamatan kec, kota ko "
			+ "where k.id_kelurahan = kel.id and kel.id_kecamatan = kec.id and kec.id_kota = ko.id and k.nomor_kk = #{nkk};")
	public KeluargaViewModel getKeluargaView(@Param("nkk") String nkk);
	
}
