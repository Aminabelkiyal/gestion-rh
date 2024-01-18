package com.example.demo.controller;

import com.example.demo.model.Conge;
import com.example.demo.repository.CongeRepository;
import com.example.demo.repository.PersonneRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repository.CongeRepository;
import com.example.demo.model.Personne;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CongeController {

    @Autowired
    private CongeRepository congeRepository;
    
    @Autowired
    private PersonneRepository personneRepository;

    @GetMapping("/conges")
    public List<Conge> getAllConges() {
        return congeRepository.findAll();
    }

    @GetMapping("/conges/{id}")
    public ResponseEntity<?> getCongeById(@PathVariable Long id) {
        Conge conge = congeRepository.findById(id).orElse(null);
        if (conge != null) {
            String employeeNom = conge.getEmploye().getNom();
            conge.getEmploye().setNom(employeeNom);
            return ResponseEntity.ok(conge);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"Conge not found\"}");
        }
    }

    @GetMapping("/conges/employe/{employeeId}")
    public ResponseEntity<List<Conge>> getListByEmploye(@PathVariable Personne employeeId) {
        try {
            List<Conge> conges = congeRepository.findByEmploye(employeeId.getId());
            if (conges.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
            } else {
                return ResponseEntity.ok(conges);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }

    @GetMapping("/Lastconges/employee/{employeeId}")
    public ResponseEntity<List<Map<String, Object>>> getLastCongeByEmployee(@PathVariable String empName) {
        try {
            List<Conge> conges = congeRepository.findByEmployeAndState(empName, "ACCEPTED");

            if (conges.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
            } else {
                List<Map<String, Object>> congeDetails = new ArrayList<>();
                for (Conge conge : conges) {
                    Map<String, Object> congeMap = new HashMap<>();
                    congeMap.put("dateDebut", conge.getDateDebut());
                    congeMap.put("nbrJours", conge.getNbrJours());
                    congeMap.put("state", conge.getState());
                    congeDetails.add(congeMap);
                }

                return ResponseEntity.ok(congeDetails);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }

    
    /*
    @GetMapping("/conges/{empName}/{state}")
    public ResponseEntity<List<Map<String, Object>>> getByStatusAndEmploye(@PathVariable String empName, String state) {
        try {
            List<Conge> conges = congeRepository.findByEmployeAndState(empName, state);

            if (conges.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
            } else {
                List<Map<String, Object>> congeDetails = new ArrayList<>();
                for (Conge conge : conges) {
                    Map<String, Object> congeMap = new HashMap<>();
                    congeMap.put("dateDebut", conge.getDateDebut());
                    congeMap.put("nbrJours", conge.getNbrJours());
                    congeMap.put("state", conge.getState());
                    congeDetails.add(congeMap);
                }

                return ResponseEntity.ok(congeDetails);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }
*/
    @GetMapping("/conges/status/{status}")
    public ResponseEntity<List<Conge>> getCongeByStatus(@PathVariable String status) {
        try {
            List<Conge> conges = congeRepository.findByState(status);

            if (conges.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
            } else {
                return ResponseEntity.ok(conges);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }

  
    @GetMapping("/conges/employee/{employeeId}")
    public ResponseEntity<List<Conge>> getCongeByEmployee(@PathVariable Long employeeId) {
        try {
            List<Conge> conges = congeRepository.findByEmploye(employeeId);

            if (conges.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
            } else {
                return ResponseEntity.ok(conges);
            }
        } catch (Exception e) {
            // Handle the exception properly, e.g., log it
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }


    @PostMapping("/conges")
    public ResponseEntity<?> createConge(@RequestBody Conge newConge) {
        try {
            System.out.println("Creating Conge: " + newConge.toString());

            congeRepository.save(newConge);
            return ResponseEntity.ok("Conge created successfully");
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();

            String errorMessage = "Creation failed. " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }


    @PutMapping("/conges/{id}")
    public ResponseEntity<?> updateConge(@PathVariable Long id, @RequestBody Conge updatedConge) {
        try {
            Conge existingConge = congeRepository.findById(id).orElse(null);
            if (existingConge != null) {
                existingConge.setType(updatedConge.getType());
                existingConge.setNbrJours(updatedConge.getNbrJours());
                existingConge.setState(updatedConge.getState());
                // Update other fields as needed

                congeRepository.save(existingConge);
                return ResponseEntity.ok("Conge updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conge not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update failed");
        }
    }

    @DeleteMapping("/conges/{id}")
    public ResponseEntity<?> deleteConge(@PathVariable Long id) {
        try {
            if (congeRepository.existsById(id)) {
                congeRepository.deleteById(id);
                return ResponseEntity.ok("Conge deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conge not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deletion failed");
        }
    }
    /*
    @PutMapping("/conges/{id}/accept")
    public ResponseEntity<?> acceptConge(@PathVariable Long id) {
        try {
            Conge existingConge = congeRepository.findById(id).orElse(null);

            if (existingConge != null) {
                Personne employee = personneRepository.findById(existingConge.getEmploye()).orElse(null);
                if (employee != null && employee.getCongeDays() >= existingConge.getNbrJours().intValue()) {
                    // Update the state to "Accepted"
                    existingConge.setState("Accepted");

                    // Decrease the available CongeDays
                    employee.setCongeDays(employee.getCongeDays() - existingConge.getNbrJours().intValue());
                    personneRepository.save(employee);

                    congeRepository.save(existingConge);
                    return ResponseEntity.ok("Conge state updated to 'Accepted'");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not enough available CongeDays");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conge not found");
            }
        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Update state failed. Error: " + e.getMessage());
        }
    }
*/
    @PutMapping("/conges/{id}/accept")
    public ResponseEntity<?> acceptConge(@PathVariable Long id) {
        try {
            Conge existingConge = congeRepository.findById(id).orElse(null);

            if (existingConge != null) {
                Personne employee = personneRepository.findById(existingConge.getEmploye().getId()).orElse(null);
                if (employee != null ) {
                   
                    String congeType = existingConge.getType();
             
                    switch (congeType) {
                    case "Annual":
                        employee.setCongeDays(employee.getCongeDays() - 22);
                        existingConge.setState("Accepted");
                        break;
                    case "Exceptional":
                        employee.setCongeDays(employee.getCongeDays() - 10);
                        existingConge.setState("Accepted");
                        break;
                    case "Sick":
                     
                        employee.setCongeDays(employee.getCongeDays() - existingConge.getNbrJours());
                        existingConge.setState("Accepted");
                        break;
                    case "Maternity":
                     
                        employee.setCongeDays(employee.getCongeDays() - existingConge.getNbrJours());
                        existingConge.setState("Accepted");
                        break;
                    case "Paternity":
                   
                        employee.setCongeDays(employee.getCongeDays() - existingConge.getNbrJours());
                        existingConge.setState("Accepted");
                        break;
                    case "None":

                        employee.setCongeDays(employee.getCongeDays() - existingConge.getNbrJours());
                        existingConge.setState("Accepted");
                        break;
                    default:
                      
                        employee.setCongeDays(employee.getCongeDays() - existingConge.getNbrJours());
                        existingConge.setState("Accepted");
                        break;
                }


                    personneRepository.save(employee);
                    congeRepository.save(existingConge);

                    return ResponseEntity.ok("Conge state updated to 'Accepted'");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not enough available CongeDays");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conge not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Update state failed. Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/conges/deleteALL")
    public ResponseEntity<?> deleteAllConges() {
        try {
            congeRepository.deleteAll();
            return ResponseEntity.ok("All Conges deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deletion failed");
        }
    }
    
    @PutMapping("/conges/{id}/reject")
    public ResponseEntity<?> rejectConge(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        try {
            String comment = requestBody.get("comment");
            
            Conge existingConge = congeRepository.findById(id).orElse(null);
            if (existingConge != null) {

                existingConge.setState("Rejected");
                existingConge.setComment(comment);

                congeRepository.save(existingConge);
                return ResponseEntity.ok("Conge state updated to 'Rejected' with comment");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conge not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update state failed");
        }
    }


    @PutMapping("/conges/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        try {
          
            
            Conge existingConge = congeRepository.findById(id).orElse(null);
            if (existingConge != null) {

                existingConge.setState("Cancelled");
            
                congeRepository.save(existingConge);
                return ResponseEntity.ok("Conge state updated to 'Cancelled' with comment");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conge not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update state failed");
        }
    }


    @GetMapping("/total-count/{state}")
    public ResponseEntity<Long> getTotalCountByState(@PathVariable String state) {
        long totalCount = congeRepository.countByState(state.toUpperCase());
        return ResponseEntity.ok(totalCount);
    }


}
