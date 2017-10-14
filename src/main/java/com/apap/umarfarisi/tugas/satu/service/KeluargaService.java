package com.apap.umarfarisi.tugas.satu.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.umarfarisi.tugas.satu.mapper.KecamatanMapper;
import com.apap.umarfarisi.tugas.satu.mapper.KeluargaMapper;
import com.apap.umarfarisi.tugas.satu.mapper.KelurahanMappper;
import com.apap.umarfarisi.tugas.satu.mapper.PendudukMapper;
import com.apap.umarfarisi.tugas.satu.model.KeluargaDBModel;
import com.apap.umarfarisi.tugas.satu.model.KeluargaFormModel;
import com.apap.umarfarisi.tugas.satu.model.KeluargaViewModel;
import com.apap.umarfarisi.tugas.satu.model.PendudukModel;

@Service
public class KeluargaService {
	
	@Autowired
	private KeluargaMapper keluargaMapper;
	@Autowired
	private PendudukMapper pendudukMapper;
	@Autowired
	private KelurahanMappper kelurahanMappper;
	@Autowired
	private KecamatanMapper kecamatanMapper;
	
	public KeluargaViewModel getKeluargaByNomorKK(String nkk) {
		
		KeluargaViewModel keluarga = keluargaMapper.getKeluargaView(nkk);
		
		if(keluarga != null) {
			
			List<PendudukModel> penduduks = pendudukMapper.getAllPendudukByIdKeluarga(keluarga.getId());
			
			if(penduduks != null)
				keluarga.setPendudukModels(penduduks);
			else
				keluarga.setPendudukModels(new ArrayList<>());
			
			return keluarga;
		}
		
		return null;
	}
	
	public String addDataKeluarga(KeluargaFormModel keluargaForm) {
		
		KeluargaDBModel keluargaDB = new KeluargaDBModel();
		keluargaDB.setAlamat(keluargaForm.getAlamat());

		
		Long idKelurahan = kelurahanMappper.getIdKelurahan(keluargaForm.getKota(), keluargaForm.getKecamatan(), keluargaForm.getKelurahan());
		
		//TODO VALIDATION FORM , it is possible idKelurahan null which is not exit
		
		keluargaDB.setIdKelurahan(idKelurahan);
		keluargaDB.setNkk(generateNKK(keluargaForm));
		keluargaDB.setRt(keluargaForm.getRt());
		keluargaDB.setRw(keluargaForm.getRw());
		keluargaDB.setTidakBerlaku(false);
		
		keluargaMapper.addDataKeluarga(keluargaDB);
		
		return keluargaDB.getNkk();
	}

	private String generateNKK(KeluargaFormModel keluargaForm) {
		
		//create first 6 digit
		String kodeKecamatan = kecamatanMapper.getKodeKecamatan(keluargaForm.getKecamatan(), keluargaForm.getKota());
		String sixDigitFirst = kodeKecamatan.substring(0, kodeKecamatan.length() - 1);
		
		//create second 6 digit
		//get KK created date
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH);
		int year = Integer.valueOf(String.valueOf(calendar.get(Calendar.YEAR)).substring(2));
		String sixDigitSecond = String.format("%02d", day) + String.format("%02d", month) + String.format("%02d", year);
		
		//uncomplete nkk which lack of 4 digit last
		String nkk = sixDigitFirst + sixDigitSecond;
		
		//create last 4 digit
		String lastNkk = keluargaMapper.getLatestNKK(nkk);
		int order = 0;
		if(lastNkk != null) {
			order = Integer.valueOf(lastNkk.substring(nkk.length()));
			order++;
		}
		
		//complete nkk
		nkk += String.format("%04d", order);
		
		return nkk;
	}
	
}
