package nus.iss.eatngreet.notification.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nus.iss.eatngreet.notification.responsedto.CommonResponseDto;

public interface MailService {

	public CommonResponseDto sendConfirmationEmail(Map<String, Object> reqObj, HttpServletRequest request);
	public CommonResponseDto sendJoiningMealEmail(Map<String, Object> reqObj, HttpServletRequest request);
	public CommonResponseDto sendOnboardingEmail(Map<String, Object> reqObj, HttpServletRequest request);
}
