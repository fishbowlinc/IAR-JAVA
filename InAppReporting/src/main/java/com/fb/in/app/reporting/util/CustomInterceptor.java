package com.fb.in.app.reporting.util;

import org.hibernate.EmptyInterceptor;
import org.springframework.stereotype.Component;
@Component
public class CustomInterceptor extends EmptyInterceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	 public String onPrepareStatement(String sql) {
		sql = sql.replace("UserManagement.dbo.User", "[UserManagement].[dbo].[User]");
		return sql;
	}
}
