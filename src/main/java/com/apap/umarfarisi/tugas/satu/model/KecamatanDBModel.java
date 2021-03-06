package com.apap.umarfarisi.tugas.satu.model;

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
public class KecamatanDBModel {
	
	private long id;
	private String idKota;
	private String kodeKecamatan;
	private String namaKecamatan;
	
}
