package com.ispan.chufa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ispan.chufa.repository.CouponRepository;
import com.ispan.chufa.service.PassingQueryListService;

@Controller
@RequestMapping("/api/coupons")
public class DisplayController {
	@Autowired
	private CouponRepository Couponrepository;

	@Autowired
	private PassingQueryListService passingQueryListService;
	
	@PostMapping("/deleteQuery")
	public String deletQueryController(Model model) {
		List<Long> queryKeys = passingQueryListService.getMule();
		model.addAttribute("deleteCount",queryKeys.size());
		Couponrepository.deleteAllById(queryKeys);
	    return "/display";
	}
	
}
