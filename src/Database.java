package src;

import java.util.*;

import java.sql.*;
import models.Job;
import io.github.cdimascio.dotenv.Dotenv;

public class Database {

    public static int insertJob(Connection conn, Job j) {
        int lastIndex = -1;
        String query1 = "INSERT INTO jobs (job_title, company, location, job_type, salary_min, salary_max) " +
                String.format("VALUES ('%s', '%s', '%s', '%s', %d, %d)",
                        j.getJobTitle(), j.getCompany(), j.getLocation(),
                        j.getJobType(), j.getSalaryMin(), j.getSalaryMax());
        String query2 = "SELECT LAST_INSERT_ID()";
        try {
            Statement st1 = conn.createStatement();
            st1.executeUpdate(query1);
            st1.close();

            Statement st2 = conn.createStatement();
            ResultSet rs = st2.executeQuery(query2);
            while (rs.next())
                lastIndex = rs.getInt("LAST_INSERT_ID()");
            rs.close();
            st2.close();
            System.out.println("Successfully inserted to database with ID = " + lastIndex);
            return lastIndex;
        } catch (SQLException e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
        return lastIndex;
    }

    public static void deleteJob(Connection conn, int id) {
        String query = String.format("DELETE FROM jobs WHERE id = %d", id);
        try {
            Statement st = conn.createStatement();
            st.executeUpdate(query);
            st.close();
            System.out.println("Deleted record at ID = " + id);
        } catch (SQLException e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }

    public static void updateJob(Connection conn, int id, Job j) {
        String query = String.format(
                "UPDATE jobs SET job_title = '%s', company = '%s', location = '%s', job_type = '%s', salary_min = %d, salary_max = %d WHERE id = %d",
                j.getJobTitle(), j.getCompany(), j.getLocation(),
                j.getJobType(), j.getSalaryMin(), j.getSalaryMax(), (id + 1));
        try {
            Statement st = conn.createStatement();
            st.executeUpdate(query);
            st.close();
            System.out.println("Updated record at ID = " + (id + 1));
        } catch (SQLException e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }

    public static ArrayList<Job> getData(Connection conn) {
        ArrayList<Job> arr = new ArrayList<Job>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM jobs");
            while (rs.next()) {
                arr.add(new Job(rs.getInt("id"),
                        rs.getString("job_title"),
                        rs.getString("company"),
                        rs.getString("location"),
                        rs.getString("job_type"),
                        rs.getInt("salary_min"),
                        rs.getInt("salary_max")));
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println("getData() - Error message: " + e.getMessage());
        }
        return arr;
    }

    public static Connection getConnection() throws SQLException {
        Dotenv dotenv = Dotenv.load();

        String url = dotenv.get("SQL_URL");
        String username = dotenv.get("SQL_USERNAME");
        String password = dotenv.get("SQL_PASSWORD");

        Connection conn = DriverManager.getConnection(url, username, password);
        System.out.println("Connection successful!\n");
        return conn;
    }

    public static void closeConnection(Connection connection) {
        try {
            connection.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
