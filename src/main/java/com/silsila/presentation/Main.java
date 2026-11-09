package com.silsila.presentation;

import com.silsila.application.AuthService;
import com.silsila.application.LinkService;
import com.silsila.application.MasalaService;
import com.silsila.dataaccess.AyahDAO;
import com.silsila.dataaccess.Database;
import com.silsila.dataaccess.HadithDAO;
import com.silsila.dataaccess.LinkDAO;
import com.silsila.dataaccess.MadhabPositionDAO;
import com.silsila.dataaccess.MasalaDAO;
import com.silsila.dataaccess.QanunDAO;
import com.silsila.dataaccess.RemoteLoader;
import com.silsila.dataaccess.TafsirDAO;
import com.silsila.dataaccess.UserDAO;
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

        // Load the texts of verses, tafsir and hadith from the websites (or from the cache folder).
        RemoteLoader loader = new RemoteLoader(database);
        loader.loadAll();

        MasalaDAO masalaDAO = new MasalaDAO(database);
        AyahDAO ayahDAO = new AyahDAO(database);
        TafsirDAO tafsirDAO = new TafsirDAO(database);
        HadithDAO hadithDAO = new HadithDAO(database);
        QanunDAO qanunDAO = new QanunDAO(database);
        MadhabPositionDAO positionDAO = new MadhabPositionDAO(database);
        LinkDAO linkDAO = new LinkDAO(database);
        UserDAO userDAO = new UserDAO(database);

        MasalaService masalaService = new MasalaService(masalaDAO, ayahDAO, tafsirDAO,
                hadithDAO, qanunDAO, positionDAO);
        LinkService linkService = new LinkService(linkDAO, masalaDAO, ayahDAO, hadithDAO, qanunDAO);
        AuthService authService = new AuthService(userDAO);

        // Two DEMO accounts so that you can try the system.
        // (A real website would never keep passwords in the code. Change or remove these before any real use.)
        authService.register("contributor1", "contributor123", "CONTRIBUTOR");
        authService.register("reviewer1", "reviewer123", "REVIEWER");

        // Create the web server on port 8080.
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Tell the server which controller answers which address.
        // The longer address is matched before the general "/".
        server.createContext("/", new SearchController(masalaService));
        server.createContext("/masala", new MasalaController(masalaService));
        server.createContext("/login", new AuthController(authService));
        server.createContext("/logout", new AuthController(authService));
        server.createContext("/propose", new ProposeController(linkService));
        server.createContext("/review", new ReviewController(linkService));

        server.start();
        System.out.println("Silsila is running. Open http://localhost:8080 in your browser.");
        System.out.println("To stop it, click the red stop button in the terminal.");
    }
}