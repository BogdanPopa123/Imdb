package org.example.enums;

public enum Gender {
    F,
    M,
    N;

    public static Gender fromString(String genderString) {
        for (Gender gender : Gender.values()) {
            if (gender.name().equals(genderString)) {
                return gender;
            }
        }
        return null;
    }
}
