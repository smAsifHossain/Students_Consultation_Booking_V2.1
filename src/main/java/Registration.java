import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Registration extends JFrame {
    public JPanel regPanel;
    private JTextField txtName;
    private JTextField txtEmail;
    private JTextField txtId;
    private JButton btnRegistration;
    private JTextField txtUserType;
    private JButton btnGotoLogin;
    private JTextField txtPassword;


    Connection con;
    PreparedStatement pst;
    public Registration(){

        setContentPane(regPanel);
        setTitle("Registration");
        setSize(1300,700);
        setVisible(true);

        connect();

        btnRegistration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name = txtName.getText();
                String id = txtId.getText();
                String password = txtPassword.getText();
                String email = txtEmail.getText();
                String userType = txtUserType.getText();

                if (name.isEmpty() || id.isEmpty() || password.isEmpty() || email.isEmpty() || userType.isEmpty()){
                    JOptionPane.showMessageDialog(btnRegistration, "Please fill out the empty fields!");

                } else{
                    //JOptionPane.showMessageDialog(btnRegistration, "User Registered!");
                    Login log = new Login();
                    setVisible(false);
                }

                try {
                    pst = con.prepareStatement
                            ("insert into alluserinfo (Name,ID,Password,Email,UserType)values(?,?,?,?,?)");

                    pst.setString(1, name);
                    pst.setString(2, id);
                    pst.setString(3, password);
                    pst.setString(4, email);
                    pst.setString(5, userType);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null,"Registration Successful!");

                    txtName.setText("");
                    txtId.setText("");
                    txtPassword.setText("");
                    txtEmail.setText("");
                    txtUserType.setText("");
                    txtName.requestFocus();
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        setVisible(true);

        btnGotoLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login log = new Login();
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
                    "root","");
        }
        catch (ClassNotFoundException | SQLException ex)
        {
            ex.printStackTrace();
        }
    }
}
