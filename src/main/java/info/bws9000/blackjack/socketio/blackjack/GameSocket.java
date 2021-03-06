package info.bws9000.blackjack.socketio.blackjack;
/**
 * Socketio client for node game server.
 *
 * @author Burt Wiley Snyder
 * @author bws9000.info
 * @version 0.3
 * @since 0.2
 */

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.Socket;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GameSocket {

    private Socket io;
    private String DEV_PASS = "bws9000";
    private List<GameSocketInterface> listeners = new ArrayList<GameSocketInterface>();

    private String socketId;


    /** Initializing GameSocket EVENTS
     * @param uri The socket uri.
     */
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

            }

        }).on("authenticated", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                //
                Ack ack = new Ack() {
                    @Override
                    public void call(Object... objects) {
                        clientAuthorizedEvent("");
                    }
                };
                ack.call(args);
            }
        })

                //other events
                .on("initEmit", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Ack ack = new Ack() {
                            @Override
                            public void call(Object... objects) {
                                try {
                                    onInitEmit(args[0].toString());//start game
                                    JSONObject obj = new JSONObject(args[0].toString());
                                    socketId = obj.getString("socketId");

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        };
                        ack.call(args);
                    }
                })
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
                })
                .on("socketStateEmit", new Emitter.Listener() {
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
                })
                .on("createTableEmit", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Ack ack = new Ack() {
                        @Override
                        public void call(Object... objects) {
                            onCreateTable(args[0].toString());
                        }
                    };
                    ack.call(args);
                }
        })
                .on("emitJoinTable", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Ack ack = new Ack() {
                            @Override
                            public void call(Object... objects) {
                                onJoinTable(args[0].toString());
                            }
                        };
                        ack.call(args);
                    }
                    })


                .on("emitListTables", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Ack ack = new Ack() {
                            @Override
                            public void call(Object... objects) {
                                onListTables(args[0].toString());
                            }
                        };
                        ack.call(args);
                    }
        });

    }

    /**
     * Add a listener for GameSocketInterface
     * @param gsi
     */
    public void addListener(GameSocketInterface gsi) {
        listeners.add(gsi);
    }


    /**
     * Emit to server init event
     * @param param empty
     */
    public void initGameSocket(String param) {
        io.emit("init", param);
    }

    /**
     * Server emit from "init" Event
     * @param data Socket data from server
     */
    public void onInitEmit(String data) {
        //
        for (GameSocketInterface gsi : listeners)
            gsi.onInitEmit(data);
    }

    public void emitCreateTableAndJoin(String data){
        io.emit("createTable", data);
    }

    public void onCreateTable(String data){
        //
        for (GameSocketInterface gsi : listeners)
            gsi.onCreateTableAndJoined(data);
    }


    /**
     * Emit to server to join a table "room"
     * @param table name
     */
    public void emitJoinTable(String table){
        io.emit("emitJoinTable",table);
    }

    /**
     * Emit to server to get initiate
     * return of active tables
     * @param param empty
     */
    public void emitGetActiveTables(){
        io.emit("emitGetActiveTables");
    }

    /**
     * return of active tables
     * @param String tables
     */
    public void onListTables(String tables){
        for (GameSocketInterface gsi : listeners)
            gsi.onListTables(tables);
    }

    public void onJoinTable(String data){
        for (GameSocketInterface gsi : listeners)
            gsi.onJoinTable(data);
    }

    //heartBeat
    public void onHeartBeat(String data) {
        for (GameSocketInterface gsi : listeners)
            gsi.onHeartBeat(data);
    }

    public void emitHeartBeat(String param) {
        io.emit("bringThatBeatBack", param);
    }

    //socket state
    public void emitSocketState(String param) {
        io.emit("socketState", param);
    }

    public void onGetSocketState(String data) {
        //
        for (GameSocketInterface gsi : listeners)
            gsi.onGetSocketState(data);
    }


    /*
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
    }*/


    //EVENT_
    public void disconnectEvent() {
        //
        for (GameSocketInterface gsi : listeners)
            gsi.disconnectEvent();
    }

    public void reConnect() {
        try {
            JSONObject jsonObj = new JSONObject("{\"socketId\":" + "\"" + socketId + "\"}");
            io.emit("reconnection", jsonObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (GameSocketInterface gsi : listeners)
            gsi.reConnect();
    }


    public void connectAuthEvent() {
        //authorizing...
        for (GameSocketInterface gsi : listeners)
            gsi.connectAuthEvent();
    }


    //called on "authenticated"
    private void clientAuthorizedEvent(String data) {
        //authorized...
        //no params sent yet...
        //start game
        initGameSocket(""); //!data

        for (GameSocketInterface gsi : listeners)
            gsi.clientAuthorizedEvent(data);
    }


    //test
    public static void main(String argv[]) {
        GameSocket gs = new GameSocket("http://localhost:3000");
        //gs.connectAuthEvent();
    }
}