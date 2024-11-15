

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@ComponentScan("com.gildedrose")
@SpringBootApplication()
public class GildedMain extends SpringBootServletInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(GildedMain.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);}


    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            new SpringApplicationBuilder(GildedMain.class).web(WebApplicationType.SERVLET).run( args);
        } catch (Exception e){
            LOGGER.warn("Exception e in Starting Spring",e);
            Throwable cause = e.getCause();
            while(cause !=null){
                LOGGER.warn("Exception caused by Starting Spring",cause);
                cause = cause.getCause();
            }
        }
    }


    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(GildedMain.class);
    }


}


