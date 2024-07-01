package com.medilabo.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Note {

    private String patId;

    private String patient;

    private List<String> notes;
}
