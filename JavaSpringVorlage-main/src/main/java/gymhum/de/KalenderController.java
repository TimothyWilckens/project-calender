package gymhum.de;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import gymhum.de.models.Kalenderevent;
import gymhum.de.models.User;

@Controller
public class KalenderController {

    ArrayList<User> alluser;
    User admin;
    User currentUser;
    ArrayList<Kalenderevent> activeKalenderevents;
    
    public KalenderController(){
        setAlluser(new ArrayList<>());
        admin();
        setActiveKalenderevents(new ArrayList<>());
        testuser();
        active_kalenderevents_in_array();
        System.out.println(currentUser.getKalenderevents()[0].getDate());
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
        try {
            DatabaseController db = new DatabaseController();
            for(User allusers : db.getUsers()){
                if((allusers.getUsername().equals(nameormail) && allusers.getPassword().equals(password)) || (allusers.getEmail().equals(nameormail) && allusers.getPassword().equals(password))){
                    setCurrentUser(allusers);
                    if(allusers.getUsername().equals(admin.getUsername()) && allusers.getPassword().equals(admin.getPassword()) || (allusers.getEmail().equals(admin.getEmail()) && allusers.getPassword().equals(admin.getPassword()))){
                        ArrayList<Integer> removeindexes = new ArrayList<>();
                        for(Kalenderevent currentkalenderevents : activeKalenderevents){
                            removeindexes.add(currentkalenderevents.getId());
                            System.out.println(currentkalenderevents.getId());
                        }
                        for(int removeindex : removeindexes){
                            activeKalenderevents.remove(removeindex);
                        }
                        active_kalenderevents_in_array();
                        System.out.println("Anmeldung mit Adminaccount");
                        return "redirect:/allaccounts";
                    } else{
                        ArrayList<Integer> removeindexes = new ArrayList<>();
                        for(Kalenderevent currentkalenderevents : activeKalenderevents){
                            removeindexes.add(currentkalenderevents.getId());
                            System.out.println(currentkalenderevents.getId());
                        }
                        for(int removeindex : removeindexes){
                            activeKalenderevents.remove(removeindex);
                        }
                        active_kalenderevents_in_array();
                        return "redirect:/terminliste";
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println("Es ist ein Fehler bei der Anmeldung aufgetreten!");
            System.out.println(e);
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
        model.addAttribute("id", alluser.size());
        return "index.html";
    }
    
    @PostMapping(path="/registrationsubmit")
    public String addAccountDo(@RequestParam MultiValueMap body){

        //Read Data Values from body-Object from Post-Body-Request
        User user = new User(Integer.parseInt(body.getFirst("id").toString()), body.getFirst("username").toString(), body.getFirst("email").toString(), body.getFirst("password").toString());
        try {
            DatabaseController db = new DatabaseController();
            db.addUser(user);
            alluser.add(alluser.size(), user);
            user.setId(alluser.size()-1);
            setCurrentUser(user);
            ArrayList<Integer> removeindexes = new ArrayList<>();
            for(Kalenderevent currentkalenderevents : activeKalenderevents){
                removeindexes.add(currentkalenderevents.getId());
            }
            for(int removeindex : removeindexes){
                activeKalenderevents.remove(removeindex);
            }
            active_kalenderevents_in_array();
            System.out.println(currentUser);
        }
        catch(Exception e){
            System.out.println("Es ist ein Fehler aufgetreten!");
            System.out.println(e);
        }
        return "redirect:/terminliste";
    }
    // Registrierung für neue Nutzer
    @GetMapping("/registrationthroughadmin")
    public String userregistrationadmin(@RequestParam(name="activePage", required = false, defaultValue = "registrationthroughadmin") String activePage, Model model){
        model.addAttribute("activePage", "registrationthroughadmin");
        model.addAttribute("id", alluser.size());
        return "index.html";
    }
    
    @PostMapping(path="/registrationthroughadminsubmit")
    public String addAccountAdminDo(@RequestParam MultiValueMap body){

        //Read Data Values from body-Object from Post-Body-Request
        User user = new User(Integer.parseInt(body.getFirst("id").toString()), body.getFirst("username").toString(), body.getFirst("email").toString(), body.getFirst("password").toString());
        try {
            DatabaseController db = new DatabaseController();
            db.addUser(user);
            alluser.add(alluser.size(), user);
            setCurrentUser(user);
            ArrayList<Integer> removeindexes = new ArrayList<>();
            for(Kalenderevent currentkalenderevents : activeKalenderevents){
                removeindexes.add(currentkalenderevents.getId());
            }
            for(int removeindex : removeindexes){
                activeKalenderevents.remove(removeindex);
            }
            active_kalenderevents_in_array();
            System.out.println(currentUser);
        }
        catch(Exception e){
            System.out.println("Es ist ein Fehler aufgetreten!");
            System.out.println(e);
        }

        // redirect to player-manage-page
        return "redirect:/allaccounts";
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
        try {
            DatabaseController db = new DatabaseController();
            if(username.equals("null")){
                System.out.println("Name wurde nicht geändert");
            } else{
                currentUser.setUsername(username);
                db.updateUsername(currentUser);
            }
            if(email.equals("null")){
                System.out.println("Mail wurde nicht geändert");
            } else{
                currentUser.setEmail(email);
                db.updateEmail(currentUser);
            }
            if(password.equals("null")){
                System.out.println("Passwort wurde nicht geändert");
            } else{
                currentUser.setPassword(password);
                db.updatePassword(currentUser);
            }
        } catch (SQLException e) {
            System.out.println("Beim Ändern der Nutzerdaten ist ein Fehler aufgetreten");
            System.out.println(e);
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
        try {
            DatabaseController db = new DatabaseController();
            db.removeUser(currentUser.getId());
        } catch (SQLException e) {
            System.out.println("Beim Entfernen des Accounts ist ein Fehler aufgetreten");
            System.out.println(e);
        }
        alluser.remove(currentUser);
        if(alluser.size() == 0){
            testuser();
        } else{
            setCurrentUser(alluser.get(0));
        }
        return "redirect:/terminliste";
    }

    @GetMapping("/deleteaccountadmin")
    public String deleteaccountadmin(@RequestParam(name="activePage", required = false, defaultValue = "deleteaccountadmin") String activePage, @RequestParam(name="id", required = true, defaultValue = "0") int id, Model model){
        model.addAttribute("activePage", "deleteaccountadmin");
        model.addAttribute("id", id);
        return "index.html";
    }
    @GetMapping("/deleteaccountadminsubmit")
    public String deleteaccountadminsubmit(@RequestParam(name="id", required = true, defaultValue = "0") int id, @RequestParam(name="activePage", required = false, defaultValue = "deleteaccountadminsubmit") String activePage, Model model){
        model.addAttribute("activePage", "deleteaccountadminsubmit");
        try {
            DatabaseController db = new DatabaseController();
            db.removeUser(id);
        } catch (SQLException e) {
            System.out.println("Beim Entfernen des Accounts ist ein Fehler aufgetreten");
            System.out.println(e);
        }
        return("redirect:/allaccounts");
    }
    // ANMELDUNG UND BENUTZERDATEN ENDE







    // KALENDEREINTRÄGE ANSICHTEN
    // Liste an Terminen
    @GetMapping("/terminliste")
    public String terminliste(@RequestParam(name="activePage", required = false, defaultValue = "terminliste") String activePage, Model model){
        model.addAttribute("activePage", "terminliste");
        model.addAttribute("currentuser", currentUser);
        model.addAttribute("kalenderevents", activeKalenderevents);
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
    public String allAccounts(@RequestParam(name="activePage", required = false, defaultValue = "allaccounts") String activePage, Model model) throws SQLException, IOException {
        model.addAttribute("activePage", "allaccounts");
        DatabaseController db = new DatabaseController();
        model.addAttribute("allusers", db.getUsers());
        if(currentUser.getUsername().equals(admin.getUsername()) && currentUser.getPassword().equals(admin.getPassword()) || (currentUser.getEmail().equals(admin.getEmail()) && currentUser.getPassword().equals(admin.getPassword()))){
            return "index.html";
        } else{
            return "redirect:/adminloginerror";
        }
        
    }

    @GetMapping("/adminloginerror")
    public String adminloginerror(@RequestParam(name="activePage", required = false, defaultValue = "adminloginerror") String activePage, Model model){
        model.addAttribute("activePage", "adminloginerror");
        return "index.html";
    }

    // KALENDEREINTRÄGE ANSICHTEN ENDE






    // KALENDEREINTRÄGE BEARBEITEN
    // Events bearbeiten
    @GetMapping("/changeevent")
    public String changeevent(@RequestParam(name="activePage", required = false, defaultValue = "changeevent") String activePage, @RequestParam(name="id", required = true) int id, Model model){
        model.addAttribute("activePage", "changeevent");
        model.addAttribute("id", id);
        activeKalenderevents.get(id).setId(id);
        model.addAttribute("kalenderevent", activeKalenderevents.get(id));
        return "index.html";
    }
    @GetMapping("/changeeventsubmit")
    public String changeEventsubmit(@RequestParam(name="activePage", required = false, defaultValue = "changeeventsubmit") String activePage, @RequestParam(name="id", required = true) int id, @RequestParam(name="name", required = true, defaultValue = "null") String name, @RequestParam(name="date", required = true, defaultValue = "null") String date, Model model){
        model.addAttribute("activePage", "changeeventsubmit");
        model.addAttribute("id", id);
        System.out.println(date);
        if(name.equals("null")){
            System.out.println("Name wurde nicht geändert");
            activeKalenderevents.get(id).setName("Nicht vorhanden");
        } else{
            activeKalenderevents.get(id).setName(name);
        }
        if(date.equals("null")){
            System.out.println("Datum wurde nicht geändert");
            activeKalenderevents.get(id).setDate("Nicht vorhanden");
        } else{
            activeKalenderevents.get(id).setDate(date);
        }

        DatumZuInteger(currentUser.getKalenderevents()[(getActiveKalenderevents().size()-1)]);
        
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
        currentUser.getKalenderevents()[getActiveKalenderevents().size()] = new Kalenderevent(name, date);
        active_kalenderevents_in_array();
        return "redirect:/terminliste";
    }
    // Events löschen
    @GetMapping("/deleteevent")
    public String deleteEvent(@RequestParam(name="activePage", required = false, defaultValue = "deleteevent") String activePage, @RequestParam(name="id", required = true) int id, Model model){
        model.addAttribute("activePage", "deleteevent");
        model.addAttribute("id", id);
        activeKalenderevents.get(id).setId(id);
        model.addAttribute("kalenderevent", currentUser.getKalenderevents()[id]);
        return "index.html";
    }
    @GetMapping("/deleteeventsubmit")
    public String deleteEventsubmit(@RequestParam(name="activePage", required = false, defaultValue = "deleteeventsubmit") String activePage, @RequestParam(name="id", required = true) int id, Model model){
        model.addAttribute("activePage", "deleteeventsubmit");
        model.addAttribute("id", id);
        activeKalenderevents.remove(getActiveKalenderevents().get(id));
        currentUser.getKalenderevents()[id] = null;
        active_kalenderevents_in_array();
        return "redirect:/terminliste";
    }
    // KALENDEREINTRÄGE BEARBEITEN ENDE





    // METHODEN

    // Default Testuser
    private void testuser(){
        User user1 = new User(alluser.size(),"Max", "maxmustermann@gmail.com", "Mustermann");
        alluser.add(alluser.size(), user1);
        setCurrentUser(user1);
        System.out.println("Testuser erstellt und hinzugefügt");
        Kalenderevent Kalenderevent1 = new Kalenderevent("Test", "01-02-2000");
        currentUser.getKalenderevents()[0] = Kalenderevent1;
        System.out.println("Kalenderevent hinzugefügt");
    }

    // Adminaccount
    private void admin(){
        setAdmin(new User(0,"Admin", "adminaccount@gmail.com", "adminpasswort"));
        DatabaseController db = new DatabaseController();
        try {
            if(db.getUsers().get(0).getUsername().equals(admin.getUsername()) && db.getUsers().get(0).getPassword().equals(admin.getPassword()) || (db.getUsers().get(0).getEmail().equals(admin.getEmail()) && db.getUsers().get(0).getPassword().equals(admin.getPassword()))){
                System.out.println("Adminaccount existierte bereits");
            } else{
                System.out.println("Adminaccount wurde erstellt");
                db.addUser(admin);
                alluser.add(0, admin);
                
            }
        } catch (SQLException e) {
            System.out.println("Es ist ein Fehler beim Erstellen des Admins aufgetreten");
            System.out.println(e);
        }
    }


    // Einbauen der Events in die aktuelle Ansicht
    private void active_kalenderevents_in_array(){
        for(Kalenderevent alleKalenderevents: currentUser.getKalenderevents()){
            if (alleKalenderevents != null) {
                getActiveKalenderevents().remove(alleKalenderevents);
                getActiveKalenderevents().add(alleKalenderevents);
                alleKalenderevents.setId(getActiveKalenderevents().indexOf(alleKalenderevents));
                System.out.println("Neues aktives Event " + alleKalenderevents.getId());
            }  
        }
    }

    // Umwandlung von Datum in Integer

    private void DatumZuInteger(Kalenderevent kalenderevent){
        System.out.println("Umwandlung");
    }


    // SETTER UND GETTER
    public ArrayList<User> getAlluser() {
        return alluser;
    }
    public User getAdmin() {
        return admin;
    }
    public User getCurrentUser() {
        return currentUser;
    }
    public ArrayList<Kalenderevent> getActiveKalenderevents() {
        return activeKalenderevents;
    }
    public void setAlluser(ArrayList<User> alluser) {
        this.alluser = alluser;
    }
    public void setAdmin(User admin) {
        this.admin = admin;
    }
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    public void setActiveKalenderevents(ArrayList<Kalenderevent> activeKalenderevents) {
        this.activeKalenderevents = activeKalenderevents;
    }
}
