package info.bws9000.blackjack.socketio.blackjack;

public interface GameSocketInterface {
    public void initGameSocket(String param, GameSocketCallback callback);
    public void test(String param, GameSocketCallback callback);
}