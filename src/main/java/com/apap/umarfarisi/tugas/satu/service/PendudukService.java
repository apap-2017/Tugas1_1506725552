package com.apap.umarfarisi.tugas.satu.service;

import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.umarfarisi.tugas.satu.mapper.KecamatanMapper;
import com.apap.umarfarisi.tugas.satu.mapper.KeluargaMapper;
import com.apap.umarfarisi.tugas.satu.mapper.KelurahanMappper;
import com.apap.umarfarisi.tugas.satu.mapper.KotaMapper;
import com.apap.umarfarisi.tugas.satu.mapper.PendudukMapper;
import com.apap.umarfarisi.tugas.satu.model.KecamatanDBModel;
import com.apap.umarfarisi.tugas.satu.model.KeluargaDBModel;
import com.apap.umarfarisi.tugas.satu.model.KelurahanDBModel;
import com.apap.umarfarisi.tugas.satu.model.KotaDBModel;
import com.apap.umarfarisi.tugas.satu.model.PendudukDBModel;
import com.apap.umarfarisi.tugas.satu.model.PendudukViewModel;
import com.apap.umarfarisi.tugas.satu.utils.PendudukUtils;

@Service
public class PendudukService {
	
	@Autowired
	private PendudukMapper pendudukMapper;
	@Autowired
	private KelurahanMappper kelurahanMappper;
	@Autowired
	private KecamatanMapper kecamatanMapper;
	@Autowired
	private KotaMapper kotaMapper;
	@Autowired
	private KeluargaMapper keluargaMapper;
	
	public PendudukViewModel getDataPendudukBerdasarkanNik(String nik) {
		return pendudukMapper.getPendudukView(nik);
	}
	
	public String addDataPenduduk(PendudukDBModel pendudukForm) {
		String nik = PendudukUtils.generateNIK(kecamatanMapper, pendudukMapper, pendudukForm.getIdKeluarga(), pendudukForm.getTanggalLahir(), pendudukForm.getJenisKelamin(), null);
		if(nik == PendudukUtils.ERROR_BY_ID_KELUARGA 
				|| nik == PendudukUtils.ERROR_BY_ID_TANGGAL_LAHIR) {
			//return error message which is nik
			return nik;
		}
		
		pendudukForm.setNik(nik);
		pendudukMapper.addPenduduk(pendudukForm);
		
		return nik;
	}

	public PendudukDBModel getDataPendudukForForm(String nik) {
		
		return pendudukMapper.getPendudukFrom(nik);

	}

	public String updateDataPenduduk(String nik, PendudukDBModel pendudukForm) {
		String newNik = PendudukUtils.generateNIK(kecamatanMapper, pendudukMapper, pendudukForm.getIdKeluarga(), pendudukForm.getTanggalLahir(), pendudukForm.getJenisKelamin(), nik);
		
		if(newNik == PendudukUtils.ERROR_BY_ID_KELUARGA 
				|| newNik == PendudukUtils.ERROR_BY_ID_TANGGAL_LAHIR) {
			//return error message which is newNik
			return newNik;
		}
		
		pendudukForm.setNik(newNik);
		pendudukMapper.updatePenduduk(nik, pendudukForm);
		return newNik;
	}

	public boolean updateDataStatusKematianPenduduk(String nik) {
		PendudukDBModel pendudukForm = pendudukMapper.getPendudukFrom(nik); //impossible null
		pendudukForm.setWafat( !pendudukForm.isWafat() );
		
		pendudukMapper.updatePenduduk(nik, pendudukForm);
		
		KeluargaDBModel keluarga = keluargaMapper.getKeluargaDBById(pendudukForm.getIdKeluarga());
		if(keluarga.isTidakBerlaku() != pendudukForm.isWafat()) {
			
			List<PendudukDBModel> anggotaKeluargas = pendudukMapper.getAllPendudukByIdKeluarga(pendudukForm.getIdKeluarga());
			boolean isChange = false;
			for(int i = 0 ; i < anggotaKeluargas.size() && !isChange ; i++) {
				if(keluarga.isTidakBerlaku() != anggotaKeluargas.get(i).isWafat()) {
					isChange = true;
				}
			}
			if(isChange) {
				keluarga.setTidakBerlaku(!keluarga.isTidakBerlaku());
				keluargaMapper.updateKeluarga(keluarga.getNkk(), keluarga);
			}
		}
		
		return pendudukForm.isWafat();
	}

	public List<KotaDBModel> getDaftarKota() {
		return kotaMapper.getAllKota();
	}

	public List<KecamatanDBModel> getDaftarKecamatanPadaSuatuKota(Long idKota) {
		return kecamatanMapper.getAllKecamatanByIdKota(idKota);
	}

	public List<KelurahanDBModel> getDaftarKelurahanPadaSuatuKecamatan(Long idKecamatan) {
		return kelurahanMappper.getAllKelurahanByIdKecamatan(idKecamatan);
	}

	public KotaDBModel getNamaKotaYangDipilih(Long idKota) {
		return kotaMapper.getKota(idKota);
	}
	
	public KecamatanDBModel getNamaKecamatanYangDipilih(Long idKecamatan) {
		return kecamatanMapper.getKecamatan(idKecamatan);
	}
	
	public KelurahanDBModel getNamaKelurahanYangDipilih(Long idKelurahan) {
		return kelurahanMappper.getKelurahan(idKelurahan);
	}

	public List<PendudukDBModel> getDaftarPendudukBerdasarkanTempatYangDipilih(Long idKota, Long idKecamatan,
			Long idKelurahan) {
		return pendudukMapper.getAllPendudukByIdKelurahan(idKelurahan);
	}
	
}
