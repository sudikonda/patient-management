package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();

        return patients.stream()
                .map(PatientMapper::toPatientResponseDTO)
                .toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Patient with email " + patientRequestDTO.getEmail() + " already exists");
        }
        Patient patient = PatientMapper.toPatientModel(patientRequestDTO);
        Patient savedPatient = patientRepository.save(patient);
        return PatientMapper.toPatientResponseDTO(savedPatient);
    }

    public PatientResponseDTO updatePatient(String id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(UUID.fromString(id)).orElseThrow(() -> new PatientNotFoundException("Patient with id " + id + " not found"));

        if (patientRequestDTO.getEmail() != null && patientRepository.existsByEmailAndIdNot((patientRequestDTO.getEmail()), UUID.fromString(id))) {
            throw new EmailAlreadyExistsException("Patient with email " + patientRequestDTO.getEmail() + " already exists");
        }

        Patient updatedPatient = PatientMapper.toUpdatedPatient(patientRequestDTO, patient);
        Patient savePatient = patientRepository.save(updatedPatient);

        return PatientMapper.toPatientResponseDTO(savePatient);
    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }

}
