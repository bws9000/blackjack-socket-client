package info.bws9000.blackjack.socketio.blackjack;

public interface GameSocketInterface {
    void connectAuthEvent();
    void clientAuthorizedEvent();
    void reConnect();
    void connectError();
    void timeOut();
    void disconnectEvent();
}