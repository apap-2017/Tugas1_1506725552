package com.apap.umarfarisi.tugas.satu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PendudukDBModel {
	
	private long id;
	private String nik;
	private String nama;
	private String tempatLahir;
	private String tanggalLahir;
	private int jenisKelamin;
	private boolean isWni;
	private long idKeluarga;
	private String agama;
	private String pekerjaan;
	private String statusPerkawinan;
	private String statusDalamKeluarga;
	private String golonganDarah;
	private boolean isWafat;
	
}
