package edu.northeastern.process.dao;


import edu.northeastern.process.beans.CrawlerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CrawlerRepo extends CrudRepository<CrawlerEntity, Integer> {
    public CrawlerEntity findById(String id);
}