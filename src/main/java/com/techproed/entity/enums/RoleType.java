package com.techproed.entity.enums;

import lombok.Getter;

@Getter
public enum RoleType {

    ADMIN("Admin"),
    TEACHER("Teacher"),
    STUDENT("Student"),
    MANAGER("Manager"),
    ASSISTANT_MANAGER("ViceDean");


    // 1-    isim ver mek için proporties ekledik.
    public final String name;

    RoleType(String name) {
        this.name = name;
    }

}
