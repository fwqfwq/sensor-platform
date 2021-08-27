package edu.northeastern.process.serivce;


import edu.northeastern.base.manager.ActorManager;
import edu.northeastern.base.manager.SensorManager;
import edu.northeastern.process.beans.CrawlerEntity;
import edu.northeastern.process.beans.OrderEntity;
import edu.northeastern.process.dao.OrderRepo;
import edu.northeastern.process.sensors.CrawlerSensor;
import edu.northeastern.process.sensors.OrderSensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.TimeZone;

/**
 * Created by F Wu
 */

public class OrderService {


    @Value("${logging.file.name}")
    private String logfile;

    @Autowired
    OrderRepo orderRepo;


    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);


    /*
     Basic Idea:
     A real-time updated order list, automated dispatching orders based on:
     1. rank buyers based on purchase amount,
     2.

     Order process procedures:

        1. Count order buyers, rank them on products amounts
        2.

        1. Simulate order buyer's id, using a random int ( random number from 0-100)
        2. Retrieve order bought by this buyer
        3. if others -> {
            3.1 do random again
            3.2 log the number
        }
     Sensors function:
        4. listen log if meet number is 88 give alert
        5. continue listen database table if table.success == true give alert
        6. if run step1 100 times then give alert
     */

    public void start(String url) throws IOException {
        logger.info(">>>> Order Dispatcher ");



        // Initiate Order Sensor
//        initSensor("demo", url);
//        listenTable();


    }

    public boolean isExist(String id){
        OrderEntity orderEntity = getById(id);
        return null != orderEntity;
    }

    public OrderEntity getById(String id) {
        return orderRepo.findByOrderId(id);
    }

    public void add(OrderEntity orderEntity) {
        orderRepo.save(orderEntity);
    }

//    private void initSensor(String key, String url) {
//        ActorManager.getSensorManager().tell(new SensorManager.Add(key, OrderSensor.create(url)));
//    }
//
//    private void listenTable() {
//        ActorManager.getSensorManager().tell(new SensorManager.Schedule(
//                "OrderDemo",
//                "order",
//                new CrawlerSensor.Update((CrawlerEntity)orderRepo.findById("order")),
//                "do order dispatcher every 2 min",
//                "0 */2 * ? * *",
//                null,
//                TimeZone.getDefault()
//        ));
//    }

}
