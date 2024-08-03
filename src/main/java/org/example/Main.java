package org.example;

public class Main {
    public static void main(String[] args) throws Exception {
        Send send = new Send();
        send.send();

        Recv recv1 = new Recv(1);
        Recv recv2 = new Recv(2);
        recv1.receive();
        recv2.receive();
    }
}