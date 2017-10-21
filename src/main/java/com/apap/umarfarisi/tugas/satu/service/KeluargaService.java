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
import com.apap.umarfarisi.tugas.satu.model.PendudukDBModel;
import com.apap.umarfarisi.tugas.satu.utils.PendudukUtils;

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
			
			List<PendudukDBModel> penduduks = pendudukMapper.getAllPendudukByIdKeluarga(keluarga.getId());
			
			if(penduduks != null)
				keluarga.setAnggotaKeluargas(penduduks);
			else
				keluarga.setAnggotaKeluargas(new ArrayList<>());
			
			return keluarga;
		}
		
		return null;
	}
	
	public String addDataKeluarga(KeluargaFormModel keluargaForm) {
		
		keluargaForm.setKota(keluargaForm.getKota().toUpperCase());
		keluargaForm.setKecamatan(keluargaForm.getKecamatan().toUpperCase());
		keluargaForm.setKelurahan(keluargaForm.getKelurahan().toUpperCase());
		
		Long idKelurahan = kelurahanMappper.getIdKelurahan(keluargaForm.getKota(), keluargaForm.getKecamatan(), keluargaForm.getKelurahan());
		
		if(idKelurahan == null || idKelurahan == 0) {
			return null;
		}
		
		KeluargaDBModel keluargaDB = new KeluargaDBModel();
		keluargaDB.setAlamat(keluargaForm.getAlamat());
		
		//TODO VALIDATION FORM , it is possible idKelurahan null which is not exit
		
		keluargaDB.setIdKelurahan(idKelurahan);
		keluargaDB.setNkk(generateFirstNKK(keluargaForm));
		keluargaDB.setRt(keluargaForm.getRt());
		keluargaDB.setRw(keluargaForm.getRw());
		keluargaDB.setTidakBerlaku(false);
		
		keluargaMapper.addDataKeluarga(keluargaDB);
		
		return keluargaDB.getNkk();
	}

	private String generateFirstNKK(KeluargaFormModel keluargaForm) {
		
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

	public KeluargaFormModel getDataKeluargaForForm(String nkk) {
		KeluargaFormModel keluargaForm = keluargaMapper.getKeluargaForm(nkk);
		return keluargaForm;
	}

	public String updateDataKeluarga(String nkk, final KeluargaFormModel keluargaForm) {
		
		Long idKelurahan = kelurahanMappper.getIdKelurahan(keluargaForm.getKota(), keluargaForm.getKecamatan(), keluargaForm.getKelurahan());
		
		if(idKelurahan == null || idKelurahan == 0) {
			return null;
		}
		
		/**
		 * Change NIK anggota keluarga
		 */
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				KeluargaFormModel olds = keluargaMapper.getKeluargaForm(nkk);
				if(!keluargaForm.getKelurahan().equals(olds.getKelurahan()) || 
						!keluargaForm.getKecamatan().equals(olds.getKecamatan()) ||
						!keluargaForm.getKota().equals(olds.getKota())) {
					
					KeluargaDBModel newKeluargaDB = keluargaMapper.getKeluargaDB(nkk);
					
					List<PendudukDBModel> anggotaKeluargas = pendudukMapper.getAllPendudukByIdKeluarga(newKeluargaDB.getId());
					
					for(PendudukDBModel anggotaKeluarga : anggotaKeluargas) {
						String oldNik = anggotaKeluarga.getNik();
						String newNik = PendudukUtils.generateNIK(kecamatanMapper, pendudukMapper, anggotaKeluarga.getIdKeluarga(), 
								anggotaKeluarga.getTanggalLahir(), anggotaKeluarga.getJenisKelamin(), anggotaKeluarga.getNik());
						anggotaKeluarga.setNik(newNik);
						pendudukMapper.updatePenduduk(oldNik, anggotaKeluarga);
					}
				}
				
			}
		}).start();
		
		keluargaForm.setKota(keluargaForm.getKota().toUpperCase());
		keluargaForm.setKecamatan(keluargaForm.getKecamatan().toUpperCase());
		keluargaForm.setKelurahan(keluargaForm.getKelurahan().toUpperCase());
		
		KeluargaDBModel keluargaDB = new KeluargaDBModel();
		keluargaDB.setAlamat(keluargaForm.getAlamat());
		
		//TODO VALIDATION FORM , it is possible idKelurahan null which is not exit
		keluargaDB.setIdKelurahan(idKelurahan);
		keluargaDB.setNkk(generateFromOldNKK(keluargaForm, nkk));
		keluargaDB.setRt(keluargaForm.getRt());
		keluargaDB.setRw(keluargaForm.getRw());
		keluargaDB.setTidakBerlaku(false);
		
		keluargaMapper.updateKeluarga(nkk, keluargaDB);
		
		//return newNkk from generate nkk
		return keluargaDB.getNkk();
	}

	private String generateFromOldNKK(KeluargaFormModel keluargaForm, String oldNkk) {
		//create first 6 digit
		String kodeKecamatan = kecamatanMapper.getKodeKecamatan(keluargaForm.getKecamatan(), keluargaForm.getKota());
		String sixDigitFirst = kodeKecamatan.substring(0, kodeKecamatan.length() - 1);
		
		//get second 6 digit
		String sixDigitSecond = oldNkk.substring(6, 12);
		
		//uncomplete nkk which lack of 4 digit last
		String nkk = sixDigitFirst + sixDigitSecond;
		
		//no nkk change
		if(oldNkk.substring(0, nkk.length()).trim().equals(nkk.trim())) {
			return oldNkk;
		}
		
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
