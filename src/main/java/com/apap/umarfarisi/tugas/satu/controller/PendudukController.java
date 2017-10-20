package com.apap.umarfarisi.tugas.satu.controller;

import java.util.List;

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

import com.apap.umarfarisi.tugas.satu.model.KecamatanDBModel;
import com.apap.umarfarisi.tugas.satu.model.KelurahanDBModel;
import com.apap.umarfarisi.tugas.satu.model.KotaDBModel;
import com.apap.umarfarisi.tugas.satu.model.PendudukDBModel;
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
			return "response-data-penduduk";
		}
		model.addAttribute("nik", nik);
		return "response-failed-data-penduduk";
	}
	
	@RequestMapping(value = "/penduduk/tambah" , method = RequestMethod.GET)
	public String formTambahPenduduk(Model model) {
		PendudukDBModel pendudukForm = new PendudukDBModel();
		model.addAttribute("pendudukForm", pendudukForm);
		
		return "form-tambah-penduduk";
		
	}
	
	@RequestMapping(value = "/penduduk/tambah" , method = RequestMethod.POST)
	public String formTambahPenduduk(@Valid @ModelAttribute("pendudukForm") PendudukDBModel pendudukForm,
			BindingResult result,
			Model model) {
		
		if(result.hasErrors()) {
			return "form-tambah-penduduk";
		}
		
		String nik = pendudukService.addDataPenduduk(pendudukForm);
		
		if(nik == null) {
			model.addAttribute("error_id_keluarga", true);
			return "form-tambah-penduduk";
		}
		
		model.addAttribute("nik", nik);
		return "response-tambah-penduduk";
	}
	
	@RequestMapping(value = "/penduduk/ubah/{nik}", method = RequestMethod.GET)
	public String formUpdatePenduduk(@PathVariable(value = "nik") String nik, Model model) {
		
		PendudukDBModel pendudukForm = pendudukService.getDataPendudukForForm(nik);
		
		if(pendudukForm != null) {
			model.addAttribute("pendudukForm", pendudukForm);
			return "form-ubah-penduduk";
		}
		
		model.addAttribute("nik", nik);
		return "not-found-data-penduduk-by-nik";
		
	}
	
	@RequestMapping(value = "/penduduk/ubah/{nik}" , method = RequestMethod.POST)
	public String formUpdatePenduduk(@PathVariable(value = "nik") String nik, Model model,
			@Valid @ModelAttribute("pendudukForm") PendudukDBModel pendudukForm,
			BindingResult result) {
		
		if(result.hasErrors()) {
			return "form-ubah-penduduk";
		}
		
		String newNik = pendudukService.updateDataPenduduk(nik, pendudukForm);
		
		if(newNik == null) {
			model.addAttribute("error_id_keluarga", true);
			return "form-tambah-penduduk";
		}
		
		model.addAttribute("nik", newNik);
		return "response-ubah-penduduk";
	}
	
	@RequestMapping(value = "/penduduk" , method = RequestMethod.POST)
	public String formUbahStatusKematianPenduduk(Model model, @RequestParam(value = "nik") String nik) {
		
		boolean isWafat = pendudukService.updateDataStatusKematianPenduduk(nik);
		model.addAttribute("wafat", isWafat);
		model.addAttribute("nik", nik);
		return "redirect:/penduduk?nik=" + nik;
		
	}

	@RequestMapping(value = "/penduduk/cari")
	public String searchPenduduk(@RequestParam(value = "id_kota" , required = false) Long idKota, 
			@RequestParam(value = "id_kecamatan" , required = false) Long idKecamatan, 
			@RequestParam(value = "id_kelurahan" , required = false) Long idKelurahan,
			Model model) {
		
		if(idKota == null && idKecamatan == null && idKelurahan == null) { //first
			
			List<KotaDBModel> kotas = pendudukService.getDaftarKota();
			model.addAttribute("kotas", kotas);
			return "form-search-penduduk-first";
			
		}else if(idKota != null && idKecamatan == null && idKelurahan == null) { //second
			
			KotaDBModel kota = pendudukService.getNamaKotaYangDipilih(idKota);
			List<KecamatanDBModel> kecamatans = pendudukService.getDaftarKecamatanPadaSuatuKota(idKota);
			model.addAttribute("kota", kota);
			model.addAttribute("kecamatans", kecamatans);
			
			return "form-search-penduduk-second";
			
		}else if(idKota != null && idKecamatan != null && idKelurahan == null) { //third
			
			KotaDBModel kota = pendudukService.getNamaKotaYangDipilih(idKota);
			KecamatanDBModel kecamatan = pendudukService.getNamaKecamatanYangDipilih(idKecamatan);
			List<KelurahanDBModel> kelurahans = pendudukService.getDaftarKelurahanPadaSuatuKecamatan(idKecamatan);
			model.addAttribute("kota", kota);
			model.addAttribute("kecamatan", kecamatan);
			model.addAttribute("kelurahans", kelurahans);
			
			return "form-search-penduduk-third";
			
		}else{ //last
			
			KotaDBModel kota = pendudukService.getNamaKotaYangDipilih(idKota);
			KecamatanDBModel kecamatan = pendudukService.getNamaKecamatanYangDipilih(idKecamatan);
			KelurahanDBModel kelurahan = pendudukService.getNamaKelurahanYangDipilih(idKelurahan);
			
			model.addAttribute("kota", kota);
			model.addAttribute("kecamatan", kecamatan);
			model.addAttribute("kelurahan", kelurahan);
			
			List<PendudukDBModel> penduduks = pendudukService.getDaftarPendudukBerdasarkanTempatYangDipilih(idKota, idKecamatan, idKelurahan);
			model.addAttribute("penduduks", penduduks);
			
			return "form-search-penduduk-fourth";
			
		}
	}
	
	
}
