package org.esquivo.downloader;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class TestServer extends AbstractHandler {
	protected Server server;

	public TestServer(int port) {
		this.server = new Server(port);
		this.server.setHandler(this);
	}

	@Override
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		long size = 128;
		boolean reportSize = true;
		boolean notFound = false;
		boolean nullResponse = false;
		long sleep = 0;

		if (request.getParameter("size") != null) {
			size = Long.parseLong(request.getParameter("size"));
		}
		if (request.getParameter("reportSize") != null) {
			reportSize = "true".equals(request.getParameter("reportSize"));
		}
		if (request.getParameter("notFound") != null) {
			notFound = "true".equals(request.getParameter("notFound"));
		}
		if (request.getParameter("nullResponse") != null) {
			nullResponse = "true".equals(request.getParameter("nullResponse"));
		}
		if (request.getParameter("sleep") != null) {
			sleep = Long.parseLong(request.getParameter("sleep"));
		}

		if (nullResponse) {
			baseRequest.getConnection().getEndPoint().close();
		} else {
			if(sleep > 0) {
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					// Ignored interrupt
				}
			}
			
			if (notFound) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			} else {
				if (reportSize) {
					response.addHeader("Content-length", Long.toString(size));
				}
				OutputStream out = response.getOutputStream();
				for (long i = 0; i < size; i++) {
					out.write('a');
				}

				response.setStatus(HttpServletResponse.SC_OK);
			}
		}

		baseRequest.setHandled(true);
	}

	public Server getServerJetty() {
		return server;
	}

}
