package com.silsila.presentation;

import com.silsila.application.MasalaService;
import com.silsila.dataaccess.AyahDAO;
import com.silsila.dataaccess.Database;
import com.silsila.dataaccess.HadithDAO;
import com.silsila.dataaccess.MadhabPositionDAO;
import com.silsila.dataaccess.MasalaDAO;
import com.silsila.dataaccess.QanunDAO;
import com.silsila.dataaccess.TafsirDAO;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

// Main is where the program starts.
// It builds the layers, connects them, and starts the web server.
public class Main {

    // "throws IOException" because starting a server can fail (for example if the port is busy).
    public static void main(String[] args) throws IOException {
        // Build the layers from the bottom up and connect them.
        Database database = new Database();
        MasalaDAO masalaDAO = new MasalaDAO(database);
        AyahDAO ayahDAO = new AyahDAO(database);
        TafsirDAO tafsirDAO = new TafsirDAO(database);
        HadithDAO hadithDAO = new HadithDAO(database);
        QanunDAO qanunDAO = new QanunDAO(database);
        MadhabPositionDAO positionDAO = new MadhabPositionDAO(database);
        MasalaService masalaService = new MasalaService(masalaDAO, ayahDAO, tafsirDAO,
                hadithDAO, qanunDAO, positionDAO);

        // Create the web server on port 8080.
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Tell the server which controller answers which address.
        // The longer address "/masala" is matched before the general "/".
        server.createContext("/", new SearchController(masalaService));
        server.createContext("/masala", new MasalaController(masalaService));

        server.start();
        System.out.println("Silsila is running. Open http://localhost:8080 in your browser.");
        System.out.println("To stop it, click the red stop button in the terminal.");
    }
}