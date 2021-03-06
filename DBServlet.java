import javax.servlet.ServletException;
import javax.servlet.http.*;

import java.io.*;
import java.sql.*;
import java.util.*;

import java.io.IOException;
import java.io.File;
import java.net.URLClassLoader;
import java.net.URL;
import java.lang.reflect.Method;

public class DBServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>でーたべえすせつぞくてすと</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>It's been a while, JDBC!</h1>");

        out.println("<p>");
        useDatabase(out);
        out.println("</p>");

        out.println("</body>");
        out.println("</html>");

    }

    public void useDatabase(PrintWriter out) {
        Connection conn = null;
        String url = "jdbc:mysql://localhost/jdbctestdb";
        String user = "testuser";
        String password = "testpass";

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            out.println("ドライバのロードに成功しました<br>");

            conn = DriverManager.getConnection(url, user, password);
            out.println("データベース接続に成功しました<br>");
            out.println("<br>");
            out.println(" (⌒,_ゝ⌒)いくぜぇ、おらおらおら！！、手加減は無しだ･･･サイクロン！あばよクソ野郎が･･･、トルネードスラァッシュ！！<br><br>");

            Statement stmt = conn.createStatement();

            String sql="insert into kabukatable (code, company) values (?,?)";
            PreparedStatement ps= conn.prepareStatement(sql);
            ps.setInt(1,23984);
            ps.setString(2,"バケスロソーダは厨武器");
            ps.executeUpdate();
            out.println("preparedでinsert<br>");
            showData(out,stmt);

        } catch (ClassNotFoundException e) {
            out.println("ClassNotFoundException:" + e.getMessage());
        } catch (SQLException e) {
            out.println("SQLException:" + e.getMessage());
        } catch (Exception e) {
            out.println("Exception:" + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    out.println("データベース切断に成功しました");
                } else {
                    out.println("コネクションがありません");
                }
            } catch (SQLException e) {
                out.println("SQLException:" + e.getMessage());
            }
        }

    }

    public void showData(PrintWriter out, Statement stmt) throws SQLException {
        String sql = "SELECT * FROM kabukatable";
        ResultSet rs = stmt.executeQuery(sql);
        out.println("<p>");
        while (rs.next()) {
            out.println("code: " + rs.getInt("code")
                    + " company: " + rs.getString("company")
                    + "<br>");
        }
        out.println("</p>");
    }


}