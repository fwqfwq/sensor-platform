package edu.northeastern.process.beans;


import javax.persistence.*;

/**
 * Created by F Wu
 */


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

    @Column(name = "date")
    private String date;

    @Column(name = "imgUrl")
    private String imgUrl;


    public CrawlerEntity() { }

    public CrawlerEntity(String url, String title, String date, String imgUrl) {
        this.url = url;
        this.title = title;
        this.date = date;
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "Title: " + this.title + "\nDate: " + this.date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isExist() {
        if(getTitle() != null) return true;
        return false;
    }
}
