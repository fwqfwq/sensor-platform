package edu.northeastern.process.dao;


import edu.northeastern.base.condition.CrawlerCondition;
import edu.northeastern.process.beans.CrawlerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by F Wu
 */

@Repository
public interface CrawlerRepo extends CrudRepository<CrawlerEntity, Integer> {
    public CrawlerEntity findById(int id);
}