package cc.accenture.performanceevaluation.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{ApplicationConfiguration.class, SecurityConfiguration.class, CustomRequestContextListener.class};
    }
    
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{DispatcherConfiguration.class};
    }
    
    @Override
    protected String[] getServletMappings() {
        return new String[]{"*.htm"};
    }
}
