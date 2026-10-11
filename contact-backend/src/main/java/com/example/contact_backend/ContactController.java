package com.example.contact_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "*")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    // GET all contacts
    @GetMapping
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    // POST create new contact
    @PostMapping
    public Contact createContact(@RequestBody Contact contact) {
        return contactRepository.save(contact);
    }

    // GET contact by ID
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        return contactRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT update contact
    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @RequestBody Contact contactDetails) {
        return contactRepository.findById(id)
            .map(contact -> {
                contact.setName(contactDetails.getName());
                contact.setEmail(contactDetails.getEmail());
                contact.setPhone(contactDetails.getPhone());
                Contact updatedContact = contactRepository.save(contact);
                return ResponseEntity.ok(updatedContact);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    // DELETE contact
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}