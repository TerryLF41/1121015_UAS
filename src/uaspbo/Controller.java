package uaspbo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Model.CategoryUser;
import Model.User;

public class Controller {

    static DatabaseHandler conn = new DatabaseHandler();
    
    // SELECT CATEGORY
    public ArrayList<CategoryUser> getCategoryUser() {
        conn.connect();
        String query = "SELECT * FROM categoryuser";
        ArrayList<CategoryUser> categoryUser = new ArrayList<>();
        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                categoryUser.add(new CategoryUser(rs.getInt("category_id"), rs.getString("category_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (categoryUser);
    }

    // SELECT ALL USER
    public ArrayList<User> getUser() {
        conn.connect();
        String query = "SELECT * FROM users";
        ArrayList<User> user = new ArrayList<>();
        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            int tempCategoryId = 0;
            User userTemp = new User();
            while (rs.next()) {
                userTemp.setUserId(rs.getInt("user_id"));
                userTemp.setUserName(rs.getString("user_nama"));
                userTemp.setUserEmail(rs.getString(""));
                userTemp.setUserGender(rs.getString(""));
                userTemp.setUserFollowers(rs.getInt(""));
                tempCategoryId = (rs.getInt("user_category"));

                String query2 = "SELECT * FROM categoryuser WHERE category_id ='" + tempCategoryId + "'";
                ResultSet rs2 = stmt.executeQuery(query2);
                while (rs.next()) {
                    int tempId = rs2.getInt("category_id");
                    String tempName = rs2.getString("category_name");
                    CategoryUser tempCategory = new CategoryUser(tempId, tempName);
                    userTemp.setUserCategory(tempCategory);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (user);
    }
    
    // INSERT
    public static boolean insertNewUser(User user) {
        conn.connect();
        String query = "INSERT INTO users VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, user.getUserId());
            stmt.setString(2, user.getUserName());
            stmt.setString(3, user.getUserEmail());
            stmt.setString(4, user.getUserGender());
            stmt.setInt(5, user.getUserCategory().getCategoryId());
            stmt.setInt(6, user.getUserFollowers());
            stmt.executeUpdate();
            return (true);
        } catch (SQLException e) {
            e.printStackTrace();
            return (false);
        }
    }
}