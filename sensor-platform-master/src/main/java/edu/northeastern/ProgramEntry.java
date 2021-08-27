package edu.northeastern;

import akka.actor.typed.ActorSystem;
import edu.northeastern.base.manager.ActorManager;
import edu.northeastern.base.manager.SensorManager;
import edu.northeastern.process.sensors.CrawlerSensor;
import edu.northeastern.process.sensors.SampleSensor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry of the project
 */
@SpringBootApplication
public class ProgramEntry {

  public static void main(String[] args) throws InterruptedException {
    ActorSystem<Object> system = ActorSystem.create(ActorManager.create(), "actorManager");
    while (!ActorManager.isReady()) {
      Thread.sleep(1000);
    }
    SpringApplication.run(ProgramEntry.class, args);
    System.out.println("Finished...");
    while (!ActorManager.isReady()) {
      Thread.sleep(1000);
    }

//    // From here is the demo process:
//    DemoCaller("demo");

    // From here is the crawler process:
    CrawlerCaller("crawler");

    system.terminate();
  }

  public static void DemoCaller(String key) throws InterruptedException {
    // First:
    // create a new sensor at Main process
    // Here use Demo Sensor you can inspect the code inside DemoSensor.class
    ActorManager.getSensorManager().tell(new SensorManager.Add(key, SampleSensor.create()));

    // create a scheduled job
    ActorManager.getSensorManager().tell(new SensorManager.Send(key, new SampleSensor.Schedule()));
    // Seconds:
    // send sensor message within the process
    for (int i = 0; i < 11; i++) {
      ActorManager.getSensorManager().tell(new SensorManager.Send(key, new SampleSensor.Msg()));
      Thread.sleep(2000);
    }

    // Finally:
    // At the end of the process, send message to stop process
    ActorManager.getSensorManager().tell(new SensorManager.Stop(key));
  }

  public static void CrawlerCaller(String key) throws InterruptedException {
    // First:
    // Create a new sensor for crawler service (CrawlerSensor.class)
    ActorManager.getSensorManager().tell(new SensorManager.Add(key, CrawlerSensor.create("1")));

    // Second:
    // Multiple Services invoking


    // Finally:
    // At the end of the process, send message to stop process
    ActorManager.getSensorManager().tell(new SensorManager.Stop(key));

  }

}
