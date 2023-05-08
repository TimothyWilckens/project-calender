package gymhum.de.models;

import java.util.ArrayList;

public class User {
    String username;
    String email;
    String password;
    ArrayList<Kalenderevent> Kalenderevents = new ArrayList<>();

    public User(String username, String email,String password){
        setKalenderevents(Kalenderevents);
        setPassword(password);
        setUsername(username);
        setEmail(email);
    }

    public void setKalenderevents(ArrayList<Kalenderevent> Kalenderevents) {
        this.Kalenderevents = Kalenderevents;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public ArrayList<Kalenderevent> getKalenderevents() {
        return Kalenderevents;
    }
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
}
