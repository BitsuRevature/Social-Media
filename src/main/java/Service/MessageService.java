package Service;

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
    
}
