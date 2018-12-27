package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadServer {
    static ExecutorService executeIt = Executors.newCachedThreadPool();
    public static  ServerSocket server;
    public MultiThreadServer(){
        View view = View.getView();
        try {

            server = new ServerSocket(3345);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Сокет создан");

            while (!server.isClosed()) {

                Socket client = server.accept();
                view.settext("\nКлиент подключен\n");
                view.settext("Порт: "+client.getLocalPort()+"\n");
                view.settext("IP: "+client.getInetAddress());

                executeIt.execute(new MonoThreadClientHandler(client,view));
            }

            executeIt.shutdown();
        } catch(SocketException e){
            view.settext("\n"+e.getMessage()+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
