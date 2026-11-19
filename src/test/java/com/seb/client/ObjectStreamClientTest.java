package com.seb.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import com.seb.model.MapObjectUpdate;

public class ObjectStreamClientTest {
    ObjectStreamClient client = new ObjectStreamClient(mapObjectU -> {
    }, "localhost", 5463);
    MapObjectUpdateParser parser = new MapObjectUpdateParser();

    @Test
    public void testReceivesObjectFromSocket() throws Exception {
        AtomicReference<MapObjectUpdate> received = new AtomicReference<>();
        CountDownLatch receivedLatch = new CountDownLatch(1);

        try (ServerSocket server = new ServerSocket(0)) {
            Thread serverThread = new Thread(() -> {
                try (Socket socket = server.accept();
                        PrintWriter out = new PrintWriter(
                                socket.getOutputStream(), true)) {
                    out.println("ID=1;X=100;Y=200;TYPE=2");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });

            serverThread.start();

            ObjectStreamClient socketClient = new ObjectStreamClient(mapObject -> {
                received.set(mapObject);
                receivedLatch.countDown();
            }, "localhost", server.getLocalPort());

            Thread clientThread = new Thread(socketClient);
            clientThread.start();

            assertEquals(true, receivedLatch.await(2, TimeUnit.SECONDS));

            clientThread.interrupt();
            clientThread.join(2000);

            serverThread.join(2000);

            assertEquals(1, received.get().id());
            assertEquals(100, received.get().x());
            assertEquals(200, received.get().y());
            assertEquals(2, received.get().type());
        }
    }
}
