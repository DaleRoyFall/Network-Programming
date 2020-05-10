/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class ServerConnection extends Thread {

    private Thread thread;
    private Socket dataSocket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String name;
    private String userList;

    // Boolean variable to validate listen thread
    private boolean running = true;

    ServerConnection(Socket socket) {
        this.dataSocket = socket;
        this.start();
    }

    // Start thread
    @Override
    public void start() {
        if (thread == null) {
            thread = new Thread(this, "Client");
            thread.start();
        }
    }

    // Send message to all clients
    public void sendMessageToAllClients(String message) {
        for (int i = 0; i < ServerForm.serverConnection.size(); i++) {
            try {
                ServerConnection serverConnection = ServerForm.serverConnection.get(i);
                serverConnection.dataOutputStream.writeUTF(message);
                //serverConnection.dataOutputStream.flush();
            } catch (IOException ex) {
                Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void run() {
        try {
            // Create streams
            dataInputStream = new DataInputStream(dataSocket.getInputStream());
            dataOutputStream = new DataOutputStream(dataSocket.getOutputStream());

            // Listen and send message
            while (running) {
                // Read from input stream
                String message = dataInputStream.readUTF();
                // Verify message to command
                if (!message.isEmpty()) {
                    if (!isCommand(message)) {
                        ServerForm.addToLogPanel(name, message);

                        sendMessageToAllClients("    [" + name + "]:" + message);
                    } else {
                        sendMessageToAllClients(message);
                    }
                }
            }

            // Close socket and streams
            dataInputStream.close();
            dataOutputStream.close();
            dataSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Verify if message is command
    private boolean isCommand(String message) {
        if (message.startsWith("\\connect:")) {
            // Get user name
            String connectName = message.substring(message.indexOf(":") + 1);
            if (this.name == null) {
                this.name = connectName;
            }
            // Add name in user list
            addInUserList();

            ServerForm.addToLogPanel("Server", "User [" + connectName + "] connected.");

            return true;

        } else if (message.startsWith("\\disconnect:")) {
            // Get user name
            String disconnectName = message.substring(message.indexOf(":") + 1);
            // Disconnect user
            disconnect(disconnectName);
            // Delete name from user list
            deleteFromUserList(name);

            ServerForm.addToLogPanel("Server", "User [" + name + "] disconnected.");

            return true;
        }

        return false;
    }

    private void addInUserList() {
        userList = "";

        userList = userList.concat("\\userlist");
        for (int i = 0; i < ServerForm.serverConnection.size(); i++) {
            ServerConnection serverConnection = ServerForm.serverConnection.get(i);

            userList = userList.concat(":" + serverConnection.name);
        }

        sendMessageToAllClients(userList);
    }

    private void deleteFromUserList(String name) {
        userList = "";

        userList = userList.concat("\\userlist");
        for (int i = 0; i < ServerForm.serverConnection.size(); i++) {
            ServerConnection serverConnection = ServerForm.serverConnection.get(i);

            if (!serverConnection.name.equals(name)) {
                userList = userList.concat(":" + serverConnection.name);
            }
        }

        sendMessageToAllClients(userList);
    }

    // Disconnect user
    protected void disconnect(String disconnectName) {

        for (int i = 0; i < ServerForm.serverConnection.size(); i++) {
            ServerConnection serverConnection = ServerForm.serverConnection.get(i);

            if (serverConnection.name.equals(disconnectName)) {
                serverConnection.running = false;
                ServerForm.serverConnection.remove(i);
                break;
            }

        }
    }
}
