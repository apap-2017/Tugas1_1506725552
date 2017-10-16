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
		String nik = generateFirstNIK(pendudukForm);
		pendudukForm.setNik(nik);
		pendudukMapper.addPenduduk(pendudukForm);
		
		return nik;
	}

	private String generateFirstNIK(PendudukFormModel pendudukForm) {
		String kodeKecamatan = kecamatanMapper.getKodeKecamatanByIdKeluarga(pendudukForm.getIdKeluarga());
		String sixDigitFirst = kodeKecamatan.substring(0, kodeKecamatan.length() - 1);
		
		StringTokenizer tokenBirdDay = new StringTokenizer(pendudukForm.getTanggalLahir(), "-");
		//2017-05-24
		int year = Integer.parseInt(tokenBirdDay.nextToken().substring(2));
		int month = Integer.parseInt(tokenBirdDay.nextToken());
		int day = Integer.parseInt(tokenBirdDay.nextToken());
		if(pendudukForm.getJenisKelamin() == 1) //1 means women
			day += 40; 
		
		String sixDigitSecod = "" + String.format("%02d", day) + String.format("%02d", month) + String.format("%02d", year);
		
		String nikTillDomisili = sixDigitFirst + sixDigitSecod;
		String lastNikInDomisili = pendudukMapper.getLatestPendudukInDomisili(nikTillDomisili);
		if(lastNikInDomisili != null) {
			int last = Integer.valueOf(lastNikInDomisili.substring(nikTillDomisili.length()));
			last++;
			return nikTillDomisili + String.format("%04d", last);
		}
		return nikTillDomisili + "0000";
	}

	public PendudukFormModel getDataPendudukForForm(String nik) {
		
		return pendudukMapper.getPendudukFrom(nik);

	}

	public String updateDataPenduduk(String nik, PendudukFormModel pendudukForm) {
		String newNik = generateNIKFromOldNIK(pendudukForm, nik);
		pendudukForm.setNik(newNik);
		pendudukMapper.updatePenduduk(nik, pendudukForm);
		return newNik;
	}
	
	
	private String generateNIKFromOldNIK(PendudukFormModel pendudukForm, String oldNik) {
		String kodeKecamatan = kecamatanMapper.getKodeKecamatanByIdKeluarga(pendudukForm.getIdKeluarga());
		String sixDigitFirst = kodeKecamatan.substring(0, kodeKecamatan.length() - 1);
		
		StringTokenizer tokenBirdDay = new StringTokenizer(pendudukForm.getTanggalLahir(), "-");
		//2017-05-24
		int year = Integer.parseInt(tokenBirdDay.nextToken().substring(2));
		int month = Integer.parseInt(tokenBirdDay.nextToken());
		int day = Integer.parseInt(tokenBirdDay.nextToken());
		if(pendudukForm.getJenisKelamin() == 1) //1 means women
			day += 40; 
		
		String sixDigitSecod = "" + String.format("%02d", day) + String.format("%02d", month) + String.format("%02d", year);
		
		String nik = sixDigitFirst + sixDigitSecod;

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> id keluarga: " + pendudukForm.getIdKeluarga());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> kode kecamatan: " + kodeKecamatan);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> second : " + sixDigitSecod);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> nik :" + nik.trim());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> old nik trim : " + oldNik.substring(0, nik.length()).trim());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> condisi : " + oldNik.substring(0, nik.length()).trim().equals(nik.trim()));
		
		//no nik change
		if(oldNik.substring(0, nik.length()).trim().equals(nik.trim())) {
			return oldNik;
		}
		
		String lastNikInDomisili = pendudukMapper.getLatestPendudukInDomisili(nik);
		if(lastNikInDomisili != null) {
			int last = Integer.valueOf(lastNikInDomisili.substring(nik.length()));
			last++;
			return nik + String.format("%04d", last);
		}
		return nik + "0000";
	}

	public boolean updateDataStatusKematianPenduduk(String nik) {
		PendudukFormModel pendudukForm = pendudukMapper.getPendudukFrom(nik); //impossible null
		System.out.println("1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> isWafat: " + pendudukForm.isWafat());
		pendudukForm.setWafat( !pendudukForm.isWafat() );
		System.out.println("2 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> isWafat: " + pendudukForm.isWafat());
		
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
	
	
}
