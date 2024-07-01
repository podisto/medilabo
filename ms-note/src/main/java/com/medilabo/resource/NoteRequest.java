package com.medilabo.resource;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NoteRequest {
    private String patId;
    private String patient;
    private List<String> notes;
}
