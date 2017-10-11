package com.apap.umarfarisi.tugas.satu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.apap.umarfarisi.tugas.satu.model.PendudukViewModel;

@Mapper
public interface PendudukMapper {

	@Select("SELECT * FROM penduduk WHERE nik = #{nik}")
	public PendudukViewModel getPendudukView(@Param("nik") String nik);

}
