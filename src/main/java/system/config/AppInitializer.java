package system.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import system.controller.AdminFilter;
import system.controller.PassThrowFilter;
import system.controller.UserFilter;

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
        encodingFilter.addMappingForUrlPatterns(null, false, "/**");

        FilterRegistration.Dynamic adminFilter = servletContext.addFilter("adminFilter", new AdminFilter());
        adminFilter.addMappingForUrlPatterns(null, false, "/list");
        adminFilter.addMappingForUrlPatterns(null, false, "/delete");

        FilterRegistration.Dynamic userFilter = servletContext.addFilter("userFilter", new UserFilter());
        userFilter.addMappingForUrlPatterns(null, false, "/edit");
        userFilter.addMappingForUrlPatterns(null, false, "/info");

        FilterRegistration.Dynamic anonFilter = servletContext.addFilter("anonFilter", new PassThrowFilter());
        anonFilter.addMappingForUrlPatterns(null, false, "/list");
        anonFilter.addMappingForUrlPatterns(null, false, "/delete");
        anonFilter.addMappingForUrlPatterns(null, false, "/edit");
        anonFilter.addMappingForUrlPatterns(null, false, "/info");

        super.onStartup(servletContext);
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/", "/login"}; // TODO: Разобраться зачем здесь нужен /login, если он и так есть в аннотоции контроллера
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
