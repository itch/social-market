package social.market.controller;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import social.market.storage.model.User;
import social.market.storage.repository.UserRepository;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Login Controller class allows only authenticated users to log in to the web
 * application.
 *
 * @author Emre Simtay <emre@simtay.com>
 */

@Component
@Scope("session")
public class LoginController implements Serializable {

    User user = null;
    private String username;
    private String password;
    private boolean loggedIn;
    private String userRole;

    @Autowired
    UserRepository userRepository;

    /**
     * Creates a new instance of LoginController
     */
    public LoginController() {
    }


    public void login(ActionEvent event) throws IOException {
        RequestContext context = RequestContext.getCurrentInstance();

        user = userRepository.findByLoginAndPass(username, password);

        FacesMessage message = null;
        boolean loggedIn = false;

        if (user != null) {
            loggedIn = true;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);

            if (user.getRole().getRolename().equals("Administrators")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/");
            } else if(user.getRole().getRolename().equals("Users")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/cp/");
            }
        } else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("loggedIn", loggedIn);
        this.loggedIn = loggedIn;
    }


    public void logout() throws IOException {

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        if (session != null) {
            session.invalidate();
        }
        externalContext.redirect(externalContext.getRequestContextPath() + "/");

    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public User getUser() {
        return user;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //  Getters and Setters

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
