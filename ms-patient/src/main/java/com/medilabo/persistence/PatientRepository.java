package com.medilabo.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "patient", path = "patient")
public interface PatientRepository extends PagingAndSortingRepository<Patient, Long>, CrudRepository<Patient, Long> {

    @Query("SELECT p FROM Patient p WHERE p.firstName LIKE %:searchTerm% OR p.lastName LIKE %:searchTerm% OR p.phoneNumber LIKE %:searchTerm%")
    List<Patient> byFirstNameOrLastNameOrPhoneNumber(String searchTerm);
}
