package com.example.xpanditchallenge.models;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "launchDate")
    private LocalDate launchDate;
    @Column(name = "rank")
    private int rank;
    @Column(name = "revenue")
    private int revenue;

    public Movie(){
        this.title = null;
        this.launchDate = null;
        this.rank = 0;
        this.revenue = 0;
    }
    public Movie(String title, LocalDate launchDate, int rank, int revenue){
        this.title = title;
        this.launchDate = launchDate;
        this.rank = rank;
        this.revenue = revenue;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(LocalDate launchDate) {
        this.launchDate = launchDate;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", launchDate=" + launchDate +
                ", rank=" + rank +
                ", revenue=" + revenue +
                '}';
    }
}
