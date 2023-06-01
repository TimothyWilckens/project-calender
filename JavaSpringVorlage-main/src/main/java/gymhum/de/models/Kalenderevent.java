package gymhum.de.models;

public class Kalenderevent {
    String name;
    String date;
    int id;
    String username;
    String email;
    String password;

    public Kalenderevent(String name, String date, String username, String email, String password){
        setName(name);
        setDate(date);
        setUsername(username);
        setEmail(email);
        setPassword(password);
    }

    
    public void setName(String name) {
        this.name = name;
    }
    public void setDate(String date) {
        this.date = date;
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
    public void setId(int id) {
        this.id = id;
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
    public String getName() {
        return name;
    }
    public String getDate() {
        return date;
    }
    public int getId() {
        return id;
    }
}
