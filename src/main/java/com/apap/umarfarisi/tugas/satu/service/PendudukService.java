package com.apap.umarfarisi.tugas.satu.service;

import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.umarfarisi.tugas.satu.mapper.KecamatanMapper;
import com.apap.umarfarisi.tugas.satu.mapper.KelurahanMappper;
import com.apap.umarfarisi.tugas.satu.mapper.KotaMapper;
import com.apap.umarfarisi.tugas.satu.mapper.PendudukMapper;
import com.apap.umarfarisi.tugas.satu.model.KecamatanDBModel;
import com.apap.umarfarisi.tugas.satu.model.KelurahanDBModel;
import com.apap.umarfarisi.tugas.satu.model.KotaDBModel;
import com.apap.umarfarisi.tugas.satu.model.PendudukDBModel;
import com.apap.umarfarisi.tugas.satu.model.PendudukFormModel;
import com.apap.umarfarisi.tugas.satu.model.PendudukViewModel;

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
	
	public PendudukViewModel getDataPendudukBerdasarkanNik(String nik) {
		return pendudukMapper.getPendudukView(nik);
	}
	
	public String addDataPenduduk(PendudukFormModel pendudukForm) {
		String nik = generateNIK(pendudukForm, null);
		pendudukForm.setNik(nik);
		pendudukMapper.addPenduduk(pendudukForm);
		
		return nik;
	}

	public PendudukFormModel getDataPendudukForForm(String nik) {
		
		return pendudukMapper.getPendudukFrom(nik);

	}

	public String updateDataPenduduk(String nik, PendudukFormModel pendudukForm) {
		String newNik = generateNIK(pendudukForm, nik);
		pendudukForm.setNik(newNik);
		pendudukMapper.updatePenduduk(nik, pendudukForm);
		return newNik;
	}

	public boolean updateDataStatusKematianPenduduk(String nik) {
		PendudukFormModel pendudukForm = pendudukMapper.getPendudukFrom(nik); //impossible null
		pendudukForm.setWafat( !pendudukForm.isWafat() );
		
		pendudukMapper.updatePenduduk(nik, pendudukForm);
		
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

	public String getNamaKotaYangDipilih(Long idKota) {
		return kotaMapper.getKota(idKota).getNamaKota();
	}
	
	public String getNamaKecamatanYangDipilih(Long idKecamatan) {
		return kecamatanMapper.getKecamatan(idKecamatan).getNamaKecamatan();
	}
	
	public String getNamaKelurahanYangDipilih(Long idKelurahan) {
		return kelurahanMappper.getKelurahan(idKelurahan).getNamaKelurahan();
	}

	public List<PendudukDBModel> getDaftarPendudukBerdasarkanTempatYangDipilih(Long idKota, Long idKecamatan,
			Long idKelurahan) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private String generateNIK(PendudukFormModel pendudukForm, String oldNik) {
		String kodeKecamatan = kecamatanMapper.getKodeKecamatanByIdKeluarga(pendudukForm.getIdKeluarga());
		String sixDigitFirst = kodeKecamatan.substring(0, kodeKecamatan.length() - 1);
		
		StringTokenizer tokenBirdDay = new StringTokenizer(pendudukForm.getTanggalLahir(), "-");
		//2017-05-24
		int year = Integer.parseInt(tokenBirdDay.nextToken().substring(2));
		int month = Integer.parseInt(tokenBirdDay.nextToken());
		int day = Integer.parseInt(tokenBirdDay.nextToken());
		if(pendudukForm.getJenisKelamin() == 1) //1 means women
			day += 40; 
		
		String sixDigitSecod = String.format("%02d", day) + String.format("%02d", month) + String.format("%02d", year);
		
		String nik = sixDigitFirst + sixDigitSecod;
		
		//no NIK change
		if(oldNik != null && oldNik.substring(0, nik.length()).trim().equals(nik.trim()))
			return oldNik;
		
		int last = 0;
		
		//check the last NIK
		String lastNikInDomisili = pendudukMapper.getLatestPendudukInDomisili(nik);
		if(lastNikInDomisili != null) {
			last = Integer.valueOf(lastNikInDomisili.substring(nik.length()));
			last++;
		}
		
		return nik + String.format("%04d", last);
	}


	
	
}
