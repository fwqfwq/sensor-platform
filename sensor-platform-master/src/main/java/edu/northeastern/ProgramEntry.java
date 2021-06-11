package edu.northeastern;

import akka.actor.typed.ActorSystem;
import edu.northeastern.base.manager.ActorManager;
import edu.northeastern.base.manager.SensorManager;
import edu.northeastern.process.sensors.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * The entry of the project
 */
@SpringBootApplication
@EnableEurekaServer
public class ProgramEntry {

  // Function to call Demo Sensor Process
  private static void demoSensor(String processName) throws InterruptedException {
    // First:
    // create a new sensor at Main process
    // Here use Demo Sensor you can inspect the code inside DemoSensor.class
    ActorManager.getSensorManager().tell(new SensorManager.Add(processName, SampleSensor.create()));

    // create a scheduled job
    ActorManager.getSensorManager().tell(new SensorManager.Send(processName, new SampleSensor.Schedule()));
    // Seconds:
    // send sensor message within the process
    for (int i = 0; i < 11; i++) {
      ActorManager.getSensorManager().tell(new SensorManager.Send(processName, new SampleSensor.Msg()));
      Thread.sleep(2000);
    }

    // Finally:
    // At the end of the process, send message to stop process
    ActorManager.getSensorManager().tell(new SensorManager.Stop(processName));

  }

  // Function to call Crawler Sensor Process
  private static void crawlerSensor(String processName) throws InterruptedException {
    // First:
    // create a new sensor at Main process
    ActorManager.getSensorManager().tell(new SensorManager.Add(processName, CrawlerSensor.create()));

    // create a scheduled job
    ActorManager.getSensorManager().tell(new SensorManager.Send(processName, new CrawlerSensor.Schedule()));
    // Seconds:
    // send sensor message within the process
    for (int i = 0; i < 11; i++) {
      ActorManager.getSensorManager().tell(new SensorManager.Send(processName, new CrawlerSensor.Msg()));
      Thread.sleep(2000);
    }

    // Finally:
    // At the end of the process, send message to stop process
    ActorManager.getSensorManager().tell(new SensorManager.Stop(processName));


  }

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
//    demoSensor("demo");

    // From here is Web Crawler Services demo:
    crawlerSensor("crawler");

    system.terminate();


  }
}
