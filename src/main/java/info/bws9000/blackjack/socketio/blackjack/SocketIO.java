package info.bws9000.blackjack.socketio.blackjack;
/**
 * Initialize a Socket.
 * @author Burt Wiley Snyder
 * @author bws9000.info
 * @version 0.3
 * @since 0.2
 */


import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

public class SocketIO {

    Socket socket;

    public SocketIO(){ }

    /**
     * Initializes a scket.
     * @param uri uri to connect.
     * @return Socket instance.
     */
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
