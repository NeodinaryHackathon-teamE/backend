package com.example.neo_backend.global.common.enums;

import com.example.neo_backend.global.common.exception.GeneralException;
import com.example.neo_backend.global.common.status.ErrorStatus;

public enum Category {
    NATURE("자연훼손"),
    FACILITY("시설물파손"),
    TRASH("쓰레기투기"),
    ROAD("도로안전");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Category fromKorean(String korean) {
        for (Category category : Category.values()) {
            if (category.displayName.equals(korean)) {
                return category;
            }
        }
        throw new GeneralException(ErrorStatus.INVALID_CATEGORY);
    }
}
