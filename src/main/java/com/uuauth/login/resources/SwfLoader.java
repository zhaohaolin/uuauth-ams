package com.uuauth.login.resources;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public abstract class SwfLoader {
	
	public final static void getFile(HttpServletResponse response,
			String fileName) throws IOException {
		InputStream is = SwfLoader.class.getResourceAsStream("/"
				+ SwfLoader.class.getPackage().getName().replaceAll("\\.", "/")
				+ "/" + fileName);
		ServletOutputStream os = response.getOutputStream();
		int count = is.available();
		byte[] bs = new byte[count];
		int readCount = 0;
		
		while (readCount < count) {
			readCount += is.read(bs, readCount, count - readCount);
		}
		os.write(bs);
		os.flush();
		is.close();
		os.close();
		return;
	}
	
}
