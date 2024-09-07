package com.portfolioKaleb.restaurante.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Role {
    private Long roleId;
    private String name;

    public enum Values {
        CLIENT(1, "client"),
        ADMIN(2, "admin");

        long roleId;
        String roleName;

        Values (long roleID, String roleName) {
            this.roleId = roleID;
            this.roleName = roleName;
        }

        public static Role getRoleByName(String roleName){
            for (Values value: Values.values()) {
                if (value.roleName.equalsIgnoreCase(roleName)) {
                    return new Role(value.roleId, value.roleName);
                }
            }
            return null;
        }
    }
}
