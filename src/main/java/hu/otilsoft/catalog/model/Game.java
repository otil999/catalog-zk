package hu.otilsoft.catalog.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Game extends AbstractEntity {
    private int counter;
    private Double rating;
    private Integer release;
    private String link;

    @ManyToOne
    private Media media;

    @ManyToOne
    private Series series;

    @Column(name = "series_num")
    private Integer seriesNum;

    @ManyToOne
    private Genre genre;

    @Column(name = "long_term")
    private Boolean longTerm;
    private String description;
}
