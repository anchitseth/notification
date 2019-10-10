package nus.iss.eatngreet.notification.serviceimpl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import nus.iss.eatngreet.notification.responsedto.CommonResponseDto;
import nus.iss.eatngreet.notification.service.MailService;
import nus.iss.eatngreet.notification.util.Constants;
import nus.iss.eatngreet.notification.util.ResponseUtil;
import nus.iss.eatngreet.notification.util.Util;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String eatNGreetEmailId;

	private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

	@Override
	public CommonResponseDto sendConfirmationEmail(Map<String, Object> reqObj, HttpServletRequest request) {
		CommonResponseDto response = new CommonResponseDto();
		if (Util.checkAuthHeader(request)) {
			Timestamp timestamp = new Timestamp((long) reqObj.get("servingDate"));
			Date date = new Date(timestamp.getTime() - 8 * 60 * 60 * 1000);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy' 'HH:mm");
			String body = "Hey " + reqObj.get("name") + ","
					+ "\n\nThanks for using our platform to share food. \nThis mail is to confirm you that you've successfully posted a meal on the Eat-N-Greet platform to serve "
					+ reqObj.get("count") + " people on " + simpleDateFormat.format(date) + "."
					+ "\nFor more details about this order, kindly visit your orders section.";
			sendMail((String) reqObj.get("email"), Constants.CONFIRMATION_MAIL_SUBJECT, body);
			logger.info("Confirmation mail sent successfully.");
			ResponseUtil.prepareResponse(response, "Confirmation mail was sent successfully.", Constants.STATUS_SUCCESS,
					"Confirmation mail was sent successfully.", true);
		} else {
			logger.error(Constants.INVALID_AUTH_TOKEN_ERROR_MESSAGE);
			ResponseUtil.prepareResponse(response, Constants.INVALID_AUTH_TOKEN_ERROR_MESSAGE, Constants.STATUS_FAILURE,
					Constants.INVALID_AUTH_TOKEN_ERROR_MESSAGE, false);
		}
		return response;
	}

	@Override
	public CommonResponseDto sendJoiningMealEmail(Map<String, Object> reqObj, HttpServletRequest request) {
		logger.info("sendJoiningMealEmail() of MailServiceImpl.");
		CommonResponseDto response = new CommonResponseDto();
		if (Util.checkAuthHeader(request)) {
			String guestName = (String) reqObj.get("guestName");
			String hostName = (String) reqObj.get("hostName");
			String guestEmailId = (String) reqObj.get("guestEmailId");
			String hostEmailId = (String) reqObj.get("hostEmailId");
			Long servingDate = (Long) reqObj.get("servingDate");
			Integer maxCount = (Integer) reqObj.get("maxCount");
			Integer guestCount = (Integer) reqObj.get("guestCount");
			Timestamp timestamp = new Timestamp(servingDate);
			Date date = new Date(timestamp.getTime() - 8 * 60 * 60 * 1000);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy' 'HH:mm");
			String guestMailBody = "Hey " + guestName + ","
					+ "\n\nWe're happy to see that you were able to find the meal that you love on our platform."
					+ "\nThis mail is to notify you that your presence is expected for the same on "
					+ simpleDateFormat.format(date)
					+ ". For more details about this order, kindly visit your orders section.";
			String hostMailBody = "Hey " + hostName + "," + "\n\nThanks for using our platform to share <3 and food."
					+ "\nThis mail is to inform you that a guest has registered for the meal that you're serving on "
					+ simpleDateFormat.format(date) + "." + "\nCurrent guest count: " + guestCount + "/" + maxCount
					+ "." + "\nFor more details about this order, kindly visit your orders section.";
			sendMail(guestEmailId, Constants.JOINING_MAIL_SUBJECT, guestMailBody);
			sendMail(hostEmailId, Constants.NOTIFY_HOST_MAIL_SUBJECT, hostMailBody);
			logger.info("Joining mail sent successfully.");
			ResponseUtil.prepareResponse(response, "Successfully sent joining mail.", Constants.STATUS_SUCCESS,
					"Successfully sent joining mail.", true);
		} else {
			logger.error(Constants.INVALID_AUTH_TOKEN_ERROR_MESSAGE);
			ResponseUtil.prepareResponse(response, Constants.INVALID_AUTH_TOKEN_ERROR_MESSAGE, Constants.STATUS_FAILURE,
					Constants.INVALID_AUTH_TOKEN_ERROR_MESSAGE, false);
		}
		return response;
	}

	@Override
	public CommonResponseDto sendOnboardingEmail(Map<String, Object> reqObj, HttpServletRequest request) {
		logger.info("sendOnboardingEmail() of MailServiceImpl.");
		CommonResponseDto response = new CommonResponseDto();
		if (Util.checkAuthHeader(request)) {
			String userName = (String) reqObj.get("name");
			String userEmailId = (String) reqObj.get("email");
			String mailBody = "Hey " + userName + "," + "\n\nWe're happy to have you onboard."
					+ "\nAs a welcome gift, SGD 10 has been credited to your account for you to have your taste buds satisfied.";
			sendMail(userEmailId, Constants.ONBOARDING_MAIL_SUBJECT, mailBody);
			logger.info("On boarding mail sent successfully.");
			ResponseUtil.prepareResponse(response, "Successfully sent onboarding mail.", Constants.STATUS_SUCCESS,
					"Successfully sent onboarding mail.", true);
		} else {
			logger.error(Constants.INVALID_AUTH_TOKEN_ERROR_MESSAGE);
			ResponseUtil.prepareResponse(response, Constants.INVALID_AUTH_TOKEN_ERROR_MESSAGE, Constants.STATUS_FAILURE,
					Constants.INVALID_AUTH_TOKEN_ERROR_MESSAGE, false);
		}
		return response;
	}

	private boolean sendMail(String to, String subject, String body) {
		logger.info("sendMail() of MailServiceImpl.");
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setFrom(eatNGreetEmailId);
		message.setSubject(subject);
		message.setText(body + Constants.MAIL_SIGNATURE);
		javaMailSender.send(message);
		return true;
	}
}
