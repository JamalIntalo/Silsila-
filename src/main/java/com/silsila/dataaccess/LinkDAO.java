package com.silsila.dataaccess;

import com.silsila.domain.Link;
import java.util.ArrayList;

// Finds and saves links in the database.
// (The other DAOs only show APPROVED links to students; this one is used by the
// contributors and reviewers, so it can see links of every status.)
public class LinkDAO {

    private Database database;

    public LinkDAO(Database database) {
        this.database = database;
    }

    public ArrayList<Link> findAll() {
        return database.links;
    }

    // Returns the link with this id, or null if there is none.
    public Link findById(int id) {
        for (Link link : database.links) {
            if (link.getId() == id) {
                return link;
            }
        }
        return null;
    }

    // Returns the links that wait for a reviewer.
    public ArrayList<Link> findPending() {
        ArrayList<Link> results = new ArrayList<Link>();
        for (Link link : database.links) {
            if (link.isPending()) {
                results.add(link);
            }
        }
        return results;
    }

    // The id for a new link: one more than the biggest id used so far.
    public int nextId() {
        int biggest = 0;
        for (Link link : database.links) {
            if (link.getId() > biggest) {
                biggest = link.getId();
            }
        }
        return biggest + 1;
    }

    public void add(Link link) {
        database.links.add(link);
    }
}