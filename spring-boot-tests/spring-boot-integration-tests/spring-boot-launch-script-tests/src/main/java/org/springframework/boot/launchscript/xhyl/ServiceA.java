package org.springframework.boot.launchscript.xhyl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// ServiceA.java
@Service
public class ServiceA {

	private final ServiceB serviceB;

	@Autowired
	public ServiceA(ServiceB serviceB) {
		this.serviceB = serviceB;
	}
}