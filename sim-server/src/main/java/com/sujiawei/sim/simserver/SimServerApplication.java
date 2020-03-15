package com.sujiawei.sim.simserver;

import com.sujiawei.sim.simserver.server.SimServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimServerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SimServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        SimServer simServer = new SimServer();
//        simServer.start();
        System.out.println("SimServerApplication.run");
    }
}
