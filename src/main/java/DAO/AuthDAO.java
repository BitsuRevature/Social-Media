package DAO;

import java.sql.*;


import Model.Account;
import Util.ConnectionUtil;

public class AuthDAO {

    public Account registerAccount(Account newAcc) {
        var conn = ConnectionUtil.getConnection();

        try{

            String sql = "INSERT INTO Account (username, password) VALUES (?,?);";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, newAcc.getUsername());
            ps.setString(2, newAcc.getPassword());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int generatedId = (int) rs.getLong(1);
                return new Account(generatedId, newAcc.getUsername(), newAcc.getPassword());
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountByUserName(String userName){
        var conn = ConnectionUtil.getConnection();

        try{

            String sql = "SELECT * FROM Account WHERE username=?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, userName);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Account acc = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                    );
                return acc;
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account login(Account acc) {
        var conn = ConnectionUtil.getConnection();

        try{
            String sql = "SELECT * FROM Account WHERE username = ? AND password = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, acc.getUsername());
            ps.setString(2, acc.getPassword());

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Account found = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
                return found;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountById(int posted_by) {
        var conn = ConnectionUtil.getConnection();

        try{
            String sql = "SELECT * FROM Account WHERE account_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, posted_by);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Account found = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
                return found;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}

