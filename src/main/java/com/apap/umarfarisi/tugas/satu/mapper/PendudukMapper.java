package com.apap.umarfarisi.tugas.satu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.apap.umarfarisi.tugas.satu.model.KeluargaFormModel;
import com.apap.umarfarisi.tugas.satu.model.PendudukFormModel;
import com.apap.umarfarisi.tugas.satu.model.PendudukDBModel;
import com.apap.umarfarisi.tugas.satu.model.PendudukViewModel;

@Mapper
public interface PendudukMapper {

	@Select("select p.nik as nik, p.nama as nama, p.tempat_lahir as tempatLahir, p.tanggal_lahir as tanggal, "
			+ "ka.alamat as alamat, ka.rt as rt, ka.rw as rw, khan.nama_kelurahan as namaKelurahan, ktan.nama_kecamatan as namaKecamatan, "
			+ "k.nama_kota as namaKota, p.golongan_darah as golonganDarah, p.agama as agama, p.status_perkawinan as statusPerkawinan, p.pekerjaan as pekerjaan, p.is_wafat as statusKematian "
			+ "from penduduk p, keluarga ka, kelurahan khan, kecamatan ktan, kota k "
			+ "where p.id_keluarga = ka.id and ka.id_kelurahan = khan.id and khan.id_kecamatan = ktan.id and ktan.id_kota = k.id and p.nik = ${nik} ;")
	public PendudukViewModel getPendudukView(@Param("nik") String nik);
	
	@Select("select nama, nik, jenis_kelamin as jenisKelamin, tempat_lahir as tempatLahir, tanggal_lahir as tanggalLahir, agama, pekerjaan, status_perkawinan as statusPerkawinan, status_dalam_keluarga as statusDalamKeluarga, is_wni as isWni "
			+ "from penduduk where id_keluarga = ${id_keluarga};")
	public List<PendudukDBModel> getAllPendudukByIdKeluarga(@Param("id_keluarga") String idKeluarga);
	
	@Insert("insert into penduduk (nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat) "
			+ "VALUES ('${pendudukForm.nik}', '${pendudukForm.nama}', '${pendudukForm.tempatLahir}', '${pendudukForm.tanggalLahir}', ${pendudukForm.jenisKelamin}, ${pendudukForm.wni}, '${pendudukForm.idKeluarga}', "
			+ "'${pendudukForm.agama}', '${pendudukForm.pekerjaan}', '${pendudukForm.statusPerkawinan}', '${pendudukForm.statusDalamKeluarga}', '${pendudukForm.golonganDarah}', ${pendudukForm.wafat});")
	public void addPenduduk(@Param("pendudukForm") PendudukFormModel pendudukForm);
	
	
	@Select("select nik from penduduk where nik like '${nik_pattern}%' order by nik desc limit 1;")
	public String getLatestPendudukInDomisili(@Param("nik_pattern") String nikPattern);
	
	
	@Select("select nik, nama, tempat_lahir as tempatLahir, tanggal_lahir as tanggalLahir, jenis_kelamin as jenisKelamin, "
			+ "is_wni as wni, id_keluarga as idKeluarga, agama, pekerjaan, status_perkawinan as statusPerkawinan, "
			+ "status_dalam_keluarga as statusDalamKeluarga, golongan_darah as golonganDarah, is_wafat as wafat "
			+ "from penduduk where nik = #{nik}")
	public PendudukFormModel getPendudukFrom(@Param("nik") String nik);

	@Update("update penduduk set nik = ${pendudukForm.nik}, nama = '${pendudukForm.nama}', tempat_lahir = '${pendudukForm.tempatLahir}', tanggal_lahir = '${pendudukForm.tanggalLahir}', jenis_kelamin = ${pendudukForm.jenisKelamin},"
			+ "is_wni = ${pendudukForm.wni}, id_keluarga = '${pendudukForm.idKeluarga}', agama = '${pendudukForm.agama}', pekerjaan = '${pendudukForm.pekerjaan}', status_perkawinan = '${pendudukForm.statusPerkawinan}', "
			+ "status_dalam_keluarga = '${pendudukForm.statusDalamKeluarga}', golongan_darah = '${pendudukForm.golonganDarah}', is_wafat = ${pendudukForm.wafat} "
			+ "where nik = #{nik}")
	public void updatePenduduk(@Param("nik") String nik, @Param("pendudukForm") PendudukFormModel pendudukForm);
	

}
