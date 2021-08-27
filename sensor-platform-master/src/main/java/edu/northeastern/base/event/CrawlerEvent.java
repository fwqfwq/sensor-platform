package edu.northeastern.base.event;

import edu.northeastern.base.condition.Condition;
import edu.northeastern.base.condition.CrawlerCondition;
import edu.northeastern.base.enums.AlertLevel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by F Wu
 */

public class CrawlerEvent extends Event{

    private String url;
    private String crawler_time;
    private String situation;

    private Map<String, Condition> conditions;

    public CrawlerEvent(String key, AlertLevel level, Date expireTime, String receiverName, String url, String crawler_time, String situation) {
        super(key, level, expireTime, receiverName);
        this.url = url;
        this.crawler_time = crawler_time;
        this.situation = situation;
    }

    private void buildMap(List<CrawlerCondition> conditions) {
        this.conditions = new HashMap<>();
        for (CrawlerCondition c : conditions) {
            this.conditions.put(c.getKey(), c);
        }
    }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public String getCrawler_time() { return crawler_time; }

    public void setCrawler_time(String crawler_time) { this.crawler_time = crawler_time; }

    public String getSituation() { return situation; }

    public void setSituation(String situation) { this.situation = situation; }


    @Override
    public Map<String, Condition> getConditions() {
        return conditions;
    }

    @Override
    public void removeCondition(String key) {
        this.conditions.remove(key);
    }

    public void addConditions(CrawlerCondition condition) {
        this.conditions.put(condition.getKey(), condition);
    }

}
