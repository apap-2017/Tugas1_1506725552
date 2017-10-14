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
	
	public void addDataPenduduk(PendudukFormModel pendudukForm) {
		String nik = generateNIK(pendudukForm);
		pendudukForm.setNik(nik);
		pendudukMapper.addPenduduk(pendudukForm);
	}

	private String generateNIK(PendudukFormModel pendudukForm) {
		String kodeKecamatan = kecamatanMapper.getKodeKecamatanByIdKeluarga(pendudukForm.getIdKeluarga());
		String sixDigitFirst = kodeKecamatan.substring(0, kodeKecamatan.length() - 1);
		String sixDigitSecod = pendudukForm.getTanggalLahir().replace("-", "");
		
		StringTokenizer tokenBirdDay = new StringTokenizer(pendudukForm.getTanggalLahir(), "-");
		//2017-05-24
		int year = Integer.parseInt(tokenBirdDay.nextToken().substring(2));
		int month = Integer.parseInt(tokenBirdDay.nextToken());
		int day = Integer.parseInt(tokenBirdDay.nextToken());
		if(pendudukForm.getJenisKelamin() == 1) { //1 means women
			day += 40; 
			sixDigitSecod = "" + day + month + year;
		}
		
		String nikTillDomisili = sixDigitFirst + sixDigitSecod;
		String lastNikInDomisili = pendudukMapper.getLatestPendudukInDomisili(nikTillDomisili);
		if(lastNikInDomisili != null) {
			int last = Integer.valueOf(lastNikInDomisili.substring(11));
			last++;
			return nikTillDomisili + String.format("%04d", last);
		}
		return nikTillDomisili + "0000";
	}
	
}
