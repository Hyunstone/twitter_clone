package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ChangePasswordUI extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    private JLabel lblEnterNewPassword;

    public ChangePasswordUI(String name) {
        setBounds(500, 120, 400, 180);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        textField = new JTextField();
        textField.setFont(new Font("Nanum Gothic", Font.PLAIN, 16));
        textField.setBounds(100, 30, 262, 33);
        contentPane.add(textField);
        textField.setColumns(10);

        JButton btnSearch = new JButton("변경하기");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String pstr = textField.getText();
                try {
                    System.out.println("update password name " + name);
                    System.out.println("update password");

                    Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/twit",
                            "root", "1234");

                    PreparedStatement st = (PreparedStatement) con
                            .prepareStatement("Update account set password=? where ID=?");

                    st.setString(1, pstr);
                    st.setString(2, name);
                    st.executeUpdate();
                    JOptionPane.showMessageDialog(btnSearch, "비밀번호가 변경되었습니다.", "Password changed", JOptionPane.PLAIN_MESSAGE);

                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        });

        btnSearch.setFont(new Font("Nanum Gothic", Font.BOLD, 14));
        btnSearch.setBackground(new Color(221, 221, 221));
        btnSearch.setForeground(new Color(31, 31, 31));
        btnSearch.setBounds(100, 88, 200, 33);
        contentPane.add(btnSearch);

        lblEnterNewPassword = new JLabel("새 비밀번호 :");
        lblEnterNewPassword.setFont(new Font("Nanum Gothic", Font.PLAIN, 12));
        lblEnterNewPassword.setForeground(new Color(254, 255, 255));
        lblEnterNewPassword.setBounds(34, 32, 72, 27);
        contentPane.add(lblEnterNewPassword);
    }
}