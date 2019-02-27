package info.bws9000.blackjack.socketio.blackjack;

public interface GameSocketCallback {
    void success(String data);
    void error(String err);
}
