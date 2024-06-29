package com.medilabo;

import com.medilabo.persistence.Note;
import com.medilabo.persistence.NoteRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RunnerConfig implements CommandLineRunner {

    private final NoteRepo noteRepo;

    @Override
    public void run(String... args) {
        Note notePatient1 = Note.builder()
                .patId("1")
                .patient("TestNone")
                .notes(List.of("Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé"))
                .build();

        Note notePatient2 = Note.builder()
                .patId("2")
                .patient("TestBorderline")
                .notes(List.of("Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement",
                        "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois Il remarque également que son audition continue d'être anormale"))
                .build();

        Note notePatient3 = Note.builder()
                .patId("3")
                .patient("TestInDanger")
                .notes(List.of("Le patient déclare qu'il fume depuis peu",
                        "Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière Il se plaint également de crises d’apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé"))
                .build();

        Note notePatient4 = Note.builder()
                .patId("4")
                .patient("TestEarlyOnset")
                .notes(List.of("Le patient déclare qu'il lui est devenu difficile de monter les escaliers Il se plaint également d’être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments",
                        "Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps",
                        "Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé",
                        "Taille, Poids, Cholestérol, Vertige et Réaction"))
                .build();

        List<Note> notes = List.of(notePatient1, notePatient2, notePatient3, notePatient4);
        noteRepo.saveAll(notes);
    }
}
