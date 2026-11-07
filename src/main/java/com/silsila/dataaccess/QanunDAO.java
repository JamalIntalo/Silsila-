package com.silsila.dataaccess;

import com.silsila.domain.Link;
import com.silsila.domain.Qanun;
import java.util.ArrayList;

// Finds laws (qanun) in the database.
public class QanunDAO {

    private Database database;

    public QanunDAO(Database database) {
        this.database = database;
    }

    // Returns the law with this id, or null if there is none.
    public Qanun findById(int id) {
        for (Qanun q : database.qanuns) {
            if (q.getId() == id) {
                return q;
            }
        }
        return null;
    }

    // Returns the laws linked to this issue through approved links only.
    public ArrayList<Qanun> findApprovedByMasala(int masalaId) {
        ArrayList<Qanun> results = new ArrayList<Qanun>();
        for (Link link : database.links) {
            if (link.getMasalaId() == masalaId
                    && link.getType().equals("QANUN")
                    && link.isApproved()) {
                Qanun qanun = findById(link.getTargetId());
                if (qanun != null) {
                    results.add(qanun);
                }
            }
        }
        return results;
    }
}