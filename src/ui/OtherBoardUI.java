package ui;

import MVC.Controller;
import MVC.Post;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// 각각의 유저들, 나ui랑 똑같은 것
public class OtherBoardUI extends JFrame {

    String otherUserID;
    String userID;
    private JPanel contentPane;
    private DefaultListModel listModel;
    private JList list;
    private JTextArea writeArea;

    public OtherBoardUI(String otherUserID, String userID) {
        this.otherUserID = otherUserID;
        this.userID = userID;

        setSize(500,800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500, 80, 400, 710);
        
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 0));

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

        JButton articleWriteButton = new JButton("글 작성하기");
        articleWriteButton.setFont(new Font("Nanum Gothic", Font.BOLD, 12));
        articleWriteButton.setBounds(210, 180, 100, 30);
        contentPane.add(articleWriteButton);

        articleWriteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String article = writeArea.getText();
                int num = controller.getArticleNextNum();
                controller.insertPost(new Post(num, otherUserID, article));
                ArrayList<Post> arr = controller.readPost(otherUserID);
                arr = controller.listSort(arr);
                writeArea.setText("");

                if (arr.size() == 0) {
                    listModel = new DefaultListModel();
                    String info = otherUserID + "님은 아직 트윗하지 않았습니다";
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
        
        ArrayList<Post> arr = controller.readPost(otherUserID);
        arr = controller.listSort(arr);

        if (arr.size() == 0) {
            listModel = new DefaultListModel();
            String info = otherUserID + "님은 아직 트윗하지 않았습니다";
            listModel.addElement(info);
        } else {
            listModel = new DefaultListModel();
            for (Post res : arr) {
                String post = res.getNum() + "   " + "(" + res.getId() + ")" + " \t " + res.getArticle() + "\n";
                listModel.addElement(post);
            }
        }
        list.setModel(listModel);

        JButton articleReadButton = new JButton("새로고침");
        articleReadButton.setFont(new Font("Nanum Gothic", Font.BOLD, 12));
        articleReadButton.setBounds(100, 180, 90, 30);
        contentPane.add(articleReadButton);

        articleReadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Post> arr = controller.readPost(otherUserID);
                arr = controller.listSort(arr);

                if (arr.size() == 0) {
                    listModel = new DefaultListModel();
                    String info = otherUserID + "님은 아직 트윗하지 않았습니다";
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
               MainBoardUI main = new MainBoardUI(userID);
                main.setTitle("Twitter");
                main.setVisible(true);
            }
        });
    }
}