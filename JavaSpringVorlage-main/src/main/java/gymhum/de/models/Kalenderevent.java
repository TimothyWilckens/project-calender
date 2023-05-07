package gymhum.de.models;

public class Kalenderevent {
    String name;
    String date;

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
    
    public String getName() {
        return name;
    }
    public String getDate() {
        return date;
    }
}
