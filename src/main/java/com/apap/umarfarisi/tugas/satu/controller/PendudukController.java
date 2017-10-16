package com.apap.umarfarisi.tugas.satu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		
		String newNik = pendudukService.updateDataPenduduk(nik, pendudukForm);
		
		model.addAttribute("nik", newNik);
		return "response-ubah-penduduk";
	}
	
	@RequestMapping(value = "/penduduk/{nik}" , method = RequestMethod.GET)
	public String formUbahStatusKematianPenduduk(@PathVariable(value = "nik") String nik, Model model) {
		
		PendudukFormModel pendudukForm = pendudukService.getDataPendudukForForm(nik);
		
		model.addAttribute("nik", nik);
		

		System.out.println("-1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> nik " + pendudukForm.getNik());
		System.out.println("0 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> is wafat " + pendudukForm.isWafat());
		
		if(pendudukForm != null) {
			model.addAttribute("status", pendudukForm.isWafat() ? "aktif" : "tidak aktif");
			return "form-ubah-status-kematian-penduduk";
		}
		
		return "not-found-data-penduduk-by-nik";
		
	}
	
	@RequestMapping(value = "/penduduk/{nik}" , method = RequestMethod.POST)
	public String formUbahStatusKematianPenduduk(Model model, @PathVariable(value = "nik") String nik) {
		
		boolean isWafat = pendudukService.updateDataStatusKematianPenduduk(nik);
		model.addAttribute("status", isWafat ? "tidak aktif" : "aktif");
		model.addAttribute("nik", nik);
		return "response-ubah-status-kematian-penduduk";
		
	}

	@RequestMapping(value = "/penduduk/cari")
	public String searchPenduduk(@RequestParam(value = "id_kota" , required = false) Long idKota, 
			@RequestParam(value = "id_kecamatan" , required = false) Long idKecamatan, 
			@RequestParam(value = "id_kelurahan" , required = false) Long idKelurahan,
			Model model) {
		
		if(idKota == null && idKecamatan == null && idKelurahan == null) { //first
			
			List<KotaDBModel> kotas = pendudukService.getDaftarKota();
			model.addAttribute("kotas", kotas);
			
			return "TODO";
			
		}else if(idKota != null && idKecamatan == null && idKelurahan == null) { //second
			
			String selectedNamaKota = pendudukService.getNamaKotaYangDipilih(idKota);
			List<KecamatanDBModel> kecamatans = pendudukService.getDaftarKecamatanPadaSuatuKota(idKota);
			model.addAttribute("selected_nama_kota", selectedNamaKota);
			model.addAttribute("kecamatans", kecamatans);
			
			return "TODO";
			
		}else if(idKota != null && idKecamatan != null && idKelurahan == null) { //third
			
			String selectedNamaKota = pendudukService.getNamaKotaYangDipilih(idKota);
			String selectedNamaKecamatan = pendudukService.getNamaKecamatanYangDipilih(idKecamatan);
			List<KelurahanDBModel> kelurahans = pendudukService.getDaftarKelurahanPadaSuatuKecamatan(idKecamatan);
			model.addAttribute("selected_nama_kota", selectedNamaKota);
			model.addAttribute("selected_nama_kecamatan", selectedNamaKecamatan);
			model.addAttribute("kelurahans", kelurahans);
			
			return "TODO";
			
		}else if(idKota != null && idKecamatan != null && idKelurahan != null) { //last
			
			String selectedNamaKota = pendudukService.getNamaKotaYangDipilih(idKota);
			String selectedNamaKecamatan = pendudukService.getNamaKecamatanYangDipilih(idKecamatan);
			String selectedNamaKelurahan = pendudukService.getNamaKelurahanYangDipilih(idKelurahan);
			model.addAttribute("selected_nama_kota", selectedNamaKota);
			model.addAttribute("selected_nama_kecamatan", selectedNamaKecamatan);
			model.addAttribute("selected_nama_kelurahan", selectedNamaKelurahan);
			
			List<PendudukDBModel> penduduks = pendudukService.getDaftarPendudukBerdasarkanTempatYangDipilih(idKota, idKecamatan, idKelurahan);
			model.addAttribute("penduduks", penduduks);
			
			return "TODO";
			
		}
		
		return "TODO";
	}
	
	
}
