package gymhum.de;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import gymhum.de.models.Kalenderevent;
import gymhum.de.models.User;

public class DatabaseController {

	public void createTableUser() throws SQLException{
		Connection connection = connectUser();
		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("CREATE TABLE IF NOT EXISTS USERS(id INTEGER PRIMARY KEY, username TEXT, email TEXT, password TEXT)");
			closeConnection(connection);
		}
	}

    public void testConnectionUser(){
		Connection connection = null;
		try {
            String url = "jdbc:sqlite:USERS.db";
            connection = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");
            closeConnection(connection);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}

	public Connection connectUser(){
		Connection connection = null;
		try {
            String url = "jdbc:sqlite:USERS.db";
            connection = DriverManager.getConnection(url);
            return connection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return connection;
	}

	public void closeConnection(Connection connection) throws SQLException{
		connection.close();
	}

	public void addUser(User user) throws SQLException{
		Connection connection = connectUser();
		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("INSERT INTO USERS (username, email, password) values ('"+user.getUsername()+"','"+user.getEmail()+"','"+user.getPassword()+"')");
			closeConnection(connection);
		}
	}

	public void updateUsername(User user) throws SQLException{
		Connection connection = connectUser();
		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("UPDATE USERS SET username='"+user.getUsername()+"' WHERE id='"+user.getId()+"'");
			closeConnection(connection);
		}
	}
	public void updateEmail(User user) throws SQLException{
		Connection connection = connectUser();
		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("UPDATE USERS SET email='"+user.getEmail()+"' WHERE id='"+user.getId()+"'");
			closeConnection(connection);
		}
	}
	public void updatePassword(User user) throws SQLException{
		Connection connection = connectUser();
		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("UPDATE USERS SET password='"+user.getPassword()+"' WHERE id='"+user.getId()+"'");
			closeConnection(connection);
		}
	}


	public void removeUser(int id) throws SQLException{
		Connection connection = connectUser();
		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("DELETE FROM USERS WHERE id='"+id+"'");
			closeConnection(connection);
		}
	}

	public ArrayList<User> getUsers()  throws SQLException{
		ArrayList<User> users = new ArrayList<>();

		Connection connection = connectUser();
		if(connection != null){
			Statement statement = connection.createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM USERS ORDER BY username ASC");
			while(res.next()){
				users.add(new User(res.getInt(1), res.getString(2), res.getString(3), res.getString(4)));
			}
			closeConnection(connection);
		}

		return users;
	}


	// EVENTS


	public void testConnectionEvent(){
		Connection connection = null;
		try {
            String url = "jdbc:sqlite:EVENTS.db";
            connection = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");
            closeConnection(connection);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}

	public Connection connectEvent(){
		Connection connection = null;
		try {
            String url = "jdbc:sqlite:EVENTS.db";
            connection = DriverManager.getConnection(url);
            return connection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return connection;
	}

	public void createTableEvents() throws SQLException{
		Connection connection = connectEvent();
		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("CREATE TABLE IF NOT EXISTS EVENTS (id INTEGER PRIMARY KEY, name TEXT, date TEXT, username TEXT, email TEXT, password TEXT)");
			System.out.println("Table events erstellt");
			closeConnection(connection);
		}
	}

	public void addEvent(Kalenderevent event) throws SQLException{
		Connection connection = connectEvent();
		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("INSERT INTO EVENTS (name, date, username, email, password) values ('"+event.getName()+"','"+event.getDate()+"','"+event.getUsername()+"','"+event.getEmail()+"','"+event.getPassword()+"')");
			closeConnection(connection);
		}
	}

	public void updateEventname(Kalenderevent event) throws SQLException{
		Connection connection = connectEvent();
		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("UPDATE EVENTS SET name='"+event.getName()+"' WHERE id='"+event.getId()+"'");
			closeConnection(connection);
		}
	}
	public void updateEventdate(Kalenderevent event) throws SQLException{
		Connection connection = connectEvent();
		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("UPDATE EVENTS SET date='"+event.getDate()+"' WHERE id='"+event.getId()+"'");
			closeConnection(connection);
		}
	}
	public void updateEventUsername(Kalenderevent event) throws SQLException{
		Connection connection = connectEvent();
		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("UPDATE EVENTS SET username='"+event.getUsername()+"' WHERE id='"+event.getId()+"'");
			closeConnection(connection);
		}
	}
	public void updateEventEmail(Kalenderevent event) throws SQLException{
		Connection connection = connectEvent();
		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("UPDATE EVENTS SET email='"+event.getEmail()+"' WHERE id='"+event.getId()+"'");
			closeConnection(connection);
		}
	}
	public void updateEventPassword(Kalenderevent event) throws SQLException{
		Connection connection = connectEvent();
		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("UPDATE EVENTS SET password='"+event.getPassword()+"' WHERE id='"+event.getId()+"'");
			closeConnection(connection);
		}
	}


	public void removeEvent(int id) throws SQLException{
		Connection connection = connectEvent();
		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("DELETE FROM EVENTS WHERE id='"+id+"'");
			closeConnection(connection);
		}
	}

	public ArrayList<Kalenderevent> getEvents()  throws SQLException{
		ArrayList<Kalenderevent> Events = new ArrayList<>();

		Connection connection = connectEvent();
		if(connection != null){
			Statement statement = connection.createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM EVENTS ORDER BY username ASC");
			while(res.next()){
				Events.add(new Kalenderevent(res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5)));
			}
			closeConnection(connection);
		}

		return Events;
	}

}
