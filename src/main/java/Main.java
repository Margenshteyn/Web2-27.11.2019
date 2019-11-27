import model.User;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import service.UserService;
import servlet.ApiServlet;
import servlet.LoginServlet;
import servlet.RegistrationServlet;

public class Main {

    public static void main(String[] args) throws Exception{
        ApiServlet apiServlet = new ApiServlet();
        LoginServlet loginServlet = new LoginServlet();
        UserService userService = UserService.getInstance();
//        userService.addUser(new User("admin", "admin"));

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(apiServlet), "/api/*");
        context.addServlet(new ServletHolder(new RegistrationServlet(userService)), "/register");
        context.addServlet(new ServletHolder(loginServlet), "/login");

       /* ResourceHandler resource_handler = new ResourceHandler();   // обращаться к статическим ресурсам
        resource_handler.setResourceBase("templates");            // говорю, что будет брать файлы из директории templates

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});  // сперва обрабатывается запрос к статике, затем запрос к сервлетам*/

        Server server = new Server(8080);
        server.setHandler(context);
//        server.setHandler(handlers);

        server.start();
        server.join();
    }
}
