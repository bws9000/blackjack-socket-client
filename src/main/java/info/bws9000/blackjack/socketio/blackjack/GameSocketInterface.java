package info.bws9000.blackjack.socketio.blackjack;

public interface GameSocketInterface {
    public void socketOnEmitEvent(String param, GameSocketCallback callback,String emitEvent, String onEvent);
}