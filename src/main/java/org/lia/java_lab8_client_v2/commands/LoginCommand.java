package org.lia.java_lab8_client_v2.commands;

import org.lia.java_lab8_client_v2.managers.CollectionManager;
import org.lia.java_lab8_client_v2.managers.CommandManager;
import org.lia.java_lab8_client_v2.managers.SqlManager;
import org.lia.java_lab8_client_v2.models.Product;
import org.lia.java_lab8_client_v2.tools.Response;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginCommand implements Command {

    private CollectionManager collectionManager;
    private SqlManager sqlManager;
    private CommandManager commandManager;
    public Product product;
    private String login;
    private String passwordString;
    private byte[] passwordByte;
    private String password;
    private static final long serialVersionUID = 1785464768755190753L;

    public String description() {
        return "logins a user";
    }

    public Response execute() {
        Response response = new Response();
        long id = sqlManager.checkUser(login, passwordString);
        response.setSuccess(id > 0);
        return response;
    }

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public void setSqlManager(SqlManager sqlManager) {
        this.sqlManager = sqlManager;
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return passwordString;
    }

}
