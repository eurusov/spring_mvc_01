package system.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import system.filter.AdminFilter;
import system.filter.UserFilter;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{HibernateConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter", characterEncodingFilter);
        encodingFilter.addMappingForUrlPatterns(null, true, "/*"); // TODO: Почему здесь не работает "/**"

        FilterRegistration.Dynamic userFilter = servletContext.addFilter("userFilter", new UserFilter());
        userFilter.addMappingForUrlPatterns(null, true, "/edit", "/info");

        FilterRegistration.Dynamic adminFilter = servletContext.addFilter("adminFilter", new AdminFilter());
        adminFilter.addMappingForUrlPatterns(null, true, "/list", "/delete");

//        FilterRegistration.Dynamic authFilter = servletContext.addFilter("authFilter", new AuthFilter());
//        authFilter.addMappingForUrlPatterns(null, false, "/list", "/delete", "/edit", "/info");

        super.onStartup(servletContext);
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/", "/login", "/list", "/new", "/edit", "/info", "/delete"}; // Здесь не работает "/*" и "/**"
    }

//    @Override
//    protected Filter[] getServletFilters() {
//        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//        characterEncodingFilter.setEncoding("UTF-8");
//        characterEncodingFilter.setForceEncoding(true);
//        return new Filter[]{
//                characterEncodingFilter,
//                new AdminFilter(),
//                new UserFilter(),
//                new PassThrowFilter()
//        };
//    }
}
