package com.bws9000.blackjack.socketio.blackjack;

import com.bws9000.blackjack.socketio.SocketIO;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.Socket;
import org.json.JSONObject;

public class GameSocket {

    private Socket io;
    private String DEV_PASS = "bws9000";

    public GameSocket(String uri){
        //init socket
        SocketIO socketIO = new SocketIO();
        this.io = socketIO.getSocket(uri);
        this.disconnectEvent();
        //connect
        this.io.connect();
    }

    public void someEvent() {
        System.out.println("some event called");
        this.io.emit("someevent", "{\"hi\":\"hi\"}");

        this.io.on("event", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                //
                Ack ack = new Ack() {
                    @Override
                    public void call(Object... objects) {
                        System.out.println("emit from server");
                    }
                };
                ack.call(args);
            }
        });
    }


    public void connectEvent(){
        this.io.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                JSONObject json = new JSONObject();
                try {
                    json.put("devuser", DEV_PASS);
                }catch(Exception e){
                    e.printStackTrace();
                }
                io.emit("authentication", json);
            }

        });

        this.io.on("authenticated", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                //
                Ack ack = new Ack() {
                    @Override
                    public void call(Object... objects) {
                        System.out.println("client authenticated Ack()");
                        authorizeClientAck("hi");
                    }
                };
                ack.call(args);
            }
        });
    }

    private void authorizeClientAck(String data){
        this.someEvent();
    }

    private void disconnectEvent(){
        this.io.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                System.out.println("client disconnected");
            }

        });
    }
}
