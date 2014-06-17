ckage sse;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Date;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * File: SseServer.java
 * Project: SSEServlet
 * @author swang
 * @version 3:40pm 6/16/14
 * Source: http://milestonenext.blogspot.com/2013/07/html5-server-sent
 *         -events-sample-with.html
 * 
 */
@WebServlet("/SseServer")
public class SseServer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Besides "text/event-stream;", Chrome also needs charset, otherwise
		// does not work
		// "text/event-stream;charset=UTF-8"
		response.setContentType("text/event-stream;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Connection", "keep-alive");

		PrintWriter out = response.getWriter();
		String filename = "longfile.txt";
		URL url = new URL("http://localhost:8080/SSEServlet/longfile.txt");
		
		out.print("data: " + "Searching in " + filename + "\n\n");
		out.flush();
		
		Scanner s = new Scanner(url.openStream());
		int id = 58;
		String event = "";
		String data = "";
		
		
//		while (true) {
		while (s.hasNext()) {				
			s.next();
			id = s.nextInt();
			s.next();
			event = s.next();
			s.nextLine();
			data = s.nextLine();
			
//			if (event.equals("personal")) {
				Date date = new Date();
				out.print(data + " id: " + id + " eventtype: " + event
						+ " in file " + "\"" + filename + "\" at "
						+ date.toString() + "\n\n");
				out.flush();
//			}
			
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		out.close();
		s.close();
	}
}
