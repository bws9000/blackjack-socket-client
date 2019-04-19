package info.bws9000.blackjack.socketio.blackjack;
/**
 * Interface for Socket events and client.
 * @author Burt Wiley Snyder
 * @author bws9000.info
 * @version 0.3
 * @since 0.2
 */
public interface GameSocketInterface {
    void onJoinTable(String table);
    void onCreateTableAndJoined(String data);
    void connectAuthEvent();
    void reConnect();
    void onHeartBeat(String data);
    void onGetSocketState(String data);
    void onInitEmit(String data);
    void disconnectEvent();
    void clientAuthorizedEvent(String data);
}