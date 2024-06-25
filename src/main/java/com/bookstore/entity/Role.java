package com.bookstore.entity;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "role",
        uniqueConstraints = {@UniqueConstraint(name = "unique_role_role_name", columnNames = "role_name")})
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;
    @Column(name = "role_name")
    private String roleName;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "roles")
    private Set<User> users;
    public Role() {
    }

    public Role(@NotNull(message = "is require") String roleName) {
        this.roleName = roleName;
    }

    public Set<User> getUsers() {
        return users;
    }
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
