package gymhum.de.models;

public class User {
    String username;
    String email;
    String password;
    int id;

    public User(int id, String username, String email, String password){
        setPassword(password);
        setUsername(username);
        setEmail(email);
        setId(id);
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
    public int getId() {
        return id;
    }

    
    @Override
	public String toString() {
		return "TODO";
	}
}
