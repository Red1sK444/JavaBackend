package ru.r3d1r4ph.springdb1.dto.converter;

import com.opencsv.bean.AbstractBeanField;
import ru.r3d1r4ph.springdb1.entity.Priority;
import ru.r3d1r4ph.springdb1.entity.Role;

public class PriorityConverter extends AbstractBeanField<String, Role> {
    @Override
    protected Object convert(String s) {
        var priority = Priority.MEDIUM;
        switch (s) {
            case "Высокий":
                priority = Priority.HIGH;
                break;
            case "Низкий":
                priority = Priority.LOW;
                break;
        }
        return priority;
    }
}
