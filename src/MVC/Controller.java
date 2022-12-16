package MVC;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Controller {
    Connection connect = null;
    ResultSet rs = null;
    Statement st = null;

    public Controller() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/twit","root",
                    "1234");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer getArticleNextNum() {
        int articleNum = -1;
        try {
            Statement st_num = connect.createStatement();
            rs = st_num.executeQuery("select max(num) from article;");
            while (rs.next()) {
            articleNum = Integer.parseInt(rs.getString(1));
            articleNum++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return articleNum;
    }

    // 게시물 추가
    public void insertPost(Post post) {
        try {
            st = connect.createStatement();
            st.executeUpdate(
                    "insert into article values ('"+ post.num +"', '" + post.id + "', '" + post.article + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 게시물 목록 출력
    public ArrayList<Post> readPost(String id) {
        ArrayList<Post> arr = new ArrayList<>();
        try {
            st = connect.createStatement();
            rs = st.executeQuery("select * from article where ID = '" + id + "';");
            while (rs.next()) {
                arr.add(new Post(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return arr;
    }

    // 게시물 삭제
    public void deletePost(int postNum) {
        try {
            st = connect.createStatement();
            int stmt = st.executeUpdate("delete from article where num = '" + postNum + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> getFollowing(String ID) {
        ArrayList<String> arr = new ArrayList<>();
        try {
            st = connect.createStatement();
            rs = st.executeQuery("select toUser from follow where fromUser = '" + ID + "';");
            while (rs.next()) {
                arr.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return arr;
    }

    public ArrayList<String> getFollower(String ID) {
        ArrayList<String> arr = new ArrayList<>();
        try {
            st = connect.createStatement();
            rs = st.executeQuery("select fromUser from follow where toUser = '" + ID + "';");
            while (rs.next()) {
                arr.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return arr;
    }

    public ArrayList<String> getAllUser(String myID) {
        ArrayList<String> arr = new ArrayList<>();
        System.out.println(arr);
        try {
            st = connect.createStatement();
            rs = st.executeQuery("select id from account where id <> '" + myID + "';");
            while (rs.next()) {
                arr.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return arr;
    }

    public String setFollowButton(String Myid, String userId) {
        // 팔로우가 되어 있지 않아야 팔로우라고 보여야 함
        // 팔로우 안 되어있는 상태
        if (!checkFollow(Myid, userId)) {
            return "follow";
        }
        return "unfollow";
    }

    public Boolean checkFollow(String Myid, String userId) {
        boolean result = false;
        int res_num = -1;
        try {
            st = connect.createStatement();
            rs = st.executeQuery(
                    "select exists ( select 1 from follow where fromUser = '" + Myid + "'" +
                            " and toUser = '" + userId + "') as cnt;");
            while (rs.next()) {
                 res_num = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (res_num == 1) {
            result = true;
        }
        return result;
    }

    public void addFollow(String Myid, String userId) {
        try {
            st = connect.createStatement();
            st.executeUpdate("insert into follow values ('"+ Myid +"', '"+ userId +"');");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteFollow(String Myid, String userId) {
        try {
            st = connect.createStatement();
            st.executeUpdate("delete from follow where fromUser = '"+ Myid +"'" +
                    " and toUser = '"+ userId +"';");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateFollow(String Myid, String userId) {
        // 팔로우 되어있을 때 언팔로우 버튼 누르는 경우
        if (checkFollow(Myid, userId)) {
            deleteFollow(Myid, userId);
            return;
        }

        addFollow(Myid, userId);
    }

    class PostNumComparator implements Comparator<Post> {
        @Override
        public int compare(Post o1, Post o2) {
            return o2.getNum() - o1.getNum();
        }
    }

    public ArrayList<Post> listSort(ArrayList<Post> list) {
        list.sort(new PostNumComparator());
        return list;
    }
}