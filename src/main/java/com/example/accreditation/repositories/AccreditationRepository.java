package com.example.accreditation.repositories;

import com.example.accreditation.models.Accreditation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccreditationRepository extends JpaRepository<Accreditation, String> {

    List<Accreditation> findByUserId(String userId);
}
