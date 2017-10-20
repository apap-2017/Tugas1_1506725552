package com.apap.umarfarisi.tugas.satu.model;

import javax.validation.constraints.NotNull;

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
public class KeluargaFormModel {
	
	@NotEmpty
	@NotNull
	private String alamat;
	@NotNull
	@NotEmpty
	private String rt;
	@NotNull
	@NotEmpty
	private String rw;
	@NotNull
	@NotEmpty
	private String kelurahan;
	@NotNull
	@NotEmpty
	private String kecamatan;
	@NotNull
	@NotEmpty
	private String kota;
	
}
