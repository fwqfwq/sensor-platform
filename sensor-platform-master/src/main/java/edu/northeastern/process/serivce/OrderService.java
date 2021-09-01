package edu.northeastern.process.serivce;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import edu.northeastern.base.manager.ActorManager;
import edu.northeastern.base.manager.AlertSensor;
import edu.northeastern.base.manager.SensorManager;
import edu.northeastern.process.beans.OrderEntity;
import edu.northeastern.process.dao.OrderRepo;
import edu.northeastern.process.sensors.OrderSensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

/**
 * Created by F Wu
 */

@Service
public class OrderService {


    @Value("${logging.file.name}")
    private String logfile;

    @Autowired
    OrderRepo orderRepo;

    @Value("${order.table.name}")
    private String tableName;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String KEY = "order";

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    /*
     Automatically dispatch orders based on rank.

     Order process procedures:
        1. Send dispatch signal into log (random int == 1, in range 0-5)
        2. Customized rank rules and then dispatch (using amount in demo)
        3. If signal (random int == 2 or 3, in range 0-5) occurs, add new data

     Sensors function:
        4. Listen log: if dispatch signal and new order signal occur give alert
     */

    public void start(String path, String input1, String input2) throws IOException, InterruptedException {
        logger.info(">>>> Order Dispatcher Services");

        // Initiate Order Sensor
        initSensor(KEY);
        listenLog();


        logger.info(">>>> starting order process... ");


        // Here is demo process for order dispatcher:
        // Read demo data into database
        readData(path);

        // Simulate signal
        Thread.sleep(5000);
        for(int i = 0; i < 15; i ++) {
            int signal = new Random().nextInt(5);
            logger.info(">>> Signal is " + signal);

            if(signal == 1) {
                // Send dispatch alert into log
                logger.info(">>>> notification: 10-order dispatching requested.. ");
                dispatcher();
            }
            else if(signal == 2) {
                readData(input1);
                sensorAlert("first");
                logger.info(">>>> notification: new orders arrived.. ");
            }
            else if(signal == 3) {
                readData(input2);
                sensorAlert("second");
                logger.info(">>>> notification: new orders arrived.. ");
            }

        }

        // Stop the sensor
        removeSensor(KEY);
    }


    public void readData(String path) {

        try {
            CSVReader csvReader = new CSVReader(new FileReader(path));
            csvReader.skip(1);

            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                OrderEntity orderEntity = new OrderEntity();

                orderEntity.setOrderId(values[4]);
                orderEntity.setProductName(values[0]);
                orderEntity.setSeriesNumber(values[2]);
                orderEntity.setAmount(Integer.parseInt(values[5]));
                orderEntity.setBuyerName(values[6]);
                orderEntity.setBuyerId(values[7]);
                orderEntity.setPurchaseTime(values[9]);
                orderEntity.setPaymentMethod(values[10]);

                add(orderEntity);
            }
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }
    }

    public void dispatcher() {
        // Rank rules - should be customized
        String sql = "select id from " + tableName + " order by amount desc";
        List<Map<String, Object>> orderlist =  jdbcTemplate.queryForList(sql);

        if(orderlist.size() >= 10) {
            // Dispatch 10 orders
            for(int i = 0; i < 10; ++i) {
                String id = orderlist.get(i).values().toString();
                id = id.substring(1, id.length() - 1);

                deleteById(id);
            }
        }
        else {
            for(int i = 0; i < orderlist.size(); ++i) {
                String id = orderlist.get(i).values().toString();
                id = id.substring(1, id.length() - 1);

                deleteById(id);
            }
            logger.warn(">>>> all orders have been dispatched");
        }

    }


    public OrderEntity getById(String id) {
        return orderRepo.findByOrderId(id);
    }

    public void deleteById(String id) {
        orderRepo.deleteById(Integer.valueOf(id));
    }

    public void add(OrderEntity orderEntity) {
        orderRepo.save(orderEntity);
    }

    private void initSensor(String key) {
        ActorManager.getSensorManager().tell(new SensorManager.Add(key, OrderSensor.create()));
    }

    private void listenLog() {
        ActorManager.getSensorManager().tell(new SensorManager.Schedule(
                "logdemo",
                KEY,
                new OrderSensor.Log(orderRepo.findByOrderId("000")),
                "check every sec",
                "*/1 * * ? * *",
                null,
                TimeZone.getDefault()
        ));
    }

    private void sensorAlert(String msg) {
        ActorManager.getAlertSensor().tell(new AlertSensor.Alert(msg + " batch of order added"));
    }

    private void removeSensor(String key){
        ActorManager.getSensorManager().tell(new SensorManager.Stop(key));
    }


}
