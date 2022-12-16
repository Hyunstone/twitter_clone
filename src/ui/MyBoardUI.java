package ui;

import MVC.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MyBoardUI extends JFrame {

    String ID;
    private JPanel contentPane;
    private JTextArea writeArea;
    private JList list;
    private int articleNum;
    DefaultListModel listModel;

    public MyBoardUI(String ID) {
        this.ID = ID;
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500, 80, 400, 710);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 0));
        
        JButton btnNewButton = new JButton("로그아웃");
        btnNewButton.setForeground(new Color(220, 45, 59));
        btnNewButton.setBackground(UIManager.getColor("Button.disabledForeground"));
        btnNewButton.setFont(new Font("Nanum Gothic", Font.BOLD, 12));
        JFrame frame = new JFrame("Logout Window");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] options = {"Yes", "No"};
                int a = JOptionPane.showOptionDialog(frame, "Are you sure?", "Logout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (a == JOptionPane.YES_OPTION) {
                    dispose();
                    LoginUI obj = new LoginUI();
                    obj.setTitle("User-Login");
                    obj.setVisible(true);
                }
            }
        });

        btnNewButton.setBounds(285, 20, 100, 30);
        contentPane.add(btnNewButton);
        
        JButton button = new JButton("비밀번호 변경");
        button.setBackground(UIManager.getColor("Button.disabledForeground"));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ChangePasswordUI bo = new ChangePasswordUI(ID);
                bo.setTitle("Change Password");
                bo.setVisible(true);
            }
        });
        button.setFont(new Font("Nanum Gothic", Font.BOLD, 12));
        button.setBounds(285, 47, 100, 30);
        contentPane.add(button);

        Controller controller = new Controller();

        setContentPane(contentPane);
        contentPane.setLayout(null);

        writeArea = new JTextArea("");
        writeArea.setBounds(40, 95, 320, 73);
        writeArea.setBorder(BorderFactory.createCompoundBorder(
                writeArea.getBorder(), 
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        contentPane.add(writeArea);

        list = new JList();
        list.setBounds(40, 240, 320, 350);
        list.setForeground(new Color(254, 255, 255));
        list.setBackground(new Color(20, 20, 20));
        contentPane.add(list);

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if(!event.getValueIsAdjusting()) {
                    String postText = (String) list.getSelectedValue();
                    String[] strArr = postText.split(" ");
                    articleNum = Integer.parseInt(String.valueOf(strArr[0]));
                }
            }
        });
        
        ArrayList<Post> arr;
        arr = controller.readPost(ID);
        arr = controller.listSort(arr);

        if (arr.size() == 0 ) {
            listModel = new DefaultListModel();
            String info = ID + "님은 아직 트윗하지 않았습니다";
            listModel.addElement(info);
        }
        else {
            listModel = new DefaultListModel();
            for (Post res : arr) {
                String post = res.getNum() + "   " + "(" + res.getId() + ")" + " \t " + res.getArticle() + "\n";
                
                listModel.addElement(post);
            }
        }
        list.setModel(listModel);

        JButton articleWriteButton = new JButton("글 작성하기");
        articleWriteButton.setFont(new Font("Nanum Gothic", Font.BOLD, 12));
        articleWriteButton.setBounds(210, 180, 100, 30);
        contentPane.add(articleWriteButton);

        articleWriteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String article = writeArea.getText();
                int num = controller.getArticleNextNum();
                controller.insertPost(new Post(num, ID, article));
                ArrayList<Post> arr;
                arr = controller.readPost(ID);
                arr = controller.listSort(arr);
                writeArea.setText("");

                if (arr.size() == 0 ) {
                    listModel = new DefaultListModel();
                    String info = ID + "님은 아직 트윗하지 않았습니다";
                    listModel.addElement(info);
                }
                else {
                    listModel = new DefaultListModel();
                    for (Post res : arr) {
                        String post = res.getNum() + "   " + "(" + res.getId() + ")" + " \t " + res.getArticle() + "\n";
                        
                        listModel.addElement(post);
                    }
                }
                list.setModel(listModel);
            }
        });
        
        JButton articleReadButton = new JButton("새로고침");
        articleReadButton.setFont(new Font("Nanum Gothic", Font.BOLD, 12));
        articleReadButton.setBounds(100, 180, 90, 30);
        contentPane.add(articleReadButton);

        articleReadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Post> arr = controller.readPost(ID);
                arr = controller.listSort(arr);

                if (arr.size() == 0) {
                    listModel = new DefaultListModel();
                    String info = ID + "님은 아직 트윗하지 않았습니다";
                    listModel.addElement(info);
                } else {
                    listModel = new DefaultListModel();
                    for (Post res : arr) {
                        String post = res.getNum() + "   " + "(" + res.getId() + ")" + " \t " + res.getArticle() + "\n";
                        
                        listModel.addElement(post);
                    }
                }
                list.setModel(listModel);
            }
        });

        JButton articleDeleteButton = new JButton("글 삭제하기");
        articleDeleteButton.setFont(new Font("Nanum Gothic", Font.BOLD, 12));
        articleDeleteButton.setBounds(90, 630, 100, 30);
        contentPane.add(articleDeleteButton);

        articleDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.deletePost(articleNum);
                System.out.println(articleNum + "'s post deleted!");
            }
        });

        JButton articleUpdateButton = new JButton("글 수정하기");
        articleUpdateButton.setFont(new Font("Nanum Gothic", Font.BOLD, 12));
        articleUpdateButton.setBounds(210, 630, 100, 30);
        contentPane.add(articleUpdateButton);

        articleUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeArticleUI changePostUI = new ChangeArticleUI(articleNum);
                changePostUI.setTitle("Change Post");
                changePostUI.setVisible(true);
            }
        });
        
        JLabel MainLogo = new JLabel("");
        Image img = new ImageIcon(this.getClass().getResource("/mainLogo.png")).getImage();
        MainLogo.setIcon(new ImageIcon(img));
        MainLogo.setBounds(184, 25, 30, 30);
        contentPane.add(MainLogo);
        
        JButton BacktoMain = new JButton(new ImageIcon("./img/backArrow.png"));
        BacktoMain.setBounds(30, 25, 30, 30);
        contentPane.add(BacktoMain);
        BacktoMain.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// main으로 이동
            	dispose();
            	MainBoardUI main = new MainBoardUI(ID);
                main.setTitle("Twitter");
                main.setVisible(true);
            }
        });
    }
}
