package com.apap.umarfarisi.tugas.satu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class PendudukFormModel {
	
	private String name;
	private String tempatLahir;
	private String tanggalLahir;
	private String golonganDarah;
	private String agama;
	private String statusPerkawinan;
	private String pekerjaan;
	private boolean isWni;
	private String statusKematian;
	private String idKeluarga;
	
}
