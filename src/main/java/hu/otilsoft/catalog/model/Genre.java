package hu.otilsoft.catalog.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.time.LocalDateTime;

@Entity
@Data
public class Genre {
    @Id
    private long id;
    private String name;
    @Transient
    private long version;
    private LocalDateTime created;
    private LocalDateTime updated;
    private LocalDateTime deleted;
}
