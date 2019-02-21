package com.bws9000.blackjack.socketio;

import io.socket.client.Socket;

public interface SocketInterface {
    public Socket getSocket(String uri);
}
