package org.esquivo.downloader.cli;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.esquivo.downloader.HCDownloader;
import org.esquivo.downloader.URLDownloader;
import org.esquivo.downloader.Downloader;
import org.esquivo.downloader.DownloaderCallback;

public class Main {
	static Options options = new Options();
	private static final int DEFAULT_MAX_THREADS = 10;
	private static final int EXEC_WAIT_TERM_SECS = 50;
	
	private Main() {

	}

	private static void usage() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("downloader [-h|-u|-t] [-o <connectiontimeout (milsecs)> [-r <read timeout (milsecs>] [-m <max threads>] <URL>", options);
		System.exit(-1);
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		int connectionTimeout = 0;
		int readTimeout = 0;
		int maxThreads = DEFAULT_MAX_THREADS;

		// crear grupo de opciones (u | h | t)
		OptionGroup group = new OptionGroup().addOption(new Option("u", "url", false, "download using URLConnection"))
		        .addOption(new Option("t", "http", false, "download using HTTPClient"))
		        .addOption(new Option("h", "help", false, "show this help"));
		group.setRequired(false);

		options.addOptionGroup(group)
			.addOption("o", "connection-timeout", true, "connection timeout (milsecs)")
		    .addOption("r", "timeout", true, "read timeout (milsecs)")
		    .addOption("m", "max-threads", true, "max concurrent threads");
		
		CommandLineParser parser = new BasicParser();
		CommandLine cmd = null;

		Downloader down = null;

		try {
			// parse the command line arguments
			cmd = parser.parse(options, args);
		} catch (ParseException exp) {
			// oops, something went wrong
			System.err.println("Parsing failed. Reason: " + exp.getMessage());
			usage();
		}

		boolean isHttpClient = false;
		@SuppressWarnings("unchecked")
        Iterator<Option> iterator = cmd.iterator();

		while (iterator.hasNext()) {
			Option op = iterator.next();

			switch (op.getId()) {
			case 'o':
				connectionTimeout = Integer.parseInt(op.getValue());
				break;
			case 'r':
				readTimeout = Integer.parseInt(op.getValue());
				break;
			case 'm':
				maxThreads = Integer.parseInt(op.getValue());
				break;
			case 'u':
				isHttpClient = false;
				break;
			case 't':
				isHttpClient = true;
				break;
			case 'h':
				usage();
				break;

			}
		}

		if (isHttpClient) {
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put(HCDownloader.CONNECTION_TIMEOUT, connectionTimeout);
			params.put(HCDownloader.READ_TIMEOUT, readTimeout);
			params.put(HCDownloader.MAX_THREADS, maxThreads);
			
			down = new HCDownloader(params);
		} else {
			down = new URLDownloader(connectionTimeout, readTimeout);
		}

		// Check if user a URL
		if (cmd.getArgList().size() > 0) {

			ExecutorService executor = Executors.newFixedThreadPool(maxThreads);

			for (String arg : cmd.getArgs()) {

				final String url = arg;
				final Downloader fDown = down;
				
				DownloaderCallback call = new DownloadCallback();

				executor.execute(new Url2File(url, fDown, call));
			}
			// This will make the executor accept no new threads
			// and finish all existing threads in the queue
			executor.shutdown();
			// Wait until all threads are finish --> change code to wait the
			// executor shutdown

			try {
				boolean b = executor.awaitTermination(EXEC_WAIT_TERM_SECS, TimeUnit.SECONDS);
				System.out.println("All done: " + b);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("Bad URL. Please type a valid URL");
			usage();
		}

	}
}