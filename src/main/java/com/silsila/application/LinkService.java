package com.silsila.application;

import com.silsila.dataaccess.AyahDAO;
import com.silsila.dataaccess.HadithDAO;
import com.silsila.dataaccess.LinkDAO;
import com.silsila.dataaccess.MasalaDAO;
import com.silsila.dataaccess.QanunDAO;
import com.silsila.domain.Ayah;
import com.silsila.domain.Hadith;
import com.silsila.domain.Link;
import com.silsila.domain.Masala;
import com.silsila.domain.Qanun;
import com.silsila.domain.User;
import java.util.ArrayList;

// The logic of the curation workflow (requirements FR-6 and FR-7):
//   a contributor PROPOSES a link  -> it is saved with the status PENDING
//   a reviewer APPROVES or REJECTS -> only APPROVED links are shown to students
//
// The methods that can fail return a text: "" means everything went well,
// anything else is the message to show to the person.
public class LinkService {

    private LinkDAO linkDAO;
    private MasalaDAO masalaDAO;
    private AyahDAO ayahDAO;
    private HadithDAO hadithDAO;
    private QanunDAO qanunDAO;

    public LinkService(LinkDAO linkDAO, MasalaDAO masalaDAO, AyahDAO ayahDAO,
                       HadithDAO hadithDAO, QanunDAO qanunDAO) {
        this.linkDAO = linkDAO;
        this.masalaDAO = masalaDAO;
        this.ayahDAO = ayahDAO;
        this.hadithDAO = hadithDAO;
        this.qanunDAO = qanunDAO;
    }

    // ---------------------------------------------------------------
    // Things the pages need to show
    // ---------------------------------------------------------------
    // The title of an issue, or null if there is no such issue.
    public String getMasalaTitle(int masalaId) {
        Masala masala = masalaDAO.findById(masalaId);
        if (masala == null) {
            return null;
        }
        return masala.getTitle();
    }

    // The lists used to fill the drop-down menus of the proposal form.
    public ArrayList<Ayah> getAllAyat() {
        return ayahDAO.findAll();
    }

    public ArrayList<Hadith> getAllHadiths() {
        return hadithDAO.findAll();
    }

    public ArrayList<Qanun> getAllQanuns() {
        return qanunDAO.findAll();
    }

    // The links that wait for a reviewer.
    public ArrayList<Link> getPendingLinks() {
        return linkDAO.findPending();
    }

    // A readable name for what a link points to, for example "Surah Al-Baqarah 2:229".
    public String describeTarget(Link link) {
        if (link.getType().equals("AYAH")) {
            Ayah a = ayahDAO.findById(link.getTargetId());
            if (a != null) {
                return a.getReference();
            }
        } else if (link.getType().equals("HADITH")) {
            Hadith h = hadithDAO.findById(link.getTargetId());
            if (h != null) {
                return h.getReference();
            }
        } else if (link.getType().equals("QANUN")) {
            Qanun q = qanunDAO.findById(link.getTargetId());
            if (q != null) {
                return q.getReference();
            }
        }
        return "(unknown)";
    }

    // ---------------------------------------------------------------
    // A contributor proposes a link
    // ---------------------------------------------------------------
    public String propose(User user, int masalaId, String type, int targetId, String citation) {
        // Only logged-in people may propose.
        if (user == null) {
            return "You must log in to propose a link.";
        }
        if (masalaDAO.findById(masalaId) == null) {
            return "This issue does not exist.";
        }

        // The rule of requirement NFR-4: no link without a citation.
        if (citation == null || citation.trim().equals("")) {
            return "A citation is required. Write where this connection comes from.";
        }
        if (citation.length() > 300) {
            return "The citation is too long (300 characters at most).";
        }

        // The target must be a real verse, hadith or law.
        Link newLink = new Link(linkDAO.nextId(), masalaId, type, targetId, citation.trim(), "PENDING");
        if (describeTarget(newLink).equals("(unknown)")) {
            return "Please choose a verse, a hadith or a law from the list.";
        }

        // Do not allow the same link twice (unless the old one was rejected).
        for (Link old : linkDAO.findAll()) {
            if (old.getMasalaId() == masalaId && old.getType().equals(type)
                    && old.getTargetId() == targetId && !old.getStatus().equals("REJECTED")) {
                return "This link already exists or is already waiting for a reviewer.";
            }
        }

        newLink.setProposedBy(user.getUsername());
        linkDAO.add(newLink);
        return "";
    }

    // ---------------------------------------------------------------
    // A reviewer decides
    // ---------------------------------------------------------------
    // decision is "approve" or "reject".
    public String decide(User reviewer, int linkId, String decision) {
        // Only reviewers may decide (requirement FR-7).
        if (reviewer == null || !reviewer.isReviewer()) {
            return "Only a reviewer can approve or reject links.";
        }
        Link link = linkDAO.findById(linkId);
        if (link == null || !link.isPending()) {
            return "This link is not waiting for a decision.";
        }

        if (decision.equals("approve")) {
            link.setStatus("APPROVED");
        } else if (decision.equals("reject")) {
            link.setStatus("REJECTED");
        } else {
            return "Unknown decision.";
        }
        return "";
    }
}