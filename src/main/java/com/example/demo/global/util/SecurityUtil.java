package com.example.demo.global.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
	
	
	public static Long getLoginUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if ( auth == null || auth.getPrincipal() == null ) {
			return null;
		}
		
		return Long.valueOf(auth.getPrincipal().toString());
	}

}
