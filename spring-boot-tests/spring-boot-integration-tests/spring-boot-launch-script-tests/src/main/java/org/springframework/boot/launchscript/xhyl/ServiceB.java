package org.springframework.boot.launchscript.xhyl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceB {
	private final ServiceA serviceA;

	@Autowired
	public ServiceB(ServiceA serviceA) {
		this.serviceA = serviceA;
	}
}