package info.bws9000.blackjack.socketio.blackjack;

import info.bws9000.blackjack.socketio.SocketIO;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.Socket;
import org.json.JSONObject;

public class GameSocket{

    private Socket io;
    private String DEV_PASS = "bws9000";

    public GameSocket(String uri) {

        //init socket
        SocketIO socketIO = new SocketIO();
        io = socketIO.getSocket(uri);

        //add events
        this.disconnectEvent();
        this.connectError();
        this.timeOut();
        this.reConnect();
        //connect
        io.connect();
    }


    /////////////////////////////////connect
    private void authorizeClientAck() {
        //
    }

    public void reConnect() {
        io.on(Socket.EVENT_RECONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                System.out.println(" *** connection reconnected ***");
                System.exit(0);
            }
        });
    }

    public void connectError() {
        io.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                System.out.println(" *** connection error ***");
                System.exit(0);
            }
        });

    }

    public void timeOut() {
        io.on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                System.out.println(" *** connection timed out ***");
                System.exit(0);
            }
        });

    }

    public void connectEvent() {
        io.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                JSONObject json = new JSONObject();
                try {
                    json.put("devuser", DEV_PASS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                io.emit("authentication", json);
            }

        });

        io.on("authenticated", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                //
                Ack ack = new Ack() {
                    @Override
                    public void call(Object... objects) {
                        authorizeClientAck();
                    }
                };
                ack.call(args);
            }
        });
    }

    private void disconnectEvent() {
        io.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                System.out.println("client disconnected");
            }
        });
    }

    //////////////////////////////////game
    public void initGame(String param, SocketCallback callback) {
        io.emit("init", "{\"hi\":\"hi\"}");
        io.on("initevent", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Ack ack = new Ack() {
                    @Override
                    public void call(Object... objects) {
                        //System.out.println("> :"+args[0]);
                        callback.success(args[0].toString());
                    }
                };
                ack.call(args);
            }
        });
    }

    //test
    public static void main(String argv[]){
        GameSocket gs = new GameSocket("");
        gs.connectEvent();
    }




}
