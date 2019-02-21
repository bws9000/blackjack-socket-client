package com.bws9000.blackjack.socketio;

import com.github.nkzawa.socketio.client.Socket;

public interface SocketInterface {
    public Socket getSocket(String uri);
}
