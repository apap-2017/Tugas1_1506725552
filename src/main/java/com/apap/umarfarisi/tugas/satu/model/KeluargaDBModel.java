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
public class KeluargaDBModel {
	
	private long id;
	private String nkk;
	private String alamat;
	private String rt;
	private String rw;
	private long idKelurahan;
	private boolean isTidakBerlaku;
	
}
