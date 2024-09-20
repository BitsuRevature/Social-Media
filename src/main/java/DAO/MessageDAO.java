package DAO;

import java.sql.*;

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
    
}
