package edu.northeastern.process.sensors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import edu.northeastern.base.manager.ActorManager;
import edu.northeastern.base.sensor.AbstractSensor;
import edu.northeastern.base.sensor.SensorCommand;
import edu.northeastern.process.beans.OrderEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by F Wu
 */

public class OrderSensor extends AbstractSensor {

    public OrderSensor(ActorContext<SensorCommand> context) {
        super(context);
    }

    public static Behavior<SensorCommand> create() {
        return Behaviors.setup(OrderSensor::new);
    }

    @Override
    public Receive<SensorCommand> createReceive() {
        return newReceiveBuilder()
                .onMessage(DemoSensor.Log.class, this::onLog)
                .build();
    }

    public static final class Log implements SensorCommand {
        public OrderEntity file;
        public Log(OrderEntity file) {
            this.file = file;
        }
    }

    private Behavior<SensorCommand> onLog(DemoSensor.Log log) {

        // May use jsch to send command to host machine later
        // monitor old.log and find 88

        try {
            String command = "grep -E 'requested' ./log/old.log";
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );
            String line = "";
            int cnt = 0;
            while (
                    (line = br.readLine()) != null
            ) {
                if (line.contains("requested")) {
                    cnt++;
                }
            }
            int exit = process.waitFor();
            if (exit == 0) { // this means the command is running success
                // give alert to sensor with correct information
                if (cnt != 0) {
                    getContext().getLog().info("Dispatched");
                    ActorManager.getScheduler().cancelJob("logdemo");
                }
            } else { // means the command is running failed
                getContext().getLog().error("No requests found");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            ActorManager.getScheduler().cancelJob("logdemo");
        }
        return this;
    }
}
