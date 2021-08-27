package edu.northeastern.process.dao;


import edu.northeastern.process.beans.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by F Wu
 */

@Repository
public interface OrderRepo extends CrudRepository<OrderEntity, Integer> {
    public OrderEntity findByOrderId(String orderId);
    public OrderEntity findBySeriesNumber(String seriesNumber);
}
