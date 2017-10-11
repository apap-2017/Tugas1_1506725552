package com.apap.umarfarisi.tugas.satu.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PendudukModel {
	
	private long id;
	private String nik;
	private String nama;
	private String tempatLahir;
	private Date tanggalLahir;
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
