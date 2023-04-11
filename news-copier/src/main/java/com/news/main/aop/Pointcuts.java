package com.news.main.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
	
	@Pointcut("execution(* com.news.main.services..*.save(..))")
	public void allServiceSaveMethods() {
	}
	
	@Pointcut("execution(* com.news.main.services..*.*delete*(..))")
	public void allServiceDeleteMethods() {
	}

}
