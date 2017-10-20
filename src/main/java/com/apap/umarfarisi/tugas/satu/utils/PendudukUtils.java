package com.apap.umarfarisi.tugas.satu.utils;

import java.util.StringTokenizer;

import com.apap.umarfarisi.tugas.satu.mapper.KecamatanMapper;
import com.apap.umarfarisi.tugas.satu.mapper.PendudukMapper;

public class PendudukUtils {
	
	public static final String ERROR_BY_ID_KELUARGA = "tidak ditemukan id keluarga";
	public static final String ERROR_BY_ID_TANGGAL_LAHIR = "format tanggal lahir salah";
	
	public static String generateNIK(KecamatanMapper kecamatanMapper, PendudukMapper pendudukMapper, long idKeluarga, String tanggalLahir, int jenisKelamin, String oldNik) {
		String kodeKecamatan = kecamatanMapper.getKodeKecamatanByIdKeluarga(idKeluarga);

		if(kodeKecamatan == null || kodeKecamatan.equals("")) {
			return ERROR_BY_ID_KELUARGA;
		}
		
		String sixDigitFirst = kodeKecamatan.substring(0, kodeKecamatan.length() - 1);
		
		StringTokenizer tokenBirdDay = new StringTokenizer(tanggalLahir, "-");
		//2017-05-24
		try {
			int year = Integer.parseInt(tokenBirdDay.nextToken().substring(2));
			int month = Integer.parseInt(tokenBirdDay.nextToken());
			int day = Integer.parseInt(tokenBirdDay.nextToken());
			if(jenisKelamin == 1) //1 means women
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
		}catch (Exception e) {
			return ERROR_BY_ID_TANGGAL_LAHIR;
		}
	}
}
