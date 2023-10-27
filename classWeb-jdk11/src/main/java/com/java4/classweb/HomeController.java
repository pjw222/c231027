package com.java4.classweb;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller // 어노테이션
// Servlet을 사용할 때 View 에 관해서 사용한다.
// views 폴더와 자동으로 엮인다, jsp 와 자동으로 엮인다.
// HTML, CSS, Javascript 등등이 아닌
// JSON을 사용하게 된다면 RestController 사용
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class); //로그 남기는 용도
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	// Request에 대한 설정   //Locale 접속 지역 Model은 request와 session 에도 들어간다 만약 redirect라면 파라미터에도 들어감
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home"; //위의 jsp와 자동으로 엮인다라는 말이 views폴더에 있는 home.jsp 파일을 반환한다.
	}
	
}
