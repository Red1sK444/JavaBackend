package ru.r3d1r4ph.springdb1.dto.converter;

import com.opencsv.bean.AbstractBeanField;
import ru.r3d1r4ph.springdb1.entity.Role;

import java.util.Objects;

public class RoleConverter extends AbstractBeanField<String, Role> {
    @Override
    protected Object convert(String s) {
        var role = Role.USER;
        if (Objects.equals(s, "Администратор")) {
            role = Role.ADMIN;
        }
        return role;
    }
}
