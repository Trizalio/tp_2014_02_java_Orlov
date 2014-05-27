package main;

import messageSystem.MessageSystem;
import services.AccountService;
import frontend.Frontend;
import org.eclipse.jetty.rewrite.handler.RedirectRegexRule;
import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.Servlet;

public class Main {
    private static final String STATIC_CONTAINER = "static";
    private static final int PORT = 8090;

    public static void main(String[] args) throws Exception {

        MessageSystem messageSystem = new MessageSystem();
        new Thread(new AccountService(messageSystem)).start();
        new Thread(new AccountService(messageSystem)).start();

        AccountService.initBase();

        Frontend frontend = new Frontend(messageSystem);
        new Thread(frontend).start();

        Server server = new Server(PORT);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(frontend), "/*");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(STATIC_CONTAINER);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, context});
        server.setHandler(handlers);

        server.start();
        server.join();
    }
}
