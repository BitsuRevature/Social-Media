package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Service.AuthService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    private AuthService authService;
    private ObjectMapper objectMapper;

    public SocialMediaController(){
        this.authService = new AuthService();
        objectMapper = new ObjectMapper();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {



        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        app.post("/register", this::registerUser);

        app.post("/login", this::login);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerUser(Context context) throws JsonMappingException, JsonProcessingException{


        Account newAcc = objectMapper.readValue(context.body(), Account.class);
        if(newAcc.getUsername().isBlank() || 
            newAcc.getUsername().isEmpty() ||
            newAcc.getPassword().length() < 4) {
            context.status(400);
            return;
        }
        Account createdAccount = authService.registerAccount(newAcc);

        if(createdAccount == null){
            context.status(400);
            return;
        }

        context.status(200);
        context.json(createdAccount);



    }

    private void login(Context context) throws JsonMappingException, JsonProcessingException{
        Account acc = objectMapper.readValue(context.body(), Account.class);

        Account existingAcc = authService.login(acc);

        if(existingAcc == null){
            context.status(401);
            return;
        }

        context.status(200);
        context.json(existingAcc);
    }


}