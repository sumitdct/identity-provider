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
@Table(name = "user_products")
public class UserProductEntity implements BaseEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userProductId")
    private Long id;

    @Column(name = "deviceId")
    private String deviceId;

    @Column(name = "configuration")
    private String configuration;

    @Column(name = "createdOn")
    private Timestamp createdOn;

    @Column(name = "modifiedOn")
    private Timestamp modifiedOn;

    @Column(name = "isActive")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "productId")
    private ProductEntity productEntity;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;

}
