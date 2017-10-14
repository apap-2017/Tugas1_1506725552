package com.apap.umarfarisi.tugas.satu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.umarfarisi.tugas.satu.model.PendudukFormModel;
import com.apap.umarfarisi.tugas.satu.model.PendudukViewModel;
import com.apap.umarfarisi.tugas.satu.service.PendudukService;

@Controller
public class PendudukController {
	
	@Autowired
	PendudukService pendudukService;
	
	@GetMapping("/penduduk")
	public String responseTampilkanDataPenduduk(@RequestParam(value = "nik", required = true) String nik, Model model) {
		PendudukViewModel penduduk = pendudukService.getDataPendudukBerdasarkanNik(nik);
		if(penduduk != null) {
			model.addAttribute("penduduk", penduduk);
			System.out.println(">>>>>>>>>>>>> "+penduduk.toString());
			return "response-data-penduduk";
		}
		model.addAttribute("nik", nik);
		return "response-failed-data-penduduk";
	}
	
	@RequestMapping(value = "/penduduk/tambah" , method = RequestMethod.GET)
	public String formTambahPenduduk(@ModelAttribute("pendudukForm") PendudukFormModel pendudukForm) {
		return "form-tambah-penduduk";
		
	}
	
	@RequestMapping(value = "/penduduk/tambah" , method = RequestMethod.POST)
	public String formTambahPenduduk(@ModelAttribute("pendudukForm") PendudukFormModel pendudukForm,
			Model model) {
		String nik = pendudukService.addDataPenduduk(pendudukForm);
		model.addAttribute("nik", nik);
		return "response-tambah-penduduk";
	}
	
	@RequestMapping(value = "/penduduk/ubah/{nik}", method = RequestMethod.GET)
	public String formUpdatePenduduk(@PathVariable(value = "nik") String nik, Model model) {
		
		PendudukFormModel pendudukForm = pendudukService.getDataPendudukForForm(nik);
		
		if(pendudukForm != null) {
			model.addAttribute("pendudukForm", pendudukForm);
			return "form-ubah-penduduk";
		}
		
		model.addAttribute("nik", nik);
		return "not-found-data-penduduk-by-nik";
		
	}
	
	@RequestMapping(value = "/penduduk/ubah/{nik}" , method = RequestMethod.POST)
	public String formUpdatePenduduk(@PathVariable(value = "nik") String nik, Model model, @ModelAttribute("pendudukForm") PendudukFormModel pendudukForm) {
		
		pendudukService.updateDataPenduduk(nik, pendudukForm);
		
		model.addAttribute("nik", nik);
		return "response-ubah-penduduk";
	}
	
}
