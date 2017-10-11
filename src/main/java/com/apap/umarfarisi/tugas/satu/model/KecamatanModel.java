package com.apap.umarfarisi.tugas.satu.model;

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

public class KecamatanModel {
	
	private long id;
	private String kodeKelurahan;
	private String idKecamatan;
	private String namaKecamatan;
	private String kodePos;
	
}
