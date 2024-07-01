package com.medilabo.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PatientDetails {

    private long id;

    private String lastName;

    private String firstName;

    private LocalDate birthDate;

    private String gender;

    private String address;

    private String phoneNumber;

    private List<String> notes;
}
