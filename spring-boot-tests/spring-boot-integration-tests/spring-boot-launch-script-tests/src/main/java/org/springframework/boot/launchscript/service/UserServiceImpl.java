package org.springframework.boot.launchscript.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class UserServiceImpl implements UserService {

	@Override
	public void createUser(String name) {
		System.out.println("Creating user " + name);
	}
}
