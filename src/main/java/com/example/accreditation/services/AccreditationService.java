package com.example.accreditation.services;

import com.example.accreditation.models.Accreditation;
import com.example.accreditation.repositories.AccreditationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccreditationService {

    @Autowired
    private AccreditationRepository accreditationRepository;

    public Accreditation createAccreditation(Accreditation accreditation) {
        return accreditationRepository.save(accreditation);
    }

    public void updateAccreditationStatus(String accreditationId, Accreditation updatedAccreditation) {
        Accreditation accreditation = accreditationRepository.findById(accreditationId)
                .orElseThrow(() -> new RuntimeException("Accreditation not found"));

        accreditation.setStatus(updatedAccreditation.getStatus());
        accreditationRepository.save(accreditation);
    }

    public List<Accreditation> getAccreditationsByUserId(String userId) {
        return accreditationRepository.findByUserId(userId);
    }
}
