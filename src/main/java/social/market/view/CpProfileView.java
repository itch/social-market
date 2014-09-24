package social.market.view;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import social.market.filter.LoginPageFilter;
import social.market.storage.model.Role;
import social.market.storage.model.User;
import social.market.storage.repository.RoleRepository;
import social.market.storage.repository.UserRepository;
import social.market.util.LazyGenericDataModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;


@Component
@Lazy
public class CpProfileView {

    @Autowired
    private UserRepository userRepository;


    public void doUpdateUser() {
        userRepository.update(LoginPageFilter.getLoggedUser());
    }

    public User getUser() {
        return LoginPageFilter.getLoggedUser();
    }

}
