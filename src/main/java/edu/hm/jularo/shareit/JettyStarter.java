package edu.hm.jularo.shareit;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Start the application without an AppServer like tomcat.
 *
 * @author ab@cs.hm.edu
 */
//CHECKSTYLE:OFF
public class JettyStarter {

    private static final String APP_URL = "/";
    private static final int PORT = 8082;
    private static final String WEBAPP_DIR = "./src/main/webapp/";

    /**
     * main Methode zum Starten von Jetty.
     *
     * @param args - nicht benötigt
     * @throws Exception - Mögliche Exception beim Starten des Servers.
     */
    public static void main(String... args) throws Exception {
        Server jetty = new Server(PORT);
        jetty.setHandler(new WebAppContext(WEBAPP_DIR, APP_URL));
        jetty.start();
        System.out.println("Jetty listening on port " + PORT);
        jetty.join();
    }
}
