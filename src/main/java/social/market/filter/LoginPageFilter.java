package social.market.filter;

import org.springframework.context.annotation.Scope;
import social.market.controller.LoginController;
import social.market.storage.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LoginPageFilter implements Filter {
    private static User loggedUser = null;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        LoginController loginController = (LoginController) request.getSession().getAttribute("loginController");
        if (loginController == null || !loginController.isLoggedIn()) {
            if (requestURI.contains("/admin/") || requestURI.contains("/cp/")) {
                response.sendRedirect(request.getContextPath() + "/index.html");
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {

        loggedUser = loginController.getUser();
            if (requestURI.equals("/index.html") || requestURI.equals("/") && loggedUser.getRole().getRolename().equals("Administrators")) {
                response.sendRedirect(request.getContextPath() + "/admin/");
            }

            if (requestURI.equals("/index.html") || requestURI.equals("/") && loggedUser.getRole().getRolename().equals("Users")) {
                response.sendRedirect(request.getContextPath() + "/cp/");
            }

            if (requestURI.contains("/admin/") && !loggedUser.getRole().getRolename().equals("Administrators")) {
                response.sendRedirect(request.getContextPath() + "/index.html");
            }

            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User loggedUser) {
        LoginPageFilter.loggedUser = loggedUser;
    }
}