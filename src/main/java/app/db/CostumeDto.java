package app.db;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "inventory")
public class CostumeDto {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "condition")
    private String condition;

    @Column(name = "inventory_date")
    private Date inventoryDate;
}
