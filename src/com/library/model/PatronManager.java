package com.library.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

public class PatronManager {
    private final Map<String, Patron> patrons;
    private static final Logger logger = Logger.getLogger(PatronManager.class.getName());


    public PatronManager() {
        this.patrons = new HashMap<>();
    }

    public void addPatron(Patron patron) {
        if (patrons.containsKey(patron.getPatronId())) {
            logger.warning("A patron with ID " + patron.getPatronId() + " already exists.");
            throw new IllegalArgumentException("A patron with ID " + patron.getPatronId() + " already exists.");
        }
        patrons.put(patron.getPatronId(), patron);
        logger.info("Patron with ID " + patron.getPatronId() + " has been added.");
    }

    public void removePatron(String patronId) {
        if (!patrons.containsKey(patronId)) {
            logger.warning("No patron with ID " + patronId + " found.");
            throw new IllegalArgumentException("No patron with ID " + patronId + " found.");
        }
        patrons.remove(patronId);
        logger.info("Patron with ID " + patronId + " has been removed.");
    }

    public void updatePatron(String patronId, String name, String email) {
        if (!patrons.containsKey(patronId)) {
            logger.warning("No patron with ID " + patronId + " found.");
            throw new IllegalArgumentException("No patron with ID " + patronId + " found.");
        }
        Patron patron = patrons.get(patronId);
        patron.setName(name);
        patron.setEmail(email);
        logger.info("Patron with ID " + patronId + " has been updated.");
    }

    public Optional<Patron> findPatronById(String patronId) {
        Patron patron = patrons.get(patronId); // returns null if not in the map
        return Optional.ofNullable(patron);    // wraps it: present if non-null, empty if null
    }
}
