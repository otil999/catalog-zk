package hu.otilsoft.catalog.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Series extends AbstractEntity {
    public static final Series EMPTY = new Series();
}
