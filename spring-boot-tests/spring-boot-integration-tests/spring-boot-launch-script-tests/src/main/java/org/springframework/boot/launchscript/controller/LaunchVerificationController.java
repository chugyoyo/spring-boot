package org.springframework.boot.launchscript.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LaunchVerificationController {

	/**
	 * http://localhost:8080/verifyLaunch
	 * @return
	 */
	@RequestMapping("/verifyLaunch")
	public String verifyLaunch() {
		return "Launched\n";
	}
}
