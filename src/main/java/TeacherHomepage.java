import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TeacherHomepage extends JFrame {
    private JPanel teacherPanel;
    private JButton btnLogout;
    private JTable tableReqConsultation;
    private JTextField txtCourseTitle;
    private JTextField txtStatus;
    private JTextField txtDate;
    private JTextField txtId;
    private JButton btnProceed;
    private JButton btnReload;
    private JTextField txtTime;

    Connection con;
    PreparedStatement pst;

    public TeacherHomepage() {

        setContentPane(teacherPanel);
        setTitle("Teacher Homepage");
        setSize(1300,700);
        setVisible(true);

        connect();
        table_show();

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login log = new Login();
                setVisible(false);
            }
        });

        setVisible(true);


        btnProceed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id = txtId.getText();
                String courseTitle = txtCourseTitle.getText();
                String status = txtStatus.getText();

                if (id.isEmpty() || courseTitle.isEmpty() || status.isEmpty()) {
                    JOptionPane.showMessageDialog(btnProceed, "Please fill out the empty fields!");

                }

                else {
                    //JOptionPane.showMessageDialog(btnRegistration, "User Registered!");
                    //Login log = new Login();
                    //setVisible(false);

                try {
                    pst = con.prepareStatement("update req_consultation set Status = ? where StudentID = ? AND CourseTitle = ?");
                    pst.setString(1, status);
                    pst.setString(2, id);
                    pst.setString(3, courseTitle);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updated!");
                    table_show();
                    txtId.setText("");
                    txtCourseTitle.setText("");
                    txtStatus.setText("");
                    txtId.requestFocus();
                }

                    catch (SQLException e1)
                    {
                        e1.printStackTrace();
                    }
                }

            }

        });

        setVisible(true);

        /*btnReload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                table_show();

                JOptionPane.showMessageDialog(btnReload,"Table reloaded!");

            }
        });

        setVisible(true);

         */
    }



    public void table_show()
    {
        try
        {
            pst = con.prepareStatement("select * from req_consultation");
            ResultSet rs = pst.executeQuery();
            tableReqConsultation.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void connect ()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/consultation_booking",
                    "root","");
        }
        catch (ClassNotFoundException | SQLException ex)
        {
            ex.printStackTrace();
        }
    }

}
