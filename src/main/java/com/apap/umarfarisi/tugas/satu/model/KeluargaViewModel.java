package com.apap.umarfarisi.tugas.satu.model;

import java.util.List;

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
public class KeluargaViewModel {
	
	private String id;
	private String nkk;
	private String alamat;
	private String rt;
	private String rw;
	private String namaKelurahan;
	private String namaKecamatan;
	private String namaKota;
	private List<PendudukDBModel> anggotaKeluargas;
	
}
