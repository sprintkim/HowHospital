package kr.or.iei.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.iei.hospital.model.service.DoctorService;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
	@Autowired
	private DoctorService doctorService;
}
