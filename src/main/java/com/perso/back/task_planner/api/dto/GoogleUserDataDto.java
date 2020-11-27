package com.perso.back.task_planner.api.dto;


import java.util.Objects;

public class GoogleUserDataDto {
    public String email;
    public boolean emailVerified;
    public String name;
    public String pictureUrl;
    public String locale;
    public String familyName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoogleUserDataDto that = (GoogleUserDataDto) o;
        return emailVerified == that.emailVerified &&
                Objects.equals(email, that.email) &&
                Objects.equals(name, that.name) &&
                Objects.equals(pictureUrl, that.pictureUrl) &&
                Objects.equals(locale, that.locale) &&
                Objects.equals(familyName, that.familyName) &&
                Objects.equals(givenName, that.givenName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, emailVerified, name, pictureUrl, locale, familyName, givenName);
    }

    public String givenName;

    @Override
    public String toString() {
        return "GoogleUserDataDto{" +
                "email='" + email + '\'' +
                ", emailVerified=" + emailVerified +
                ", name='" + name + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", locale='" + locale + '\'' +
                ", familyName='" + familyName + '\'' +
                ", givenName='" + givenName + '\'' +
                '}';
    }
}
