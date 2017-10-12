package com.apap.umarfarisi.tugas.satu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.umarfarisi.tugas.satu.mapper.KeluargaMapper;
import com.apap.umarfarisi.tugas.satu.mapper.PendudukMapper;
import com.apap.umarfarisi.tugas.satu.model.KeluargaViewModel;
import com.apap.umarfarisi.tugas.satu.model.PendudukModel;

@Service
public class KeluargaService {
	
	@Autowired
	private KeluargaMapper keluargaMapper;
	@Autowired
	private PendudukMapper pendudukMapper;
	
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
	
}
