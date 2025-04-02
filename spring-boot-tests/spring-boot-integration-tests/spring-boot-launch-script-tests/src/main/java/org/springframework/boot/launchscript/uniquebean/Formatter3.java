package org.springframework.boot.launchscript.uniquebean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Formatter3 implements Formatter {

	@Qualifier(value = "formatter3")
	@Autowired
	private Formatter formatter;
}
