package com.apap.umarfarisi.tugas.satu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class KotaDBModel {

	private long id;
	private String kodeKota;
	private String namaKota;
	
}
