package edu.northeastern.process.sensors;


import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import edu.northeastern.base.condition.CrawlerCondition;
import edu.northeastern.base.event.CrawlerEvent;
import edu.northeastern.base.manager.ActorManager;
import edu.northeastern.base.manager.EventManager;
import edu.northeastern.base.manager.PartitionManager;
import edu.northeastern.base.manager.SensorManager;
import edu.northeastern.base.sensor.AbstractSensor;
import edu.northeastern.base.sensor.SensorCommand;
import edu.northeastern.process.beans.CrawlerEntity;

/**
 * Created by F Wu
 *
 * Crawler Sensor
 *
 * Implementations required by user:
 *
 * 1. Give default constructor or enrich by user
 * 2. Define URL or other requested contents that sensor will receive
 *    (should implement top type SensorCommand interface)
 * 3. Define actions when receive message
 * 4. Inside actions should define the workflow when meet conditions
 */

public class CrawlerSensor extends AbstractSensor {

    private final String id;

    public CrawlerSensor(ActorContext<SensorCommand> context, String id) {
        super(context);
        this.id = id;
    }

    public static Behavior<SensorCommand> create(String id) {
        return Behaviors.setup(param -> new CommonSensor(id, param));
    }

    public static final class Update implements SensorCommand {
        public CrawlerCondition condition;
        public Update(CrawlerCondition condition) {
            this.condition = condition;
        }
    }

    public static final class Query implements SensorCommand {
        public CrawlerEntity entity;
        public Query(CrawlerEntity entity) {
            this.entity = entity;
        }
    }

    @Override
    public Receive<SensorCommand> createReceive() {
        return newReceiveBuilder()
                .onMessage(Update.class, this::onUpdate)
                .onMessage(Query.class, this::onQuery)
                .build();
    }


    private Behavior<SensorCommand> onUpdate(Update update) {
        CrawlerCondition cur = update.condition;
        CrawlerEvent e = (CrawlerEvent) EventManager.getInstance().getEvent(id);
        for (String cid :  e.getConditions().keySet()) {
            CrawlerCondition c = (CrawlerCondition) e.getConditions().get(cid);
            if (c.isMatched(cur.getKey(), cur.getValue())) {
                PartitionManager.getInstance()
                        .getPartition(ActorManager.userPartitionName)
                        .tell(new SensorManager.Send(e.getReceiverName(),
                                new UserSensor.Mail("Crawler: " + e.getUrl() + " on " + e.getCrawler_time() + " is " + e.getSituation())));
            }
        }
        return this;
    }

    private Behavior<SensorCommand> onQuery(Query query) {
        if (query.entity.isExist()) {
            getContext().getLog().warn("crawler database table update success");
            ActorManager.getScheduler().cancelJob("databasedemo");
        } else {
            getContext().getLog().info("scheduled check database table");
        }
        return this;
    }



}
