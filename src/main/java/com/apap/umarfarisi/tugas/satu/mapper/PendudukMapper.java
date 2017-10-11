package com.apap.umarfarisi.tugas.satu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.apache.tools.ant.types.resources.comparators.Date;

import com.apap.umarfarisi.tugas.satu.model.PendudukViewModel;
import com.fasterxml.jackson.databind.JavaType;

@Mapper
public interface PendudukMapper {

	@Select("select p.nik as nik, p.nama as nama, p.tempat_lahir as tempatLahir, p.tanggal_lahir as tanggal, "
			+ "ka.alamat as alamat, ka.rt as rt, ka.rw as rw, khan.nama_kelurahan as namaKelurahan, ktan.nama_kecamatan as namaKecamatan, "
			+ "k.nama_kota as namaKota, p.golongan_darah as golonganDarah, p.agama as agama, p.status_perkawinan as statusPerkawinan, p.pekerjaan as pekerjaan, p.is_wafat as statusKematian "
			+ "from penduduk p, keluarga ka, kelurahan khan, kecamatan ktan, kota k "
			+ "where p.id_keluarga = ka.id and ka.id_kelurahan = khan.id and khan.id_kecamatan = ktan.id and ktan.id_kota = k.id and p.nik = #{nik} ;")
	public PendudukViewModel getPendudukView(@Param("nik") String nik);

}
