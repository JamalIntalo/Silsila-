package com.example.silsila.application;

import com.example.silsila.data.Masala;
import java.util.ArrayList;
import java.util.List;

public class MasalaService {
    
    public List<Masala> getSampleMasalas() {
        List<Masala> list = new ArrayList<>();
        list.add(new Masala("Nikah Conditions", "Hanafi", "Valid with presence of witnesses."));
        list.add(new Masala("Nikah Conditions", "Shafi'i", "Requires a wali (guardian) and witnesses."));
        return list;
    }
}