package com.uuauth.login.resources.history;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class HistoryJsLoader {
	
	private static String	STYLE	= null;
	static {
		STYLE = readFile(HistoryJsLoader.class.getResourceAsStream("/"
				+ HistoryJsLoader.class.getPackage().getName()
						.replaceAll("\\.", "/") + "/history.js"));
	}
	
	public final static String getJs() {
		return STYLE;
	}
	
	private final static String readFile(InputStream is) {
		String content = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		
		try {
			String temp = reader.readLine();
			
			while (temp != null) {
				content += (temp + "\n");
				temp = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return content;
	}
}
