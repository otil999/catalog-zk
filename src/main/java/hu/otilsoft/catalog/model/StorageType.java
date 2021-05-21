package hu.otilsoft.catalog.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity(name = "storage_type")
@Data
@EqualsAndHashCode(callSuper = true)
public class StorageType extends AbstractEntity {
}
