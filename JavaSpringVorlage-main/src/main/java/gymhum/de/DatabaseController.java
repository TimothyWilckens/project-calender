package gymhum.de;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import gymhum.de.models.User;

public class DatabaseController {

	public void createTable() throws SQLException{
		Connection connection = connect();
		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("CREATE TABLE IF NOT EXISTS USERS(id INTEGER PRIMARY KEY, username TEXT, email TEXT, password TEXT)");
			closeConnection(connection);
		}
	}

    public void testConnection(){
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

	public Connection connect(){
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
		Connection connection = connect();
		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("INSERT INTO USERS (username, email, password) values ('"+user.getUsername()+"','"+user.getEmail()+"','"+user.getPassword()+"')");
			closeConnection(connection);
		}
	}

	public void updateUsername(User user) throws SQLException{
		Connection connection = connect();
		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("UPDATE USERS SET username='"+user.getUsername()+"' WHERE id='"+user.getId()+"'");
			closeConnection(connection);
		}
	}
	public void updateEmail(User user) throws SQLException{
		Connection connection = connect();
		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("UPDATE USERS SET email='"+user.getEmail()+"' WHERE id='"+user.getId()+"'");
			closeConnection(connection);
		}
	}
	public void updatePassword(User user) throws SQLException{
		Connection connection = connect();
		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("UPDATE USERS SET password='"+user.getPassword()+"' WHERE id='"+user.getId()+"'");
			closeConnection(connection);
		}
	}


	public void removeUser(int id) throws SQLException{
		Connection connection = connect();
		if(connection != null){
			Statement statement = connection.createStatement();
			statement.execute("DELETE FROM USERS WHERE id='"+id+"'");
			closeConnection(connection);
		}
	}

	public ArrayList<User> getUsers()  throws SQLException{
		ArrayList<User> users = new ArrayList<>();

		Connection connection = connect();
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

}
