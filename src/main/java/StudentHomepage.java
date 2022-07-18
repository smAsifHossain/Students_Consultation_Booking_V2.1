import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import static com.sun.glass.ui.Cursor.setVisible;

public class StudentHomepage extends JFrame {
    private JButton btnLogout;
    private JTextField txtTime;
    private JTextField txtDate;
    private JTextField txtCourseTitle;
    private JButton btnRequestConsultation;
    private JPanel studentPanel;
    private JTextField txtId;
    private JButton btnShowStatus;
    private JTextField txtShowId;
    private JTextField txtShowCourseTitle;
    private JTextField txtShowDate;
    private JTextField txtShowStatus;
    private JTextField txtShowTime;

    Connection con;
    PreparedStatement pst;
    public StudentHomepage() {

        setContentPane(studentPanel);
        setTitle("Student Homepage");
        setSize(1300,700);
        setVisible(true);

        connect();

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Login log = new Login();
                setVisible(false);

            }
        });
        setVisible(true);

        btnRequestConsultation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id = txtId.getText();
                String time = txtTime.getText();
                String date = txtDate.getText();
                String course = txtCourseTitle.getText();

                if (id.isEmpty() || time.isEmpty() || date.isEmpty() || course.isEmpty()) {
                    JOptionPane.showMessageDialog(btnRequestConsultation, "Fields are EMPTY!");
                } else {
                    StudentHomepage studentHomepage = new StudentHomepage();
                    setVisible(false);

                    //JOptionPane.showMessageDialog(btnRequestConsultation, "Request Proceed!");
                    try {
                        pst = con.prepareStatement
                                ("insert into req_consultation (StudentID,ReqTime,ReqDate,CourseTitle)values(?,?,?,?)");

                        pst.setString(1, id);
                        pst.setString(2, time);
                        pst.setString(3, date);
                        pst.setString(4, course);
                        pst.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Request Placed!");

                        txtTime.setText("");
                        txtTime.setText("");
                        txtDate.setText("");
                        txtCourseTitle.setText("");
                        txtId.requestFocus();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                }

            }

        });
        setVisible(true);

        btnShowStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    String showId = txtShowId.getText();

                    pst = con.prepareStatement("select ReqTime,ReqDate,CourseTitle,Status from req_consultation where StudentID = ?");
                    pst.setString(1, showId);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String showTime = rs.getString(1);
                        String showDate = rs.getString(2);
                        String showCourseTitle = rs.getString(3);
                        String showStatus = rs.getString(4);

                        txtShowTime.setText(showTime);
                        txtShowDate.setText(showDate);
                        txtShowCourseTitle.setText(showCourseTitle);
                        txtShowStatus.setText(showStatus);

                    }
                    else
                    {
                        txtShowTime.setText("");
                        txtShowDate.setText("");
                        txtCourseTitle.setText("");
                        txtShowStatus.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Student ID. Try again!");

                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }

        });
    }

    public void connect ()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/consultation_booking",
                    "root", "");
        }
        catch (ClassNotFoundException | SQLException ex)
        {
            ex.printStackTrace();
        }
    }
}