package demotest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by simon on 2017/6/2.
 */
@Entity
public class AdminUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String password;

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRole() {
        return role;
    }

    public List<String> getRoles(){

        List<String> roleList = new ArrayList<>();
        for(Role roleresult : this.getRole()){
            roleList.add(roleresult.getName());
        }
        return roleList;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "adminuser_id")
    private List<Role> role;


    public AdminUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
