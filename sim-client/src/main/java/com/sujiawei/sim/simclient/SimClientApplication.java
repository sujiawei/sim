package com.sujiawei.sim.simclient;

import com.sujiawei.sim.simclient.client.SimClient;
import com.sujiawei.sim.simcommon.protocol.Protocol;
import com.sujiawei.sim.simcommon.protocol.ProtocolUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class SimClientApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SimClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("SimClientApplication.run");
    }
}
