package Service;

import java.util.List;

import DAO.AuthDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;

public class MessageService {

    private MessageDAO messageDAO;
    private AuthDAO authDAO;

    public MessageService(){
        this.messageDAO = new MessageDAO();
        this.authDAO = new AuthDAO();
    }

    public Message createMessage(Message msg) {

        Account postedBy = authDAO.getAccountById(msg.getPosted_by());
        if(postedBy == null) return null;

        return messageDAO.createMessage(msg);
        

    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }

    public Message deleteMessageById(int id) {
        Message exists = messageDAO.getMessageById(id);
        if(exists == null) return null;

        messageDAO.deleteMessageById(id);
        return exists;
    }

    public List<Message> getMessagesByUser(int id) {
        return messageDAO.getMessagesByUser(id);
    }
    
}
