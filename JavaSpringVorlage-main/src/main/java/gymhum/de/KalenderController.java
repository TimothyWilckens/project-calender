package gymhum.de;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.GregorianCalendar;


import gymhum.de.models.Kalenderevent;
import gymhum.de.models.User;

@Controller
public class KalenderController {

    ArrayList<User> alluser; 
    User currentUser;
    
    public KalenderController(){
        setAlluser(new ArrayList<>());
        testuser();
        System.out.println(currentUser.getKalenderevents().get(0).getDate());
    }

    // ANMELDUNG UND BENUTZERDATEN
    // Loginseite
    @GetMapping("/login")
    public String userlogin(@RequestParam(name="activePage", required = false, defaultValue = "login") String activePage, @RequestParam(name="username", required = false, defaultValue = "null") String username, @RequestParam(name="password", required = false, defaultValue = "null") String password, Model model){
        model.addAttribute("activePage", "login");
        return "index.html";
    }

    // Registrierung für neue Nutzer
    @GetMapping("/registration")
    public String userregistration(@RequestParam(name="activePage", required = false, defaultValue = "registration") String activePage, @RequestParam(name="username", required = false, defaultValue = "null") String username, @RequestParam(name="password", required = false, defaultValue = "null") String password, Model model){
        model.addAttribute("activePage", "registration");
        return "index.html";
    }
    // ANMELDUNG UND BENUTZERDATEN ENDE

    // KALENDEREINTRÄGE ANSICHTEN
    // Liste an Terminen
    @GetMapping("/terminliste")
    public String terminliste(@RequestParam(name="activePage", required = false, defaultValue = "terminliste") String activePage, Model model){
        model.addAttribute("activePage", "terminliste");
        model.addAttribute("currentuser", currentUser);
        model.addAttribute("kalenderevents", currentUser.getKalenderevents());
        return "index.html";
    }

    // Ansicht der Termine im allgemeinen Kalender
    @GetMapping("/kalenderansicht")
    public String kalenderansicht(@RequestParam(name="activePage", required = false, defaultValue = "kalenderansicht") String activePage, Model model){
        model.addAttribute("activePage", "kalenderansicht");
        model.addAttribute("currentuser", currentUser);
        return "index.html";
    }

    // TESTANSICHT FÜR ACCOUNTS
    @GetMapping("/allaccounts")
    public String allAccounts(@RequestParam(name="activePage", required = false, defaultValue = "allaccounts") String activePage, Model model){
        model.addAttribute("activePage", "allaccounts");
        model.addAttribute("allusers", getAlluser());
        System.out.println(getAlluser().size());
        return "index.html";
    }


    // KALENDEREINTRÄGE ANSICHTEN ENDE

    // KALENDEREINTRÄGE BEARBEITEN
    // Events bearbeiten
    @GetMapping("/changeevent")
    public String changeEvent(@RequestParam(name="activePage", required = false, defaultValue = "changeevent") String activePage, Model model){
        model.addAttribute("activePage", "changeevent");
        return "index.html";
    }
    @GetMapping("/changeeventsubmit")
    public String changeEventsubmit(@RequestParam(name="activePage", required = false, defaultValue = "changeeventsubmit") String activePage, Model model){
        model.addAttribute("activePage", "changeeventsubmit");
        return "redirect:/terminliste";
    }

    // Events hinzufügen
    @GetMapping("/addevent")
    public String addEvent(@RequestParam(name="activePage", required = false, defaultValue = "addevent") String activePage, Model model){
        model.addAttribute("activePage", "addevent");
        return "index.html";
    }
    @GetMapping("/addeventsubmit")
    public String addEventsubmit(@RequestParam(name="activePage", required = false, defaultValue = "addeventsubmit") String activePage, @RequestParam(name="name", required = true, defaultValue = "Nicht benannt") String name, @RequestParam(name="date", required = true, defaultValue = "01-01-2000") String date, Model model){
        model.addAttribute("activePage", "addeventsubmit");
        System.out.println(date);
        currentUser.getKalenderevents().add(currentUser.getKalenderevents().size()-1, new Kalenderevent(name, date));
        // Abfrage einbauen, ob ein Wert bei den anderen Parametern angegeben wurde, gegebenenfalls als Werte hinzufügen
        System.out.println(currentUser.getKalenderevents().get(currentUser.getKalenderevents().size()-1));
        
        DatumZuInteger(currentUser.getKalenderevents().get(currentUser.getKalenderevents().size()-1));
        
        return "redirect:/terminliste";
    }
    // Events löschen
    @GetMapping("/deleteevent")
    public String deleteEvent(@RequestParam(name="activePage", required = false, defaultValue = "kalender") String activePage, Model model){
        model.addAttribute("activePage", "kalender");
        System.out.println(getAlluser().size());
        return "index.html";
    }


    // METHODEN

    // Default Testuser
    private void testuser(){
        User user1 = new User("Max", "Mustermann");
        alluser.add(0, user1);
        setCurrentUser(user1);
        System.out.println("Testuser erstellt und hinzugefügt");
        Kalenderevent Kalenderevent1 = new Kalenderevent("Test", "01-02-2000");
        currentUser.getKalenderevents().add(0, Kalenderevent1);
        System.out.println("Kalenderevent hinzugefügt");
    }

    // Umwandlung von Datum in Integer

    private void DatumZuInteger(Kalenderevent kalenderevent){
        System.out.println("Umwandlung");
    }


    // SETTER UND GETTER
    public ArrayList<User> getAlluser() {
        return alluser;
    }
    public User getCurrentUser() {
        return currentUser;
    }
    public void setAlluser(ArrayList<User> alluser) {
        this.alluser = alluser;
    }
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
