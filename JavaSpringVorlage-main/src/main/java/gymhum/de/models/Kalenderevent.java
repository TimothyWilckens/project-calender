package gymhum.de.models;

public class Kalenderevent {
    String name;
    String date;
    int id;

    public Kalenderevent(String name, String date){
        setName(name);
        setDate(date);
    }

    
    public void setName(String name) {
        this.name = name;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setId(int id) {
        this.id = id;
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
