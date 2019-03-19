package info.bws9000.blackjack.socketio.blackjack;

public interface GameSocketInterface {
    void connectAuthEvent();
    void clientAuthorizedEvent();
    void reConnect();
    void onHeartBeat(String data);
    void onGetSocketState(String data);
    void disconnectEvent();
}