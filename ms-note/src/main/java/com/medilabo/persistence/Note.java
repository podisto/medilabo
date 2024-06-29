package com.medilabo.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("notes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    @Id
    private String patId;
    private String patient;
    private List<String> notes;
}
