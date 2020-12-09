package com.revature.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateStringify {
	
	public static final String stringifyNow() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();
	    return formatter.format(date);
	}
	
}
