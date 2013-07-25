package org.esquivo.downloader;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public class ServerTestBase {
    protected static TestServer server;
    protected static String serverUrl;
    protected static int listenPort;

    @BeforeClass
    public static void startServer() throws Exception {
        server = new TestServer(0);
        server.getServerJetty().start();
        listenPort = server.getServerJetty().getConnectors()[0].getLocalPort();
        serverUrl = "http://localhost:" + listenPort;
    }

    @AfterClass
    public static void stopServer() throws Exception {
        server.getServerJetty().stop();
    }
}
