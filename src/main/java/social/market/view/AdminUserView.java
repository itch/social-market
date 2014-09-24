package social.market.view;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import social.market.storage.model.Role;
import social.market.storage.repository.RoleRepository;
import social.market.util.LazyGenericDataModel;
import social.market.storage.model.User;



import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import social.market.storage.repository.UserRepository;

import java.util.List;


/**
 * Created by ITCH on 30.07.2014.
 */
@Component
@Scope(value = "session")
@Lazy
public class AdminUserView {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    private User newUser = new User();
    private User selectedUser;
    private User[] selectedUsers;
    private LazyGenericDataModel users;
    private List<Role> roleList;


    @PostConstruct
    public void init() {
        System.out.println("loadUserData");
        roleList = this.roleRepository.getAllRoles();

        loadUserData();
    }

    private void loadUserData() {
        users = new LazyGenericDataModel(userRepository.findAll());
    }

    public LazyGenericDataModel getUsers() {
        return users;
    }


    public User getSelectedUser() {
        return selectedUser;
    }


    public void setSelectedUser(User selectedUser)
    {
        System.out.println(selectedUser);
        this.selectedUser = selectedUser;
    }



    public User[] getSelectedUsers() {
        return selectedUsers;
    }


    public void setSelectedUsers(User[] selectedUsers) {
        this.selectedUsers = selectedUsers;
    }


    public void doDeleteUsers() {
        for (User user : selectedUsers) {
            userRepository.delete(user);
        }

        loadUserData();
    }

    public void doUpdateUser() {
        userRepository.update(selectedUser);
    }

    public void doCreateUser() {
        userRepository.save(newUser);

        loadUserData();
    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}
