package com.apap.umarfarisi.tugas.satu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.umarfarisi.tugas.satu.mapper.PendudukMapper;
import com.apap.umarfarisi.tugas.satu.model.PendudukFormModel;
import com.apap.umarfarisi.tugas.satu.model.PendudukViewModel;

@Service
public class PendudukService {
	
	@Autowired
	private PendudukMapper pendudukMapper;
	
	public PendudukViewModel getDataPendudukBerdasarkanNik(String nik) {
		return pendudukMapper.getPendudukView(nik);
	}
	
	public void addDataPenduduk(PendudukFormModel pendudukForm) {
		
		pendudukForm.setNik("SET DONG NIK NYA");
		
		pendudukMapper.addPenduduk(pendudukForm);
	}
	
}
