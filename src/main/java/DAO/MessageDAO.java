package DAO;

import java.sql.*;
import java.util.*;


import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

    public Message createMessage(Message msg) {
        var conn = ConnectionUtil.getConnection();

        try{

            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?);";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, msg.getPosted_by());
            ps.setString(2, msg.getMessage_text());
            ps.setLong(3, msg.getTime_posted_epoch());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int generatedId = (int) rs.getLong(1);
                return new Message(generatedId, msg.getPosted_by(), msg.getMessage_text(), msg.getTime_posted_epoch());
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessages() {
        var conn = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{

            String sql = "SELECT * FROM Message;";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Message msg = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
                messages.add(msg);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessageById(int id) {
        var conn = ConnectionUtil.getConnection();
        try{

            String sql = "SELECT * FROM Message WHERE message_id = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Message msg = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
                return msg;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void deleteMessageById(int id) {
        var conn = ConnectionUtil.getConnection();
        try{

            String sql = "DELETE * FROM Message WHERE message_id = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public List<Message> getMessagesByUser(int id) {
        var conn = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{

            String sql = "SELECT * FROM Message WHERE posted_by = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Message msg = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
                messages.add(msg);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public int updateMessage(int id, Message update) {
        var conn = ConnectionUtil.getConnection();
        int result = 0;
        try{

            String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, update.getMessage_text());
            ps.setInt(2, id);

            result = ps.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return result;
    }
    
}
