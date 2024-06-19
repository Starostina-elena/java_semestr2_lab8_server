package org.lia.java_lab8_client_v2.commands;

import org.lia.java_lab8_client_v2.managers.CollectionManager;
import org.lia.java_lab8_client_v2.managers.CommandManager;
import org.lia.java_lab8_client_v2.managers.SqlManager;
import org.lia.java_lab8_client_v2.models.Product;
import org.lia.java_lab8_client_v2.tools.Response;

import java.sql.SQLException;

public class UpdateCommand implements Command {
    private static final long serialVersionUID = 1785464768755190753L;

    private CollectionManager collectionManager;
    private SqlManager sqlManager;
    private CommandManager commandManager;
    private Product product;
    private String login;
    private String password;
    long productId;

    public UpdateCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String description() {
        return "update product by it's id. Pattern: update (long)id";
    }

    public Response execute() {
        Response response = new Response();
        long userId = sqlManager.checkUser(login, password);
        Product oldProduct = null;
        for (Product c : collectionManager.getProductCollection()) {
            if (c.getId() == productId) {
                oldProduct = c;
                break;
            }
        }
        if (oldProduct == null | product == null) {
            response.addAnswer("no_such_element");
        } else if (oldProduct.getUserId() == userId) {
            try {
                product.setId(productId);
                product.setUserId(userId);
                product.setCreationDate(oldProduct.getCreationDate());
                sqlManager.updateElement(product);
                collectionManager.removeFromCollection(oldProduct);
                collectionManager.addToCollection(product);
                response.addAnswer("object_was_successfully_updated");
                response.setSuccess(true);

            } catch (SQLException e) {
                response.addAnswer(e.toString());
            }
        } else {
            response.addAnswer("access_denied_update");
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
