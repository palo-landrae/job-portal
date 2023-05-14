package src;

import java.util.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import models.*;

public class App {
    public static void main(String[] args) throws SQLException {
        Connection conn = Database.getConnection();

        ArrayList<Job> arr = Database.getData(conn);

        JobTable jobTable = new JobTable(conn, arr);
        jobTable.setLocationRelativeTo(null);
        jobTable.setVisible(true);
        jobTable.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Database.closeConnection(conn);
                System.out.println("Program closed.");
                e.getWindow().dispose();
            }
        });
    }
}
