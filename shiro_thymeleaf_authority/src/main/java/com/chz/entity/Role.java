package com.chz.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class Role {
    private Integer rId;
    private String role;
    private Set<Perm> perms;
}
