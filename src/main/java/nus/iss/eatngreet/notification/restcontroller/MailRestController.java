package nus.iss.eatngreet.notification.restcontroller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger logger = LoggerFactory.getLogger(MailRestController.class);

	@PostMapping("/confirm-posting-meal")
	public CommonResponseDto sendConfirmationEmail(@RequestBody Map<String, Object> reqObj, HttpServletRequest request) {
		logger.info("sendConfirmationEmail() of MailRestController.Request Object: {}.", reqObj);
		return mailService.sendConfirmationEmail(reqObj, request);
	}
	
	@PostMapping("/confirm-joining-meal")
	public CommonResponseDto sendJoiningEmail(@RequestBody Map<String, Object> reqObj, HttpServletRequest request) {
		logger.info("sendJoiningEmail() of MailRestController. Request Object: {}.", reqObj);
		return mailService.sendJoiningMealEmail(reqObj, request);
	}
	
	@PostMapping("/onboarding")
	public CommonResponseDto sendOnBoardingEmail(@RequestBody Map<String, Object> reqObj, HttpServletRequest request) {
		logger.info("sendOnBoardingEmail() of MailRestController. Request Object: {}.", reqObj);
		return mailService.sendOnboardingEmail(reqObj, request);
	}
	
}
