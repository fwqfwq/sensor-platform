package edu.northeastern.process.beans;


import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "crawler")
@Entity
public class CrawlerEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "url")
    private String url;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;


    private boolean success;


    public CrawlerEntity(String url, String title, String text) {
        this.url = url;
        this.title = title;
        this.text = text;
        this.success = false;
    }


    public CrawlerEntity() {

    }

}
