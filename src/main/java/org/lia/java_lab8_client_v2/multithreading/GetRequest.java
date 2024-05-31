package org.lia.java_lab8_client_v2.multithreading;

import org.lia.java_lab8_client_v2.commands.Command;
import org.lia.java_lab8_client_v2.managers.CollectionManager;
import org.lia.java_lab8_client_v2.managers.CommandManager;
import org.lia.java_lab8_client_v2.managers.SqlManager;
import org.lia.java_lab8_client_v2.tools.Response;

import java.io.*;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ExecutorService;

public class GetRequest implements Runnable {

    ByteBuffer buf;
    DatagramChannel dc;
    CollectionManager collectionManager;
    SqlManager sqlManager;
    CommandManager commandManager;
    Command command;
    Response response;
    WorkRequest working;
    SendRequest answer;
    SocketAddress address;
    ExecutorService executor;

    public GetRequest(ByteBuffer buf, DatagramChannel dc,
                      CollectionManager collectionManager, SqlManager sqlManager,
                      CommandManager commandManager, ExecutorService executor) {
        this.buf = buf;
        this.dc = dc;
        this.collectionManager = collectionManager;
        this.sqlManager = sqlManager;
        this.commandManager = commandManager;
        this.executor = executor;
    }

    @Override
    public void run() {
        try {
            address = dc.receive(buf);
            if (address != null) {
                buf.flip();
                ByteArrayInputStream in = new ByteArrayInputStream(buf.array());
                ObjectInputStream is = new ObjectInputStream(in);
                command = (Command) is.readObject();
                working = new WorkRequest(this);
                executor.submit(working);
            }
        } catch (StreamCorruptedException | UTFDataFormatException e) {
            System.out.println("Bad packet");
            buf.clear();
            response.addAnswer("Protocol error. Please try again");
            answer = new SendRequest(this);
            executor.submit(answer);
        } catch (ClassNotFoundException | IOException e) {
            System.out.println(e);
        }
    }

}
