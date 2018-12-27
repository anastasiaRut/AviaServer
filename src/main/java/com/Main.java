package com;

import java.io.IOException;
import java.net.SocketException;

public class Main {
    public static void main(String[] args)  throws InterruptedException,IOException {
        try {
            MultiThreadServer server = new MultiThreadServer();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }


    }


}
