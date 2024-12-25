package org.jsp.ekart.controller;

import org.jsp.ekart.dto.Vendor;

import org.jsp.ekart.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class EcartController {

	@Autowired
	VendorService vendorService;

	@GetMapping("/")
	public String loadHomePage() {
		return "home.html";
	}

	@GetMapping("/vendor/otp/{id}")
	public String loadOtpPage(@PathVariable int id, ModelMap map) {
		map.put("id", id);
		return "vendor-otp.html";
	}

	@GetMapping("/vendor/register")
//	public String loadVendorRegistration(ModelMap map,Vendor vendor) {
//		map.put("vendor", vendor);
//		return "vendor-register.html";

	public String loadVendorRegistration(ModelMap map, Vendor vendor) {
		return vendorService.loadRegistration(map, vendor);

	}

	@PostMapping("/vendor/register")
//	public String vendorRegistration(@Valid Vendor vendor,BindingResult result) {
//		if(result.hasErrors())
//			return "vendor-register.html";
//		else {
//		System.err.println(vendor);	
//		return "redirect:https://www.youtube.com";
//		}
//	}

//	public String vendorRegistration(@Valid Vendor vendor, BindingResult result) {
//		return vendorService.registration(vendor, result);
//	}

	public String vendorRegistration(@Valid Vendor vendor, BindingResult result, HttpSession Session) {
		return vendorService.registration(vendor, result, Session);
	}

	@PostMapping("/vendor/otp")
//	public String verifyOtp(@RequestParam int id, @RequestParam int otp) {
//		return vendorService.verifyOtp(id, otp);
//	}

	public String verifyOtp(@RequestParam int id, @RequestParam int otp, HttpSession session) {
		return vendorService.verifyOtp(id, otp, session);
	}

	@GetMapping("/vendor/login")
	public String loadLogin() {
		return "vendor-login.html";
	}

	@PostMapping("/vendor/login")
	public String login(@RequestParam String email, @RequestParam String password, HttpSession session) {
		return vendorService.login(email, password, session);
	}

	@GetMapping("/vendor/home")
	public String loadHome(HttpSession session) {
		if (session.getAttribute("vendor") != null)
			return "vendor-home.html";
		else {
			session.setAttribute("failure", "Invalid Session, First Login");
			return "redirect:/vendor/login";
		}

	}
}
