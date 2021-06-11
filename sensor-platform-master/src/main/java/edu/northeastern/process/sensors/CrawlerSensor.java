package edu.northeastern.process.sensors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Adapter;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import edu.northeastern.base.manager.ActorManager;
import edu.northeastern.base.manager.AlertSensor;
import edu.northeastern.base.sensor.AbstractSensor;
import edu.northeastern.base.sensor.SensorCommand;
import org.springframework.stereotype.Component;
import scala.Option;
import java.util.TimeZone;

/**
 *
 * This is Crawler Sensor
 *
 */

public class CrawlerSensor extends AbstractSensor {

    public static final class Msg implements SensorCommand { }
    public static final class Schedule implements SensorCommand { }
    private int count = 0;

    public static Behavior<SensorCommand> create() { return Behaviors.setup(CrawlerSensor::new); }

    public CrawlerSensor(ActorContext<SensorCommand> context) {
        super(context);
    }

    @Override
    public Receive<SensorCommand> createReceive() {
        return newReceiveBuilder()
                .onMessage(Schedule.class, this::onSchdule)
                .onMessage(Msg.class, this::onUpdate)
                .build();
    }

    private Behavior<SensorCommand> onSchdule(Schedule schedule) {
        createScheduleTask();
        return this;
    }

    private Behavior<SensorCommand> onUpdate(Msg msg) {
        count++;
        if (count > 10) {
            ActorManager.getAlertSensor().tell(new AlertSensor.Alert("Crawler sensor received 10+ times message"));
        }
        return this;
    }

    private void createScheduleTask() {
        ActorManager.getScheduler().createJobSchedule(
                "crawler scheduler",
                Adapter.toClassic(ActorManager.getAlertSensor()),
                new AlertSensor.Alert("schduled message"),
                Option.apply("Crawler"),
                "c ? * *",
                Option.empty(),
                TimeZone.getDefault()
        );
    }

}
