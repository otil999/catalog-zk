package hu.otilsoft.catalog.model;

import hu.otilsoft.catalog.Formatter;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Transient
//    @Version
    private Integer revision;

    @Column(updatable = false, insertable = false)
    private LocalDateTime created;

    @Column(updatable = false, insertable = false)
    private LocalDateTime updated;

    @Column(updatable = false, insertable = false)
    private LocalDateTime deleted;

    public String getCreated() {
        return Formatter.formatLocalDateTime(created);
    }

    public String getUpdated() {
        return Formatter.formatLocalDateTime(updated);
    }

    public String getDeleted() {
        return Formatter.formatLocalDateTime(deleted);
    }
}
