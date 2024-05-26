import java.sql.*;

public class Database {
    Connection con;
    //This method executes a query and returns the number of albums for the artist with name artistName
    public int getTitles(String artistName) {
        int titleNum = 0;
        try{

            String sql = "SELECT COUNT(albumid) FROM album JOIN artist USING (artistid) WHERE artist.name = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.clearParameters();
            pstmt.setString(1,artistName);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            titleNum = rs.getInt("count");
        } catch(SQLException e){
            e.printStackTrace();
        }

        //Implement this method
        //Prevent SQL injections!
        return titleNum;
    }

    // This method establishes a DB connection & returns a boolean status
    public boolean establishDBConnection() {

        try {

            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(Credentials.URL, Credentials.USERNAME, Credentials.PASSWORD);
            
            return con.isValid(5);
            

        } catch (ClassNotFoundException | SQLException e) {
            return false;
        }

        //Implement this method
        //5 sec timeout
    }
}