package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;

public class LoginUI extends JFrame {

    private JTextField textField;
    private JPasswordField passwordField;
    private JButton btnNewButton1;
    private JButton btnNewButton2;
    private JLabel label;
    private JPanel contentPane;
    
    //버튼 디자인
    public class RoundedButton extends JButton {
       public RoundedButton() { super(); decorate(); } 
       public RoundedButton(String text) { super(text); decorate(); } 
       public RoundedButton(Action action) { super(action); decorate(); } 
       public RoundedButton(Icon icon) { super(icon); decorate(); } 
       public RoundedButton(String text, Icon icon) { super(text, icon); decorate(); } 
       protected void decorate() { setBorderPainted(false); setOpaque(false); }
       @Override 
       protected void paintComponent(Graphics g) {
          Color c=new Color(239,243,244); //배경색 결정
          Color o=new Color(15,20,25); //글자색 결정
          int width = getWidth(); 
          int height = getHeight(); 
          Graphics2D graphics = (Graphics2D) g; 
          graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
          if (getModel().isArmed()) { graphics.setColor(c.darker()); } 
          else if (getModel().isRollover()) { graphics.setColor(c.brighter()); } 
          else { graphics.setColor(c); } 
          graphics.fillRoundRect(0, 0, width, height, 40, 40); 
          FontMetrics fontMetrics = graphics.getFontMetrics(); 
          Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds(); 
          int textX = (width - stringBounds.width) / 2; 
          int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent(); 
          graphics.setColor(o); 
          graphics.setFont(getFont()); 
          graphics.drawString(getText(), textX, textY); 
          graphics.dispose(); 
          super.paintComponent(g); 
          }
       }

    public LoginUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500, 80, 400, 710);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("트위터에 로그인하기");
        lblNewLabel.setBounds(14, 199, 373, 93);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setForeground(new Color(221, 228, 232));
        lblNewLabel.setFont(new Font("Nanum Gothic", Font.BOLD, 30));
        contentPane.add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(69, 343, 262, 33);
        textField.setFont(new Font("Nanum Gothic", Font.PLAIN, 20));
        contentPane.add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(69, 403, 262, 33);
        passwordField.setFont(new Font("Nanum Gothic", Font.PLAIN, 20));
        contentPane.add(passwordField);

        JLabel lblUsername = new JLabel("사용자 아이디");
        lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
        lblUsername.setBounds(66, 316, 76, 27);
        lblUsername.setForeground(new Color(254, 255, 255));
        lblUsername.setFont(new Font("Nanum Gothic", Font.PLAIN, 12));
        contentPane.add(lblUsername);

        JLabel lblPassword = new JLabel("비밀번호");
        lblPassword.setBounds(71, 378, 50, 27);
        lblPassword.setForeground(new Color(254, 255, 255));
        lblPassword.setFont(new Font("Nanum Gothic", Font.PLAIN, 12));
        contentPane.add(lblPassword);

        btnNewButton1 = new RoundedButton("로그인하기");
        btnNewButton1.setBackground(new Color(221, 221, 221));
        btnNewButton1.setForeground(new Color(31, 31, 31));
        btnNewButton1.setBounds(69, 474, 262, 40);
        btnNewButton1.setFont(new Font("Nanum Gothic", Font.BOLD, 14));
        btnNewButton1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String id = textField.getText();
                String password = passwordField.getText();
                try {
                    Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/twit",
                            "root", "1234");

                    PreparedStatement st = (PreparedStatement) connection
                            .prepareStatement("Select id, password from account where id=? and password=?");

                    st.setString(1, id);
                    st.setString(2, password);
                    ResultSet rs = st.executeQuery();
                    if (rs.next()) {
                        dispose();
                        MainBoardUI main = new MainBoardUI(id);
                        main.setTitle("Twitter");
                        main.setVisible(true);
                        JOptionPane.showMessageDialog(btnNewButton1, "환영합니다", "Login", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(btnNewButton1, "아이디 또는 비밀번호가 틀렸습니다", "Failed to Login", JOptionPane.PLAIN_MESSAGE);
                    }
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        });

        btnNewButton2 = new RoundedButton("새로 가입하기" );
        btnNewButton2.setForeground(new Color(254, 255, 255));
        btnNewButton2.setBounds(69, 522, 262, 40);
        btnNewButton2.setFont(new Font("Nanum Gothic", Font.BOLD, 14));
        btnNewButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                RegistrationUI ur = new RegistrationUI();
                ur.setTitle("Register");
                ur.setVisible(true);
            }
        });

        contentPane.add(btnNewButton1);
        contentPane.add(btnNewButton2);

        label = new JLabel("");
        label.setBackground(new Color(221, 228, 232));
        label.setBounds(0, 0, 400, 562);
        contentPane.add(label);
        
        JLabel LoginLogo = new JLabel("");
        Image img = new ImageIcon(this.getClass().getResource("/loginLogo.png")).getImage();
        LoginLogo.setIcon(new ImageIcon(img));
        LoginLogo.setBounds(145, 85, 110, 110);
        contentPane.add(LoginLogo);
    }
    
  
}
