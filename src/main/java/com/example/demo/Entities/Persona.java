package com.example.demo.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Persona {
    @Id
    private String id;

    private boolean isMutant;

    public Persona() {}

    public Persona(String id, boolean isMutant){
        this.id = id;
        this.isMutant = isMutant;
    }
}
