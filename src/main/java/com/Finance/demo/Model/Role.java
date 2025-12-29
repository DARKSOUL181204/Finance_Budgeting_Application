package com.Finance.demo.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.HashSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true, nullable = false)
    private String name;

    public  Role(String name){
        this.name = name;
    }
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new HashSet<>();


}
