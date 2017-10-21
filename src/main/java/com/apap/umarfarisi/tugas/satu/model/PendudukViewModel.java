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
@Getter
@Setter
@ToString

public class PendudukViewModel {
	
	private String nik;
	private String nama;
	private String tempatLahir;
	private String tanggalLahir;
	private String alamat;
	private String rt;
	private String rw;
	private String namaKelurahan;
	private String namaKecamatan;
	private String namaKota;
	private String golonganDarah;
	private String agama;
	private String statusPerkawinan;
	private String pekerjaan;
	private boolean isWafat;
	private boolean isWni;
	private String jenisKelamin;
	
}
