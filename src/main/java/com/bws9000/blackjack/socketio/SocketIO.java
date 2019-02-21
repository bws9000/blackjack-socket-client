package com.bws9000.blackjack.socketio;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SocketIO implements SocketInterface {

    Socket socket;

    public SocketIO(){ }

    public Socket getSocket(String uri){
        try {
            socket = IO.socket(uri + "/blackjack");
        }catch(Exception e){
            e.printStackTrace();
        }
        return socket;
    }

}
