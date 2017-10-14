package com.apap.umarfarisi.tugas.satu.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.apap.umarfarisi.tugas.satu.model.KeluargaDBModel;
import com.apap.umarfarisi.tugas.satu.model.KeluargaFormModel;
import com.apap.umarfarisi.tugas.satu.model.KeluargaViewModel;

@Mapper
public interface KeluargaMapper {

	@Select("select k.id, k.nomor_kk as nkk, k.alamat, k.rt, k.rw, kel.nama_kelurahan as namaKelurahan, kec.nama_kecamatan as namaKecamatan, ko.nama_kota as namaKota "
			+ "from keluarga k, kelurahan kel, kecamatan kec, kota ko "
			+ "where k.id_kelurahan = kel.id and kel.id_kecamatan = kec.id and kec.id_kota = ko.id and k.nomor_kk = #{nkk};")
	public KeluargaViewModel getKeluargaView(@Param("nkk") String nkk);
	
	@Insert("insert into keluarga (nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku) "
			+ "values (#{keluarga.nkk},#{keluarga.alamat},#{keluarga.rt},#{keluarga.rw},#{keluarga.idKelurahan},#{keluarga.isTidakBerlaku})")
	public void addDataKeluarga(@Param("keluarga") KeluargaDBModel keluarga);

	@Select("select nomor_kk from keluarga where nomor_kk like '${nkk_pattern}%' order by nomor_kk desc limit 1")
	public String getLatestNKK(@Param("nkk_pattern") String nkkPattern);

	@Select("select k.alamat, k.rt, k.rw, kel.nama_kelurahan as kelurahan, kec.nama_kecamatan as kecamatan, kot.nama_kota as kota "
			+ "from keluarga k, kelurahan kel, kecamatan kec, kota kot "
			+ "where k.id_kelurahan = kel.id and kel.id_kecamatan = kec.id and kec.id_kota = kot.id and k.nomor_kk = ${nkk};")
	public KeluargaFormModel getKeluargaForm(@Param("nkk") String nkk);

	@Update("update keluarga set nomor_kk = #{keluargaDB.nkk}, alamat = #{keluargaDB.alamat}, rt = #{keluargaDB.rt}, "
			+ "rw = #{keluargaDB.rw}, id_kelurahan = #{keluargaDB.idKelurahan}, is_tidak_berlaku = #{keluargaDB.tidakBerlaku} "
			+ "where nomor_kk = #{nkk}")
	public void updateKeluarga(@Param("nkk") String nkk, @Param("keluargaDB") KeluargaDBModel keluargaDB);
	
}
