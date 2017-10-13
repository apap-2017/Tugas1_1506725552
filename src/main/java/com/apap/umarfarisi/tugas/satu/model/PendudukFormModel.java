package com.apap.umarfarisi.tugas.satu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class PendudukFormModel {
	
	private String nik;
	private String nama;
	private String tempatLahir;
	private int jenisKelamin;
	private boolean isWni;
	private String idKeluarga;
	private String agama;
	private String pekerjaan;
	private String statusPerkawinan;
	private String statusDalamKeluarga;
	private String golonganDarah;
	private String tanggalLahir;
	private boolean isWafat;

	
}
