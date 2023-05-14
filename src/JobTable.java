package src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import models.Job;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;

public class JobTable extends JFrame implements ActionListener {
    private JTable table;
    private DefaultTableModel model;
    private JButton addButton, deleteButton, updateButton, refreshButton;
    private Connection conn;

    public JobTable(Connection conn, ArrayList<Job> jobs) {
        super("Job Table");
        this.conn = conn;
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the table model and table
        String[] columns = Job.getColumnNames();
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        for (Job row : jobs) {
            model.addRow(row.toArray());
        }

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Add the "Add" and "Delete" buttons
        addButton = new JButton("Add");
        addButton.addActionListener(this);
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);
        updateButton = new JButton("Update");
        updateButton.addActionListener(this);
        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(this);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(refreshButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            // Display a dialog for the user to enter a new job
            String[] jobFields = Job.getColumnNames();
            JTextField[] textFields = new JTextField[jobFields.length];
            JPanel panel = new JPanel(new GridLayout(jobFields.length, 2));
            for (int i = 1; i < jobFields.length; i++) {
                panel.add(new JLabel(jobFields[i]));
                textFields[i] = new JTextField();
                panel.add(textFields[i]);
            }
            int result = JOptionPane.showConfirmDialog(this, panel, "Add Job", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                // Add the new job to the table
                Job newJob = new Job(-1, textFields[1].getText(), textFields[2].getText(),
                        textFields[3].getText(),
                        textFields[4].getText(), Integer.parseInt(textFields[5].getText()),
                        Integer.parseInt(textFields[6].getText()));
                int lastIndex = Database.insertJob(conn, newJob);
                newJob.setId(lastIndex);
                model.addRow(newJob.toArray());
            }
        } else if (e.getSource() == deleteButton) {
            // Delete the selected row from the table
            int rowIndex = table.getSelectedRow();
            if (rowIndex >= 0) {
                int confirmation = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete this job?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    Database.deleteJob(conn, (int) model.getValueAt(rowIndex, 0));
                    model.removeRow(rowIndex);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a job to delete.");
            }
        } else if (e.getSource() == updateButton) {
            // Display a dialog for the user to update the selected job
            int rowIndex = table.getSelectedRow();
            if (rowIndex >= 0) {
                String[] jobFields = Job.getColumnNames();
                JTextField[] textFields = new JTextField[jobFields.length];
                JPanel panel = new JPanel(new GridLayout(jobFields.length, 2));
                for (int i = 1; i < jobFields.length; i++) {
                    panel.add(new JLabel(jobFields[i]));
                    textFields[i] = new JTextField(model.getValueAt(rowIndex, i).toString());
                    panel.add(textFields[i]);
                }
                int result = JOptionPane.showConfirmDialog(this, panel, "Update Job", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    // Update the selected job in the table
                    Job newJob = new Job(rowIndex, textFields[1].getText(), textFields[2].getText(),
                        textFields[3].getText(),
                        textFields[4].getText(), Integer.parseInt(textFields[5].getText()),
                        Integer.parseInt(textFields[6].getText()));

                    Database.updateJob(conn, rowIndex, newJob);
                    for (int i = 1; i < jobFields.length; i++) {
                        model.setValueAt(textFields[i].getText(), rowIndex, i);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a job to update.");
            }
        } else if (e.getSource() == refreshButton) {
            model.fireTableDataChanged();
        }

    }
}
