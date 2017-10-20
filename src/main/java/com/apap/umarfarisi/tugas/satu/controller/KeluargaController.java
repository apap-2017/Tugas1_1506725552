package com.apap.umarfarisi.tugas.satu.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
			return "response-data-keluarga";
		}
		
		model.addAttribute("nkk", nkk);		
		return "response-failed-data-keluarga";
	}
	
	@RequestMapping(value = "/keluarga/tambah" , method = RequestMethod.GET)
	public String formTambahKeluarga(Model model) {
		model.addAttribute("keluargaForm", new KeluargaFormModel());
		return "form-tambah-keluarga";
	}
	
	
	@RequestMapping(value = "/keluarga/tambah" , method = RequestMethod.POST)
	public String formTambahKeluarga(@Valid @ModelAttribute("keluargaForm") KeluargaFormModel keluargaForm, BindingResult bindingResult,
			Model model) {
		
		if(bindingResult.hasErrors()) {
			return "form-tambah-keluarga";
		}
		
		String nkk = "";//keluargaService.addDataKeluarga(keluargaForm);
		model.addAttribute("nkk", nkk);
		return "response-tambah-keluarga";
	}
	
	@RequestMapping(value = "/keluarga/ubah/{nkk}", method = RequestMethod.GET)
	public String formUbahKeluarga(@PathVariable("nkk") String nkk, Model model) {
		KeluargaFormModel keluargaForm = keluargaService.getDataKeluargaForForm(nkk);
		if(keluargaForm != null) {
			model.addAttribute("keluargaForm", keluargaForm);
			return "form-ubah-keluarga";
		}
		model.addAttribute("nkk", nkk);
		return "not-found-data-keluarga-by-nkk";
		
	}
	
	@RequestMapping(value = "/keluarga/ubah/{nkk}", method = RequestMethod.POST)
	public String formUbahKeluarga(@PathVariable("nkk") String nkk ,@ModelAttribute("keluargaForm") KeluargaFormModel keluargaForm, Model model) {
		
		String newNkk = keluargaService.updateDataKeluarga(nkk, keluargaForm);
		
		model.addAttribute("nkk", newNkk);
		return "response-ubah-keluarga";
	}
	
}
