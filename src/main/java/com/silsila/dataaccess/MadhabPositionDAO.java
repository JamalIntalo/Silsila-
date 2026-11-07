package com.silsila.dataaccess;

import com.silsila.domain.MadhabPosition;
import java.util.ArrayList;

// Finds madhab positions in the database.
public class MadhabPositionDAO {

    private Database database;

    public MadhabPositionDAO(Database database) {
        this.database = database;
    }

    // Returns the positions of the madhabs on one issue.
    public ArrayList<MadhabPosition> findByMasala(int masalaId) {
        ArrayList<MadhabPosition> results = new ArrayList<MadhabPosition>();
        for (MadhabPosition p : database.positions) {
            if (p.getMasalaId() == masalaId) {
                results.add(p);
            }
        }
        return results;
    }
}