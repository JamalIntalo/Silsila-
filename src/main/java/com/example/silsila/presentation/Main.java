package com.example.silsila.presentation;

import com.example.silsila.application.MasalaService;
import com.example.silsila.data.Masala;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Silsila System...");
        
        MasalaService service = new MasalaService();
        List<Masala> masalas = service.getSampleMasalas();
        
        for (Masala m : masalas) {
            System.out.println(m);
        }
    }
}