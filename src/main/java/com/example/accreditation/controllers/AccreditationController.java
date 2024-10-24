package com.example.accreditation.controllers;

import com.example.accreditation.models.Accreditation;
import com.example.accreditation.services.AccreditationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class AccreditationController {

    @Autowired
    private AccreditationService accreditationService;

    @PostMapping("/accreditation")
    public ResponseEntity<?> createAccreditation(@RequestBody Accreditation request) {
        Accreditation accreditation = accreditationService.createAccreditation(request);
        return ResponseEntity.ok(accreditation.getId());
    }

    @PutMapping("/accreditation/{accreditationId}")
    public ResponseEntity<?> updateAccreditationStatus(@PathVariable String accreditationId, @RequestBody Accreditation request) {
        accreditationService.updateAccreditationStatus(accreditationId, request);
        return ResponseEntity.ok(accreditationId);
    }

    @GetMapping("/{userId}/accreditation")
    public ResponseEntity<List<Accreditation>> getUserAccreditations(@PathVariable String userId) {
        return ResponseEntity.ok(accreditationService.getAccreditationsByUserId(userId));
    }
}
