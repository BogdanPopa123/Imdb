package org.example;

public class Rating {

    public Rating(String username, Integer grade, String comment) {
        this.username = username;
        this.grade = grade;
        this.comment = comment;
    }

    private String username;
    private Integer grade;
    private String comment;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        if (1 <= grade && grade <= 10) {
            this.grade = grade;
        }
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "username='" + username + '\'' +
                ", grade=" + grade +
                ", comment='" + comment + '\'' +
                '}';
    }
}
