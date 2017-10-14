package com.apap.umarfarisi.tugas.satu.service;

import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.umarfarisi.tugas.satu.mapper.KecamatanMapper;
import com.apap.umarfarisi.tugas.satu.mapper.PendudukMapper;
import com.apap.umarfarisi.tugas.satu.model.PendudukFormModel;
import com.apap.umarfarisi.tugas.satu.model.PendudukViewModel;

@Service
public class PendudukService {
	
	@Autowired
	private PendudukMapper pendudukMapper;
	@Autowired
	private KecamatanMapper kecamatanMapper;
	
	public PendudukViewModel getDataPendudukBerdasarkanNik(String nik) {
		return pendudukMapper.getPendudukView(nik);
	}
	
	public String addDataPenduduk(PendudukFormModel pendudukForm) {
		String nik = generateNIK(pendudukForm);
		pendudukForm.setNik(nik);
		pendudukMapper.addPenduduk(pendudukForm);
		
		return nik;
	}

	private String generateNIK(PendudukFormModel pendudukForm) {
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
		String newNik = generateNIK(pendudukForm);
		pendudukForm.setNik(newNik);
		pendudukMapper.updatePenduduk(nik, pendudukForm);
		return newNik;
	}
	
}
