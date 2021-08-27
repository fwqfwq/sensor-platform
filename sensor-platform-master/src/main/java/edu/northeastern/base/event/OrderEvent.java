package edu.northeastern.base.event;

import edu.northeastern.base.condition.OrderCondition;
import edu.northeastern.base.condition.Condition;
import edu.northeastern.base.enums.AlertLevel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by F Wu
 */

public class OrderEvent extends Event {

    private String order_id;
    private String statement;

    private Map<String, Condition> conditions;

    public OrderEvent(String key, AlertLevel level, Date expireTime, String receiverName, String order_id, String statement) {
        super(key, level, expireTime, receiverName);
        this.order_id = order_id;
        this.statement = statement;
    }

    private void buildMap(List<OrderCondition> conditions) {
        this.conditions = new HashMap<>();
        for (OrderCondition c : conditions) {
            this.conditions.put(c.getKey(), c);
        }
    }

    public String getOrder_id() { return order_id; }

    public void setOrder_id(String order_id) { this.order_id = order_id; }

    public String getStatement() { return statement; }

    public void setStatement(String statement) { this.statement = statement;
    }

    @Override
    public Map<String, Condition> getConditions() {
        return conditions;
    }

    @Override
    public void removeCondition(String key) {
        this.conditions.remove(key);
    }

    public void addConditions(OrderCondition condition) {
        this.conditions.put(condition.getKey(), condition);
    }

}
