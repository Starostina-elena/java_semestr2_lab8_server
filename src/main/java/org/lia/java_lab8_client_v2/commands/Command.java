package org.lia.java_lab8_client_v2.commands;

import org.lia.java_lab8_client_v2.managers.CollectionManager;
import org.lia.java_lab8_client_v2.managers.CommandManager;
import org.lia.java_lab8_client_v2.managers.SqlManager;
import org.lia.java_lab8_client_v2.tools.Response;

import java.io.Serializable;

public interface Command extends Serializable {
    Response execute();
    void setCollectionManager(CollectionManager collectionManager);
    void setSqlManager(SqlManager sqlManager);
    void setCommandManager(CommandManager commandManager);
    String description();
    public String getLogin();
    public String getPassword();
}
