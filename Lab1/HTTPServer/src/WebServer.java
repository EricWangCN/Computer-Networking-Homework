import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;

public class WebServer {
    public static void main(String[] args) {
        int portNo = 1145;
        try {
            ServerSocket server = new ServerSocket(portNo);
            Socket client = null;
            System.out.println("ServerSocket: Port " + server.getLocalPort());
            System.out.println("Server Started.");
            while(true) {
                client = server.accept();
                System.out.println("连接建立");
                new ConnectionThread(client).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

class ConnectionThread extends Thread {
    Socket client;
    public ConnectionThread(Socket s) {
        client = s;
    }

    public String getFileName(String r) {
        System.out.println(System.getProperty("user.dir"));
        System.out.println("getFileName: " + r);
        String t = r.split(" ")[1];
        if(t.equals("/")) {
            return "index.html";
        } else {
            t = t.substring(1); // GET /PAGE.HTML HTTP/1.1 -> PAGE.HTML
            return t;
        }
    }

    public void send(PrintStream p, File f) {
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream(f));
            int contentLength = (int)f.length();
            byte buf[] = new byte[contentLength];
            dis.readFully(buf);
            p.write(buf, 0, contentLength);
            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            InetAddress clientAddress = client.getInetAddress();
            int clientPort = client.getPort();
            PrintStream socketStream = new PrintStream(client.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String request = br.readLine();
            System.out.println("Client Request: " + request);
            System.out.println("Client Port: " + clientPort);
            File f = new File(getFileName(request));
            System.out.println("Requested Page: " + getFileName(request));
            if(f.exists()) {
                socketStream.println("HTTP/1.0 200 OK");
                socketStream.println("MIME_version:1.0");
                socketStream.println("Content_Type:text/html;charset=utf-8");
                socketStream.println("Content_Length:" + f.length());
                socketStream.println("");

                send(socketStream, f);

            } else {
                socketStream.println("HTTP/1.0 404 Not Found");
                socketStream.println("");
            }
            socketStream.close();

        } catch (Exception e){

            // e.printStackTrace();
        }
    }
}