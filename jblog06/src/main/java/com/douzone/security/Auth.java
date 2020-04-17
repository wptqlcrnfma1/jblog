package com.douzone.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
	public enum Role{USER, ADMIN}
	
	String value() default "USER";
	
	
	public Role role() default Role.USER; //쓰는게 복잡하다고 일단 안씀
	boolean test() default false;
}


