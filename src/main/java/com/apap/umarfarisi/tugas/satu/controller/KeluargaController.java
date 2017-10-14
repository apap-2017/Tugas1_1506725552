package com.apap.umarfarisi.tugas.satu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.umarfarisi.tugas.satu.model.KeluargaFormModel;
import com.apap.umarfarisi.tugas.satu.model.KeluargaViewModel;
import com.apap.umarfarisi.tugas.satu.service.KeluargaService;

@Controller
public class KeluargaController {
	
	@Autowired
	private KeluargaService keluargaService;
	
	@GetMapping("/keluarga")
	public String responseTampilkanDataKeluargaBesertaDaftarAnggotanyaBerdasarkanNomorKK(
			@RequestParam(value = "nkk", required = true) String nkk, Model model) {
		
		KeluargaViewModel keluarga = keluargaService.getKeluargaByNomorKK(nkk);
		
		if(keluarga != null) {
			model.addAttribute("keluarga", keluarga);
			return "response-data-keluarga-berdasarkan-kk";
		}
		
		model.addAttribute("nkk", nkk);		
		return "response-failed-data-keluarga-berdasarkan-kk";
	}
	
	@RequestMapping(value = "/keluarga/tambah" , method = RequestMethod.GET)
	public String formTambahKeluarga(@ModelAttribute("keluargaForm") KeluargaFormModel keluargaFrom) {
		return "form-tambah-keluarga";
	}
	
	
	@RequestMapping(value = "/keluarga/tambah" , method = RequestMethod.POST)
	public String formTambahKeluarga(@ModelAttribute("keluargaForm") KeluargaFormModel keluargaForm, Model model) {
		String nkk = keluargaService.addDataKeluarga(keluargaForm);
		model.addAttribute("nkk", nkk);
		return "response-tambah-keluarga";
	}
	
}
