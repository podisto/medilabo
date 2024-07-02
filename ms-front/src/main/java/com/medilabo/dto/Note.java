package com.medilabo.dto;

import lombok.Data;

import java.util.List;

@Data
public class Note {

    private String patId;

    private String patient;

    private List<String> notes;
}
