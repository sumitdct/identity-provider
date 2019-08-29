package org.billing.api.entities;

import com.sumitchouksey.book.entities.hibernate.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "products")
public class ProductEntity implements BaseEntity {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "productId")
    private Long id;

    @Column(name = "productName")
    private String productName;

    @Column(name = "createdOn")
    private Timestamp createdOn;

    @Column(name = "isActive")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isActive;

}
