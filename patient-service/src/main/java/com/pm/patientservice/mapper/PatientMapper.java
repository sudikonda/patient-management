package com.pm.patientservice.mapper;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.model.Patient;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class PatientMapper {

    public static PatientResponseDTO toPatientResponseDTO(Patient patient) {
        PatientResponseDTO patientResponseDTO = new PatientResponseDTO();
        patientResponseDTO.setId(patient.getId().toString());
        patientResponseDTO.setName(patient.getName());
        patientResponseDTO.setEmail(patient.getEmail());
        patientResponseDTO.setAddress(patient.getAddress());
        patientResponseDTO.setDateOfBirth(patient.getDateOfBirth().toString());
        return patientResponseDTO;
    }

    public static Patient toPatientModel(PatientRequestDTO patientRequestDTO) {
        Patient patient = new Patient();
        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        patient.setDateOfRegistration(LocalDate.parse(patientRequestDTO.getDateOfRegistration()));
        return patient;
    }

    public static Patient toUpdatedPatient(PatientRequestDTO patientRequestDTO, Patient patient) {
        patient.setId(patient.getId());
        patient.setName(patientRequestDTO.getName() != null ? patientRequestDTO.getName() : patient.getName());
        patient.setEmail(patientRequestDTO.getEmail() != null ? patientRequestDTO.getEmail() : patient.getEmail());
        patient.setAddress(patientRequestDTO.getAddress() != null ? patientRequestDTO.getAddress() : patient.getAddress());
        patient.setDateOfBirth(patientRequestDTO.getDateOfBirth() != null ? LocalDate.parse(patientRequestDTO.getDateOfBirth()) : patient.getDateOfBirth());
        return patient;
    }
}
