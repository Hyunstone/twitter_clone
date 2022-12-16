package ui;

import MVC.Controller;
import MVC.Post;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainBoardUI extends JFrame {

    String ID;
    private JPanel contentPane;
    private DefaultListModel listModel;
    private JList list;
    private JButton articleReadButton;
    private final JButton lowBarFollowingBoardButton;
    private final JButton lowBarFollowButton;
    private final JButton lowBarHisBoard;

    public MainBoardUI(String ID) {
        this.ID = ID;
        Controller controller = new Controller();
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500, 80, 400, 710);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 0));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        list = new JList();
        list.setBounds(40, 200, 320, 400);
        list.setBackground(new Color(20, 20, 20));
        list.setForeground(new Color(254, 255, 255));
        contentPane.add(list);

        articleReadButton = new JButton(new ImageIcon("./img/refreshPage.png"));
        articleReadButton.setBorderPainted(false);
        articleReadButton.setOpaque(false);
        articleReadButton.setBounds(310, 120, 50, 50);
        contentPane.add(articleReadButton);
        
        ArrayList<Post> arr = controller.readPost(ID);
        ArrayList<String> followArr = controller.getFollowing(ID);
        for (String s : followArr) {
            ArrayList<Post> tempPost = controller.readPost(s);
            arr.addAll(tempPost);
        }
        arr = controller.listSort(arr);

        if (arr.size() == 0) {
            listModel = new DefaultListModel();
            String info = "불러올 글이 없습니다";
            listModel.addElement(info);
        } else {
            listModel = new DefaultListModel();
            for (Post res : arr) {
                String post = res.getNum() + "   " + "(" + res.getId() + ")" + " \t " + res.getArticle() + "\n";
                
                listModel.addElement(post);
            }
        }
        list.setModel(listModel);

        articleReadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Post> arr = controller.readPost(ID);
                ArrayList<String> followArr = controller.getFollowing(ID);
                for (String s : followArr) {
                    ArrayList<Post> tempPost = controller.readPost(s);
                    arr.addAll(tempPost);
                }
                arr = controller.listSort(arr);

                if (arr.size() == 0) {
                    listModel = new DefaultListModel();
                    String info = "불러올 글이 없습니다";
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
        

        lowBarFollowingBoardButton = new JButton(new ImageIcon("./img/home.png"));
        lowBarFollowingBoardButton.setBounds(40, 620, 30, 30);
        lowBarFollowingBoardButton.setBorderPainted(false);
        lowBarFollowingBoardButton.setOpaque(false);
        lowBarFollowingBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("MainBoard!");
            }
        });

        lowBarFollowButton = new JButton(new ImageIcon("./img/followers.png"));
        lowBarFollowButton.setBounds(184,620, 30, 30);
        lowBarFollowButton.setBorderPainted(false);
        lowBarFollowButton.setOpaque(false);
        lowBarFollowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 팔로잉 보드로 넘어가기
                System.out.println("Follow!");
                FollowUI followUI = new FollowUI(ID);
                followUI.setVisible(true);
            }
        });

        lowBarHisBoard = new JButton(new ImageIcon("./img/myhome.png"));
        lowBarHisBoard.setBounds(330,620, 30, 30);
        lowBarHisBoard.setBorderPainted(false);
        lowBarHisBoard.setOpaque(false);
        lowBarHisBoard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // myboard로 이동
            	dispose();
                System.out.println("Your Board!");
                MyBoardUI myBoardUI = new MyBoardUI(ID);
                myBoardUI.setVisible(true);
            }
        });

        contentPane.add(lowBarFollowingBoardButton);
        contentPane.add(lowBarFollowButton);
        contentPane.add(lowBarHisBoard);
        
        JLabel MainLogo = new JLabel("");
        Image img = new ImageIcon(this.getClass().getResource("/mainLogo.png")).getImage();
        MainLogo.setIcon(new ImageIcon(img));
        MainLogo.setBounds(184, 25, 30, 30);
        contentPane.add(MainLogo);
    }
}
