package info.bws9000.blackjack.socketio.blackjack;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.Socket;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GameSocket {

    private Socket io;
    private String DEV_PASS = "bws9000"; //merry christmas
    private List<GameSocketInterface> listeners = new ArrayList<GameSocketInterface>();
    private String socketId;

    public GameSocket(String uri) {

        SocketIO socketIO = new SocketIO();
        io = socketIO.getSocket(uri);
        io.connect();

        //socket EVENT_
        io.on(Socket.EVENT_RECONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                reConnect();
            }
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                disconnectEvent();
            }
        }).on(Socket.EVENT_CONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                JSONObject json = new JSONObject();
                try {
                    json.put("devuser", DEV_PASS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                io.emit("authentication", json);

                //hi connectAuthEvent()
                for (GameSocketInterface gsi : listeners)
                    gsi.connectAuthEvent();

            }

        }).on("authenticated", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                //
                Ack ack = new Ack() {
                    @Override
                    public void call(Object... objects) {
                        clientAuthorizedEvent();
                    }
                };
                ack.call(args);
            }
        })

        //other events
        .on("heartBeat", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Ack ack = new Ack() {
                    @Override
                    public void call(Object... objects) {
                        onHeartBeat(args[0].toString());
                    }
                };
                ack.call(args);
            }
        }).on("socketStateEmit", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Ack ack = new Ack() {
                    @Override
                    public void call(Object... objects) {
                        //callback.success(args[0].toString());
                        onGetSocketState(args[0].toString());
                    }
                };
                ack.call(args);
            }
        });
    }

    public void addListener(GameSocketInterface gsi) {
        listeners.add(gsi);
    }

    //EVENT_
    public void disconnectEvent() {
        //
        for (GameSocketInterface gsi : listeners)
            gsi.disconnectEvent();
    }

    public void reConnect(){
        try {
            JSONObject jsonObj = new JSONObject("{\"socketId\":" + "\"" + socketId + "\"}");
            io.emit("reconnection", jsonObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (GameSocketInterface gsi : listeners)
            gsi.reConnect();
    }

    //heartBeat
    public void onHeartBeat(String data){
        for (GameSocketInterface gsi : listeners)
            gsi.onHeartBeat(data);
    }
    public void emitHeartBeat(String param) {
        io.emit("bringThatBeatBack", param);
    }

    //socket state
    public void onGetSocketState(String data){
        //
        for (GameSocketInterface gsi : listeners)
            gsi.onGetSocketState(data);
    }
    public void emitSocketState(String param) {
        io.emit("socketState", param);
    }


    public void initGameSocket(String param, GameSocketCallback callback) {
        io.emit("init", param)
                .on("initEmit", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Ack ack = new Ack() {
                            @Override
                            public void call(Object... objects) {
                                try {
                                    JSONObject obj = new JSONObject(args[0].toString());
                                    socketId = obj.getString("socketId");
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                                callback.success(args[0].toString());
                            }
                        };
                        ack.call(args);
                    }
                });
    }

    public void createJoinTable(String param, GameSocketCallback callback) {
        io.emit("createTable", param)
                .on("createTableEmit", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Ack ack = new Ack() {
                            @Override
                            public void call(Object... objects) {
                                callback.success(args[0].toString());
                            }
                        };
                        ack.call(args);
                    }
                });
    }

    private void clientAuthorizedEvent() {
        //
        for (GameSocketInterface gsi : listeners)
            gsi.clientAuthorizedEvent();
    }


    public void connectAuthEvent() {
        //
        for (GameSocketInterface gsi : listeners)
            gsi.connectAuthEvent();
    }


    //test
    public static void main(String argv[]) {
        GameSocket gs = new GameSocket("http://localhost:3000");
        gs.connectAuthEvent();
    }
}