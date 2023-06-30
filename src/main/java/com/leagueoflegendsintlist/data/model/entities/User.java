package com.leagueoflegendsintlist.data.model.entities;


import com.leagueoflegendsintlist.data.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
@ToString
public class User implements UserDetails {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long usersId;
    private String email;
    private String name;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String email, String name, String password,Role role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    @ManyToMany(mappedBy = "users")
    private List<Criminal> criminals = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

