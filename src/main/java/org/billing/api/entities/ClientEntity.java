package org.billing.api.entities;

import com.sumitchouksey.book.entities.hibernate.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "clients")
public class ClientEntity implements BaseEntity {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "clientId")
    private Long id;

    @Column(name = "clientName")
    private String clientName;

    @Column(name = "configurations")
    private String configurations;

    @Column(name = "createdOn")
    private Timestamp createdOn;

    @Column(name = "modifiedOn")
    private Timestamp modifiedOn;

    @Column(name = "isActive")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isActive;

    @OneToMany(mappedBy = "clientEntity")
    private Set<RolesEntity> rolesEntitySet;

}
