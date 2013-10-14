package com.uuauth.login.resources.history;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class HistoryHtmlLoader {
	
	private static String	STYLE	= null;
	static {
		STYLE = readFile(HistoryHtmlLoader.class.getResourceAsStream("/"
				+ HistoryHtmlLoader.class.getPackage().getName()
						.replaceAll("\\.", "/") + "/historyFrame.html?"));
	}
	
	public final static String getHtml() {
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
