package org.lia.java_lab8_client_v2.commands;

import org.lia.java_lab8_client_v2.managers.CollectionManager;
import org.lia.java_lab8_client_v2.managers.CommandManager;
import org.lia.java_lab8_client_v2.managers.SqlManager;
import org.lia.java_lab8_client_v2.models.Product;
import org.lia.java_lab8_client_v2.tools.Response;

public class MinByIdCommand implements Command {
    private static final long serialVersionUID = 1785464768755190753L;

    private CollectionManager collectionManager;
    private SqlManager sqlManager;
    private CommandManager commandManager;
    private String login;
    private String password;
    public MinByIdCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String description() {
        return "shows element with min id";
    }

    public Response execute() {
        Response response = new Response();
        Product answer = collectionManager.getProductCollection().getFirst();
        for (Product c: collectionManager.getProductCollection()) {
            if (c.getId() < answer.getId()) {
                answer = c;
            }
        }
        response.addAnswer(answer.toString());
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
        return password;
    }
}
