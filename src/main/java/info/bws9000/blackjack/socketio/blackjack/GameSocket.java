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
        this.disconnectEvent();
        //connect
        io.connect();
    }


    /////////////////////////////////connect
    private void authorizeClientAck() {
        //
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
                        System.out.println("client authenticated Ack()");
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
        io.emit("someevent", "{\"hi\":\"hi\"}");
        io.on("event", new Emitter.Listener() {

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
