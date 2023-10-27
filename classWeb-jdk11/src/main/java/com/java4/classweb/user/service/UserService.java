package com.java4.classweb.user.service;

import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java4.classweb.user.dao.UserDAO;
import com.java4.classweb.user.domain.User;

@Service
public class UserService {
	@Autowired
	UserDAO userDAO;

	public void add(User user) {
		System.out.println(cryptoPassword(user.getPassword(), "SHA-512"));
		user.setPassword(cryptoPassword(user.getPassword(), "SHA-256"));
		userDAO.add(user);
		System.out.println(user);
	}

	public User login(User user) {
		User tempUser = userDAO.get(user.getUserId());
		if (tempUser != null && tempUser.getPassword().equals(cryptoPassword(user.getPassword(), "SHA-256"))) {
			return tempUser;
		} else
			return null;
	}

	public User logout(User user) {
		User tempUser = userDAO.get(user.getUserId());
		
		if(tempUser != null) {
			
			return null;
		}else
		return null;
	}

	// 03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4 비밀번호 1234
	// 03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4 비밀번호 1234 동일
	// 835d5e8314340ab852a2f979ab4cd53e994dbe38366afb6eed84fe4957b980c8 비밀번호 232 다름
	// 암호화잘됨
	// 3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2
	// SHA-512의경우
	private String cryptoPassword(String password, String method) {
		try {
			MessageDigest md = MessageDigest.getInstance(method);
			md.update(password.getBytes());
			byte[] sha256Hash = md.digest();
			StringBuilder sb = new StringBuilder();
			for (byte b : sha256Hash) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
