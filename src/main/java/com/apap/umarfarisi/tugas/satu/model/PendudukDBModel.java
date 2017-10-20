package com.apap.umarfarisi.tugas.satu.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PendudukDBModel {
	
	private long id;
	@NotEmpty
	private String nik;
	@NotEmpty
	private String nama;
	@NotEmpty
	private String tempatLahir;
	@NotEmpty
	private String tanggalLahir;
	private int jenisKelamin;
	private boolean isWni;
	private long idKeluarga;
	@NotEmpty
	private String agama;
	@NotEmpty
	private String pekerjaan;
	@NotEmpty
	private String statusPerkawinan;
	@NotEmpty
	private String statusDalamKeluarga;
	private String golonganDarah;
	private boolean isWafat;
	
}
