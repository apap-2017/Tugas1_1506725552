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

public class KelurahanModel {
	
	private long id;
	private long idKecamatan;
	private String kodeKelurahan;
	private String namaKelurahan;
	private String kodePos;

}
