package com.bws9000.blackjack.socketio;


import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

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
