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

public class KeluargaModel {
	
	private long id;
	private String nomorKk;
	private String alamat;
	private String rt;
	private String rw;
	private long idKeluarahan;
	private boolean isTidakBerlaku;
	
}
