package hu.otilsoft.catalog.model;

import hu.otilsoft.catalog.Formatter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Storage extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "storage_type_id", referencedColumnName = "id")
    private StorageType storageType;
}
