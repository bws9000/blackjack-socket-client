package info.bws9000.blackjack.socketio.blackjack;

public interface SocketCallback {
        void success(String data);
        void error(String err);
}
