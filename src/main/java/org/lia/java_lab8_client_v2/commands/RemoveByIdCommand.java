package org.lia.java_lab8_client_v2.commands;

import org.lia.java_lab8_client_v2.managers.CollectionManager;
import org.lia.java_lab8_client_v2.managers.CommandManager;
import org.lia.java_lab8_client_v2.managers.SqlManager;
import org.lia.java_lab8_client_v2.models.Product;
import org.lia.java_lab8_client_v2.tools.Response;

public class RemoveByIdCommand implements Command {
    private static final long serialVersionUID = 1785464768755190753L;

    private CollectionManager collectionManager;
    private SqlManager sqlManager;
    private CommandManager commandManager;
    private long id;
    private String login;
    private String password;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String description() {
        return "removes element from collection by id";
    }

    public Response execute() {
        Response response = new Response();
        try {
            long userId = sqlManager.checkUser(login, password);
            int res = sqlManager.deleteElementById(id, userId);
            Product product = collectionManager.getById(id);
            if (res > 0) {
                collectionManager.removeFromCollection(product);
                response.addAnswer("Product_was_successfully_deleted");
                response.setSuccess(true);
            } else {
                response.addAnswer("access_denied_delete");
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            response.addAnswer("Incorrect number of arguments for update command. Please try again");
        } catch (NumberFormatException e) {
            response.addAnswer("id is not integer. Please try again");
        } catch (IllegalArgumentException e) {
            response.addAnswer(e + ". Please try again");
        }
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
