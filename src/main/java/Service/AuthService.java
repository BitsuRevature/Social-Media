package Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import DAO.AuthDAO;
import Model.Account;

public class AuthService {

    private AuthDAO authDAO;

    public AuthService(){
        authDAO = new AuthDAO();
    }

    public Account registerAccount(Account newAcc) {
        Account exists = authDAO.getAccountByUserName(newAcc.getUsername());
        if(exists != null){
            System.out.println("User Exitst");
            return null;
        }

       return authDAO.registerAccount(newAcc);
    }
    
}
