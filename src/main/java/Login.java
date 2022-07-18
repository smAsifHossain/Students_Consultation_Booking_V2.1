import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JFrame {
    private JPanel loginPanel;
    private JButton btnGoToRegistration;
    private JTextField txtUserType;
    private JTextField txtPassword;
    private JTextField txtId;
    private JButton btnLogin;

    Connection con;
    PreparedStatement pst;

    public Login() {

        setContentPane(loginPanel);
        setTitle("Login");
        setSize(1300,700);
        setVisible(true);

        connect();

            btnLogin.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String id = txtId.getText();
                    String password = txtPassword.getText();
                    String userType = txtUserType.getText();

                    if (id.isEmpty() || password.isEmpty() || userType.isEmpty()) {
                        JOptionPane.showMessageDialog(btnLogin, "Fields are empty!");
                    }

                    else {

                        try {

                            String s = "select * from alluserinfo where ID=? AND Password=? AND UserType=?";

                            pst = con.prepareStatement(s);


                            pst.setString(1, txtId.getText());
                            pst.setString(2, txtPassword.getText());
                            pst.setString(3, txtUserType.getText());

                            ResultSet rs = pst.executeQuery();

                            if (rs.next() == true) {
                                JOptionPane.showMessageDialog(btnLogin, "Login Successful");

                                String user= txtUserType.getText();

                                if (user.equals("Student"))
                                {
                                    StudentHomepage studentHomepage = new StudentHomepage();
                                    setVisible(false);
                                    //JOptionPane.showMessageDialog(btnLogin, "I am a student");
                                }

                                else
                                {
                                    TeacherHomepage teacherHomepage = new TeacherHomepage();
                                    setVisible(false);
                                    //JOptionPane.showMessageDialog(btnLogin, "I am a teacher");
                                }

                            } else {
                                JOptionPane.showMessageDialog(btnLogin, "Wrong credentials");
                            }

                        } catch (SQLException e2) {
                            e2.printStackTrace();
                        }

                    }
                }

            });

            setVisible(true);

            btnGoToRegistration.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Registration reg = new Registration();
                    setVisible(false);
                }
            });

            setVisible(true);

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