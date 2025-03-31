package org.springframework.boot.launchscript.xhyl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

//@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class CircleDependent1 {

	@Resource
	private CircleDependent2 circleDependent2;
}
