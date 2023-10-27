package com.java4.classweb.user.controller;

import java.util.Map;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.java4.classweb.user.domain.User;
import com.java4.classweb.user.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/regist", method = RequestMethod.GET)
	public String regist() {
		return "user/regist";
	}

	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public String registPost(@RequestParam Map<String, String> map) {
		userService.add(new User(map.get("user-name"), map.get("user-id"), map.get("user-pw")));
		return "redirect:/regist";
		// redirect: 명령어 foword의경우도 동일하게 뒤에 : 붙이면된다.
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String logInPost(@RequestParam Map<String, String> map, HttpSession session) {
		User user = new User();
		user.setUserId(map.get("user-id"));
		user.setPassword(map.get("user-pw"));
		user = userService.login(user);
		if (user != null)
			session.setAttribute("userName", user.getName());

		return "redirect:/";
	}
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logOutPost(@RequestParam Map<String, String> map, HttpSession session) {
		session.setAttribute("userName", null);

		return "redirect:/";
	}

}
