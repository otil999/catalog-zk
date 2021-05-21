package hu.otilsoft.catalog.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Movie extends AbstractEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "original_name")
    private String originalName;
    private String link;
    private Integer release;
    private Double rating;
    private int counter;
    @ManyToOne(fetch = FetchType.LAZY)
    private Media media;
    @Column(name = "media_number")
    private Integer mediaNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    private Storage storage;
    @ManyToOne(fetch = FetchType.LAZY)
    private Series series;
    @Column(name = "series_num")
    private Integer seriesNum;
    @ManyToOne(fetch = FetchType.LAZY)
    private Genre genre;

    public double getRating() {
        double exp = Math.pow(10, 1);
        long l = java.lang.Math.round(rating * exp);
        return l / exp;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
