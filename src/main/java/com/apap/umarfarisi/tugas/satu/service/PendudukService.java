package com.apap.umarfarisi.tugas.satu.service;

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
//		String nik = generateNIK(pendudukForm);
//		pendudukForm.setNik(nik);
		
		pendudukMapper.addPenduduk(new PendudukFormModel("1234", "1-1-1990", "tempatLahirAAAAA", 
				1, true, 172, "Islam", "pekerjaan AAAAA", "status perkawinana AAAAA", "status dalam keluarga AAAAA",
				"golongan darah AAAAA", "2017-05-14", true));
	}

	private String generateNIK(PendudukFormModel pendudukForm) {
		String kodeKecamatan = kecamatanMapper.getKodeKecamatanByIdKeluarga(pendudukForm.getIdKeluarga());
		String sixDigitFirst = kodeKecamatan.substring(0, kodeKecamatan.length() - 1);
		String sixDigitSecod = pendudukForm.getTanggalLahir().replace("-", "");
		if(pendudukForm.getJenisKelamin() == 1) { //1 means women
			int dayBird = Integer.valueOf(sixDigitSecod.substring(0,2));
			dayBird += 40; 
			sixDigitSecod = dayBird + sixDigitSecod.substring(2);
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
