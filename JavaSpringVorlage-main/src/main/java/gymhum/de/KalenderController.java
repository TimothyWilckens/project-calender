package gymhum.de;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;


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
    public String userlogin(@RequestParam(name="activePage", required = false, defaultValue = "login") String activePage, Model model){
        model.addAttribute("activePage", "login");
        return "index.html";
    }
    @GetMapping("/loginsubmit")
    public String userloginsubmit(@RequestParam(name="activePage", required = false, defaultValue = "loginsubmit") String activePage, @RequestParam(name="nameormail", required = false, defaultValue = "null") String nameormail, @RequestParam(name="password", required = false, defaultValue = "null") String password, Model model){
        model.addAttribute("activePage", "loginsubmit");
        System.out.println(nameormail + password);
        for(User allusers : alluser){
            if((allusers.getUsername().equals(nameormail) && allusers.getPassword().equals(password)) || (allusers.getEmail().equals(nameormail) && allusers.getPassword().equals(password))){
                setCurrentUser(allusers);
                return "redirect:/terminliste";
            }
        }
        return "redirect:/loginerror";
    }
    @GetMapping("/loginerror")
    public String userloginerror(@RequestParam(name="activePage", required = false, defaultValue = "loginerror") String activePage, Model model){
        model.addAttribute("activePage", "loginerror");
        return "index.html";
    }

    // Registrierung für neue Nutzer
    @GetMapping("/registration")
    public String userregistration(@RequestParam(name="activePage", required = false, defaultValue = "registration") String activePage, Model model){
        model.addAttribute("activePage", "registration");
        return "index.html";
    }
    @GetMapping("/registrationsubmit")
    public String userregistrationsubmit(@RequestParam(name="activePage", required = false, defaultValue = "registrationsubmit") String activePage, @RequestParam(name="username", required = false, defaultValue = "null") String username, @RequestParam(name="email", required = false, defaultValue = "null") String email,@RequestParam(name="password", required = false, defaultValue = "null") String password, Model model){
        model.addAttribute("activePage", "registrationsubmit");
        alluser.add(alluser.size(), new User(username, email, password));
        setCurrentUser(alluser.get(alluser.size()-1));
        return "redirect:/terminliste";
    }

    // Anzeige der Nutzerdaten
    @GetMapping("/manageaccount")
    public String manageaccount(@RequestParam(name="activePage", required = false, defaultValue = "manageaccount") String activePage, Model model){
        model.addAttribute("activePage", "manageaccount");
        model.addAttribute("currentuser", currentUser);
        return "index.html";
    }

    // Änderungen der Nutzerdaten
    @GetMapping("/changeuserdata")
    public String changeuserdata(@RequestParam(name="activePage", required = false, defaultValue = "changeuserdata") String activePage, Model model){
        model.addAttribute("activePage", "changeuserdata");
        model.addAttribute("currentuser", currentUser);
        return "index.html";
    }
    @GetMapping("/changeuserdatasubmit")
    public String changeuserdatasubmit(@RequestParam(name="activePage", required = false, defaultValue = "changeuserdatasubmit") String activePage, @RequestParam(name="username", required = false, defaultValue = "null") String username, @RequestParam(name="email", required = false, defaultValue = "null") String email,@RequestParam(name="password", required = false, defaultValue = "null") String password, Model model){
        model.addAttribute("activePage", "changeuserdatasubmit");
        if(username.equals("null")){
            System.out.println("Name wurde nicht geändert");
        } else{
            currentUser.setUsername(username);
        }
        if(email.equals("null")){
            System.out.println("Mail wurde nicht geändert");
        } else{
            currentUser.setEmail(email);
        }
        if(password.equals("null")){
            System.out.println("Passwort wurde nicht geändert");
        } else{
            currentUser.setPassword(password);
        }
        return "redirect:/manageaccount";
    }

    // Löschen des Accounts
    @GetMapping("/deleteaccount")
    public String deleteaccount(@RequestParam(name="activePage", required = false, defaultValue = "deleteaccount") String activePage, Model model){
        model.addAttribute("activePage", "deleteaccount");
        return "index.html";
    }
    @GetMapping("/deleteaccountsubmit")
    public String deleteaccountsubmit(@RequestParam(name="activePage", required = false, defaultValue = "deleteaccountsubmit") String activePage, Model model){
        model.addAttribute("activePage", "deleteaccountsubmit");
        alluser.remove(currentUser);
        if(alluser.size() == 0){
            testuser();
        } else{
            setCurrentUser(alluser.get(0));
        }
        return "redirect:/manageaccount";
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

    // TESTANSICHT FÜR ACCOUNTS (NOCH AUF NUR FÜR ADMIN SICHTBAR STELLEN)
    @GetMapping("/allaccounts")
    public String allAccounts(@RequestParam(name="activePage", required = false, defaultValue = "allaccounts") String activePage, Model model){
        model.addAttribute("activePage", "allaccounts");
        model.addAttribute("allusers", getAlluser());
        return "index.html";
    }


    // KALENDEREINTRÄGE ANSICHTEN ENDE

    // KALENDEREINTRÄGE BEARBEITEN
    // Events bearbeiten
    @GetMapping("/changeevent")
    public String chanegevent(@RequestParam(name="activePage", required = false, defaultValue = "changeevent") String activePage, @RequestParam(name="id", required = true) int id, Model model){
        model.addAttribute("activePage", "changeevent");
        model.addAttribute("id", id);
        currentUser.getKalenderevents().get(id).setId(id);
        model.addAttribute("kalenderevent", currentUser.getKalenderevents().get(id));
        System.out.println(id);
        return "index.html";
    }
    @GetMapping("/changeeventsubmit")
    public String changeEventsubmit(@RequestParam(name="activePage", required = false, defaultValue = "changeeventsubmit") String activePage, @RequestParam(name="id", required = true) int id, @RequestParam(name="name", required = true, defaultValue = "null") String name, @RequestParam(name="date", required = true, defaultValue = "null") String date, Model model){
        model.addAttribute("activePage", "changeeventsubmit");
        model.addAttribute("id", id);
        System.out.println(date);
        if(name.equals("null")){
            System.out.println("Name wurde nicht geändert");
        } else{
            currentUser.getKalenderevents().get(id).setName(name);;
        }
        if(date.equals("null")){
            System.out.println("Datum wurde nicht geändert");
        } else{
            currentUser.getKalenderevents().get(id).setDate(date);
        }

        DatumZuInteger(currentUser.getKalenderevents().get(currentUser.getKalenderevents().size()-1));
        
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
        currentUser.getKalenderevents().add(currentUser.getKalenderevents().size(), new Kalenderevent(name, date));
        // Abfrage einbauen, ob ein Wert bei den anderen Parametern angegeben wurde, gegebenenfalls als Werte hinzufügen
        System.out.println(currentUser.getKalenderevents().get(currentUser.getKalenderevents().size()-1));

        DatumZuInteger(currentUser.getKalenderevents().get(currentUser.getKalenderevents().size()-1));
        
        return "redirect:/terminliste";
    }
    // Events löschen
    @GetMapping("/deleteevent")
    public String deleteEvent(@RequestParam(name="activePage", required = false, defaultValue = "deleteevent") String activePage, @RequestParam(name="id", required = true) int id, Model model){
        model.addAttribute("activePage", "deleteevent");
        model.addAttribute("id", id);
        currentUser.getKalenderevents().get(id).setId(id);
        model.addAttribute("kalenderevent", currentUser.getKalenderevents().get(id));
        System.out.println(id);
        return "index.html";
    }
    @GetMapping("/deleteeventsubmit")
    public String deleteEventsubmit(@RequestParam(name="activePage", required = false, defaultValue = "deleteeventsubmit") String activePage, @RequestParam(name="id", required = true) int id, Model model){
        model.addAttribute("activePage", "deleteeventsubmit");
        model.addAttribute("id", id);
        System.out.println(id);
        currentUser.getKalenderevents().remove(id);
        return "redirect:/terminliste";
    }


    // METHODEN

    // Default Testuser
    private void testuser(){
        User user1 = new User("Max", "maxmustermann@gmail.com", "Mustermann");
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
