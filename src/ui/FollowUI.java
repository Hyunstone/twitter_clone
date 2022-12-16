package ui;

import MVC.Controller;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FollowUI extends JFrame {

    String id;

    FollowUI(String id) {
        this.id = id;
        Controller controller = new Controller();

        setBounds(500, 120, 400, 300);
        setTitle("팔로워 / 팔로잉 목록");
        getContentPane().setLayout(new BorderLayout());
        setResizable(false);

        JPanel jp1 = new JPanel(); // 나의 팔로워, 팔로잉 버튼 있는곳.
        jp1.setBackground(new Color(0, 0, 0));
        jp1.setLayout(new FlowLayout(FlowLayout.LEFT)); // jp1은 나의 이름, 팔로워 팔로잉이 나오게 함.
        JLabel jl1 = new JLabel(id);
        jl1.setForeground(new Color(254, 255, 255));
        JButton followingButton = new JButton("팔로잉");
        followingButton.setFont(new Font("Nanum Gothic", Font.BOLD, 12));
        followingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> followerList = controller.getFollowing(id);

                JFrame followListFrame = new JFrame();
                JPanel jpMain = new JPanel();
                jpMain.setLayout(new BorderLayout());
                JPanel jp = new JPanel();

                followListFrame.setTitle("내 팔로잉 목록");
                followListFrame.setSize(300, 300);
                followListFrame.setVisible(true);
                jp.setLayout(new GridLayout(followerList.size(), 2, 10, 10));

                JLabel[] followUserId = new JLabel[1000];
                JButton[] followerBoardButton = new JButton[1000];

                for (int i = 0; i < followerList.size(); i++) {
                    String followerId = followerList.get(i);
                    followUserId[i] = new JLabel(followerId);
                    followerBoardButton[i] = new JButton(followerId + "의 피드");
                    followerBoardButton[i].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                           dispose();
                            System.out.println(followerId + "'s Board!");
                            OtherBoardUI otherBoardUI = new OtherBoardUI(followerId, id);
                            otherBoardUI.setVisible(true);
                        }
                    });

                    jp.add(followUserId[i], i - 1, 0);
                    jp.add(followerBoardButton[i], i - 1, 1);
                }
                jpMain.add(jp, BorderLayout.NORTH);
                followListFrame.getContentPane().add(new JScrollPane(jpMain), BorderLayout.CENTER);
            }
        });

        JButton followerButton = new JButton("팔로워");
        followerButton.setFont(new Font("Nanum Gothic", Font.BOLD, 12));
        followerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> followerList = controller.getFollower(id);

                JFrame followListFrame = new JFrame();
                JPanel jpMain = new JPanel();
                jpMain.setLayout(new BorderLayout());
                JPanel jp = new JPanel();

                followListFrame.setTitle("내 팔로워 목록");
                followListFrame.setSize(300, 300);
                followListFrame.setVisible(true);
                jp.setLayout(new GridLayout(followerList.size(), 2, 10, 10));

                JLabel[] followUserId = new JLabel[1000];
                JButton[] followerBoardButton = new JButton[1000];

                for (int i = 0; i < followerList.size(); i++) {
                    String followerId = followerList.get(i);
                    followUserId[i] = new JLabel(followerId);
                    followerBoardButton[i] = new JButton(followerId + "의 피드");
                    followerBoardButton[i].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                           dispose();
                            System.out.println(followerId + "'s Board!");
                            OtherBoardUI otherBoardUI = new OtherBoardUI(followerId, id);
                            otherBoardUI.setVisible(true);
                        }
                    });

                    jp.add(followUserId[i], i - 1, 0);
                    jp.add(followerBoardButton[i], i - 1, 1);
                }
                jpMain.add(jp, BorderLayout.NORTH);
                followListFrame.getContentPane().add(new JScrollPane(jpMain), BorderLayout.CENTER);
            }
        });

        jp1.add(jl1);
        jp1.add(followingButton);
        jp1.add(followerButton);

        JPanel jpMain = new JPanel();
        jpMain.setLayout(new BorderLayout());

        ArrayList<String> allUserList = controller.getAllUser(id);

        JLabel[] userId = new JLabel[1000]; // 유저 이름왼쪽에 뜨게하는 라벨
        JButton[] userBoardButton = new JButton[1000]; // 유저 정보뜨게 하는 버튼
        JButton[] followButton = new JButton[1000]; // follow버튼


        JPanel jp2 = new JPanel();
        jp2.setLayout(new GridLayout(allUserList.size(), 3, 10, 10));

        for (int i = 0; i < allUserList.size(); i++) {
            String userid = allUserList.get(i);
            String followState = controller.setFollowButton(id, userid);
            userId[i] = new JLabel("         " + userid);
            userBoardButton[i] = new JButton( userid + "의 피드");
            userBoardButton[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   dispose();
                    System.out.println(userid + "'s Board!");
                    OtherBoardUI otherBoardUI = new OtherBoardUI(userid, id);
                    otherBoardUI.setVisible(true);
                }
            });

            followButton[i] = new JButton(followState);
            int finalI = i;
            followButton[i].addActionListener(new ActionListener() {
                int num = finalI;
                @Override
                public void actionPerformed(ActionEvent e) {
                    String followState = controller.setFollowButton(id, userid);

                    if (followState.equals("follow")) {
                        controller.updateFollow(id, userid);
                        followButton[num].setText("unfollow");
                    }
                    else if (followState.equals("unfollow")) {
                        controller.updateFollow(id, userid);
                        followButton[num].setText("follow");
                    }
                }
            });

            jp2.add(userId[i], i - 1, 0);
            jp2.add(userBoardButton[i], i - 1, 1);
            jp2.add(followButton[i], i - 1, 2);
        }

        getContentPane().add(jp1, BorderLayout.NORTH);
        jpMain.add(jp2, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(jpMain), BorderLayout.CENTER);
        setVisible(true);
    }
}