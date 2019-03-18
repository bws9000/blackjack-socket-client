package info.bws9000.blackjack.socketio.blackjack;


import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

public class SocketIO {

    Socket socket;

    public SocketIO(){ }

    public Socket getSocket(String uri){
        try {
            //IO.Options opts = new IO.Options();
            //opts.forceNew = false;
            //opts.reconnection = true;
            //opts.reconnectionDelay = 500;
            //opts.reconnectionDelayMax = 60000;
            //socket = IO.socket(uri,opts);
            socket = IO.socket(uri);
        }catch(Exception e){
            e.printStackTrace();
        }
        return socket;
    }

}
