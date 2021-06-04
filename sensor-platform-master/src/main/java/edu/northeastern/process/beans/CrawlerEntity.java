package edu.northeastern.process.beans;


import javax.persistence.*;

@Table
@Entity
public class CrawlerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int id;
    private String data;
    private boolean success;
    private String email;
    // TODO

    public CrawlerEntity(String data, String email) {
        this.data = data;
        this.success = false;
        this.email = email;
    }


    public CrawlerEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
