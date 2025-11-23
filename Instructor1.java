package frontendpkg;

import backendpkg.*;
import javax.swing.*;

public class InstructorDashboard extends javax.swing.JFrame {

    private Database db;
    private Instructor instructor;
    private CourseManagement cm;

    private javax.swing.JButton viewInsightsButton;

    public InstructorDashboard(Database db, Instructor instructor, CourseManagement cm) {
        this.db = db;
        this.instructor = instructor;
        this.cm = cm;
        initComponents();
    }

    private void initComponents() {
        viewInsightsButton = new javax.swing.JButton();
        viewInsightsButton.setText("View Insights");
        viewInsightsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewInsightsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(129, 129, 129)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(viewInsightsButton)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(151, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(myCourses)
                                        .addComponent(logout))
                                .addGap(160, 160, 160))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLabel1)
                                .addGap(66, 66, 66)
                                .addComponent(AvailableCourses)
                                .addGap(18, 18, 18)
                                .addComponent(myCourses)
                                .addGap(18, 18, 18)
                                .addComponent(viewInsightsButton)
                                .addGap(39, 39, 39)
                                .addComponent(logout)
                                .addContainerGap(68, Short.MAX_VALUE))
        );

        pack();
    }

    private void viewInsightsButtonActionPerformed(java.awt.event.ActionEvent evt) {
        new InsightsChartFrame().setVisible(true);
        this.dispose();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InstructorDashboard(new Database(), new Instructor(), new CourseManagement()).setVisible(true);
            }
        });
    }
}