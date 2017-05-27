import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by andremigueldasilvapinho on 21-05-2017.
 */

public class Client {
    public final static int SOCKET_PORT = 13267;
    public final static String SERVER = "127.0.0.1";
    public final static String FILE_TO_SEND = "src/text.txt";

    public static void solveChallenge(int binary, Socket socket){
        try {
            File myFile = new File(FILE_TO_SEND);
            byte[] mybytearray = new byte[(int)myFile.length()];
            FileInputStream fileInputStream = new FileInputStream(myFile);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            bufferedInputStream.read(mybytearray,0,mybytearray.length);
            OutputStream outputStream = socket.getOutputStream();
            System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
            outputStream.write(mybytearray,0,mybytearray.length);
            outputStream.flush();
            System.out.println("Done.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Socket socket = null;
        try {
            socket = new Socket(SERVER,SOCKET_PORT);
            System.out.println("Connecting...");
            while (true){
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                int length = dataInputStream.readInt();
                if (length>0) {
                    byte[] message = new byte[length];
                    dataInputStream.readFully(message, 0, message.length);
                    String s = new String(message,"US-ASCII");
                    System.out.println(s);
                    solveChallenge(Integer.parseInt(s),socket);
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}