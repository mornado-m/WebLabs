package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.Vector;

@WebServlet(name = "mainServlet")
public class MainServlet extends HttpServlet {
    private String URL = "jdbc:mysql://localhost:3306/lab_2_db";
    private String USERNAME = "mornado";
    private String PASSWORD = "1224";
    private ResultSet rs;
    private Connection connection;
    private int ActiveTableIdx = 0;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String TableName = req.getParameter("LoadName");
        String Query = "INSERT INTO ";

        Vector<String> Tables = new Vector<String>();
        Vector<String> TableColumns = new Vector<String>();
        Vector<Vector<String>> TableRows = new Vector<Vector<String>>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Statement statement = connection.createStatement();
            rs = statement.executeQuery(
                    "SELECT table_name FROM information_schema.tables WHERE table_schema = 'lab_2_db'");
            while(rs.next()){
                Tables.add(rs.getString(1));
            }

            if(TableName == null) ActiveTableIdx = 0;
            else ActiveTableIdx = Tables.indexOf(TableName);

            Query += "" + Tables.get(ActiveTableIdx) + " (";

            rs = statement.executeQuery(
                    "SELECT COLUMN_NAME FROM information_schema.columns " +
                            "WHERE TABLE_SCHEMA='lab_2_db' AND TABLE_NAME ='"+ Tables.get(ActiveTableIdx) +"'");
            while(rs.next()){
                TableColumns.add(rs.getString(1));
                Query += rs.getString(1) + ",";
            }
            Query = Query.substring(0, Query.length() - 1);
            Query += ") VALUES ";


            String del = req.getParameter("DeleteAll");
            String rowDel = req.getParameter("DeleteIdx");
            if (rowDel != null && rowDel != ""){
                int deleteRow = Integer.parseInt(rowDel);
                String rowdata = "";
                rs = statement.executeQuery("SELECT * FROM " + Tables.get(ActiveTableIdx));
                for (int i = 0; rs.next(); i++){
                    if (i == deleteRow)
                        rowdata = rs.getString(1);
                }

                statement.executeUpdate("DELETE FROM " + Tables.get(ActiveTableIdx) +
                        " WHERE " + TableColumns.get(0) + "=" + rowdata);
            }


            if (del != null && del != ""){
                int deleteAll = Integer.parseInt(del);
                if (deleteAll == 1)
                    statement.executeUpdate("DELETE FROM " + Tables.get(ActiveTableIdx));
            }

            String params = "(";
            boolean HaveAllParams = true;
            for (int i = 0; i < TableColumns.size(); ++i){
                String str = req.getParameter("" + i);
                if (str == null || str == ""){
                    HaveAllParams = false;
                    break;
                }
                if (i == 0) params += "'" + str + "'";
                else params += ", '" + str + "'";
            }
            params += ")";

            Query += params;
            if (HaveAllParams){
                statement.executeUpdate(Query);
            }

            rs = statement.executeQuery("SELECT * FROM " + Tables.get(ActiveTableIdx));
            for (int i = 0; rs.next(); ++i){
                TableRows.add(new Vector<String>());
                for (int j = 1; j <= TableColumns.size(); ++j){
                    TableRows.get(i).add(rs.getString(j));
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        req.setAttribute("Tables", Tables);
        req.setAttribute("TableColumns", TableColumns);
        req.setAttribute("TableRows", TableRows);
        req.setAttribute("ActiveTableIdx", ActiveTableIdx);
        req.getRequestDispatcher("WEB-INF/servletPage.jsp").forward(req, resp);

    }
}
