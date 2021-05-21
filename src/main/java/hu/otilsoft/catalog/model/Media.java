package hu.otilsoft.catalog.model;

import hu.otilsoft.catalog.Formatter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Media extends AbstractEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
