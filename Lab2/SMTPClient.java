import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class SMTPClient {

    public static void main(String[] args) {
        /*
         *用户名和密码
         */
        String SendUser="1204216974@qq.com";
        String SendPassword="fpumxeyzxyjygjid";
        String ReceiveUser="1204216974@qq.com";

        /*
         *对用户名和密码进行Base64编码
         */
        //BASE64Encoder s= new BASE64Encoder();
        String UserBase64="MTIwNDIxNjk3NEBxcS5jb20=";// s.encode(SendUser.getBytes());
        String PasswordBase64="ZnB1bXhleXp4eWp5Z2ppZA==";//s.encode(SendPassword.getBytes());

        try {
            /*
             *远程连接smtp.qq.com服务器的25号端口
             *并定义输入流和输出流(输入流读取服务器返回的信息、输出流向服务器发送相应的信息)
             */
            Socket socket=new Socket("smtp.qq.com", 25);
            InputStream inputStream=socket.getInputStream();//读取服务器返回信息的流
            InputStreamReader isr=new InputStreamReader(inputStream);//字节解码为字符
            BufferedReader br=new BufferedReader(isr);//字符缓冲

            OutputStream outputStream=socket.getOutputStream();//向服务器发送相应信息
            PrintWriter pw=new PrintWriter(outputStream, true);//true代表自带flush
            System.out.println(br.readLine());

            /*
             *向服务器发送信息以及返回其相应结果
             */

            //helo
            pw.println("helo 2020 COMPUTER NETWORKING");
            System.out.println(br.readLine());

            //auth login
            pw.println("auth login");
            System.out.println(br.readLine());
            pw.println(UserBase64);
            System.out.println(br.readLine());
            pw.println(PasswordBase64);
            System.out.println(br.readLine());



            //Set "mail from" and  "rect to"
            pw.println("mail from:<"+SendUser+">");
            System.out.println(br.readLine());
            pw.println("rcpt to:<"+ReceiveUser+">");
            System.out.println(br.readLine());

            //Set "data"
            pw.println("data");
            System.out.println(br.readLine());

            //正文主体(包括标题,发送方,接收方,内容,点)
            pw.println("subject:计网实验2");
            pw.println("from:"+SendUser);
            pw.println("to:"+ReceiveUser);
            pw.println("Content-Type: text/plain;charset=\"utf-8\"");//设置编码格式可发送中文内容
            pw.println();
            pw.println("小组成员：田震 万奕晨 周天宸 武斯全 王子龙");
            pw.println(".");
            pw.print("");
            System.out.println(br.readLine());

            /*
             *发送完毕,中断与服务器连接
             */
            pw.println("rset");
            System.out.println(br.readLine());
            pw.println("quit");
            System.out.println(br.readLine());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }}