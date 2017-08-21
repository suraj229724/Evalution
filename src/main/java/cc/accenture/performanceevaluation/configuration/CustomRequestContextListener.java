/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.configuration;

import javax.servlet.annotation.WebListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

/**
 *
 * @author Suraj
 */
@Configuration
@WebListener
public class CustomRequestContextListener extends RequestContextListener{
    
}
