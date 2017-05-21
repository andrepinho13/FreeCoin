import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.TimerTask;

/**
 * Created by andremigueldasilvapinho on 21-05-2017.
 */
public class Challenge extends TimerTask {
    public String bitNumber = null;
    public Socket connected = null;
    public Challenge(Socket connected){
        this.connected = connected;
    }

    public void run(){
        ServerThread.bitNumber = ServerThread.bitRandom();
        byte[] message = ServerThread.bitNumber.getBytes();
        DataOutputStream dataOutputStream = null;
        try {
            dataOutputStream = new DataOutputStream(connected.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dataOutputStream.writeInt(message.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dataOutputStream.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
