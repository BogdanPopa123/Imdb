package org.example.enums;

public enum AccountType {
    REGULAR,
    CONTRIBUTOR,
    ADMIN;

    public static AccountType fromString(String typeString) {
        for (AccountType type : AccountType.values()) {
            if (type.name().equals(typeString)) {
                return type;
            }
        }
        return null;
    }
}
