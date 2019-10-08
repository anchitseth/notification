package nus.iss.eatngreet.notification.restcontroller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nus.iss.eatngreet.notification.responsedto.CommonResponseDto;
import nus.iss.eatngreet.notification.service.MailService;

@RestController
@RequestMapping("notify")
public class MailRestController {

	@Autowired
	MailService mailService;

	@PostMapping("/confirm-posting-meal")
	public CommonResponseDto sendConfirmationEmail(@RequestBody Map<String, Object> reqObj, HttpServletRequest request) {
		return mailService.sendConfirmationEmail(reqObj, request);
	}
	
	@PostMapping("/confirm-joining-meal")
	public CommonResponseDto sendJoiningEmail(@RequestBody Map<String, Object> reqObj, HttpServletRequest request) {
		return mailService.sendJoiningMealEmail(reqObj, request);
	}
	
	@PostMapping("/onboarding")
	public CommonResponseDto sendOnBoardingEmail(@RequestBody Map<String, Object> reqObj, HttpServletRequest request) {
		return mailService.sendOnboardingEmail(reqObj, request);
	}
	
}
