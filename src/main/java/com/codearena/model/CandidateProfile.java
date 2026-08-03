package com.codearena.model;

import com.codearena.enums.Gender;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Stores personal profile details for a candidate.
 */

public class CandidateProfile {

    private String fullName;
    private String mobileNumber;
    private Gender gender;
    private LocalDate dateOfBirth;

    public CandidateProfile(String fullName, String mobileNumber, Gender gender, LocalDate dateOfBirth) {
        this.fullName = Objects.requireNonNull(fullName, "fullName is required");
        this.mobileNumber = Objects.requireNonNull(mobileNumber, "mobileNumber is required");
        this.gender = Objects.requireNonNull(gender, "gender is required");
        this.dateOfBirth = Objects.requireNonNull(dateOfBirth, "dateOfBirth is required");
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = Objects.requireNonNull(fullName, "fullName is required");
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = Objects.requireNonNull(mobileNumber, "mobileNumber is required");
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = Objects.requireNonNull(gender, "gender is required");
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = Objects.requireNonNull(dateOfBirth, "dateOfBirth is required");
    }


}
