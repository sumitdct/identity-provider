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
@Table(name = "roles")
public class RolesEntity implements BaseEntity {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="roleId")
    private Long id;

    @Column(name="roleName")
    private String roleName;

    @Column(name="createdOn")
    private Timestamp createdOn;

    @Column(name="modifiedOn")
    private Timestamp modifiedOn;

    @Column(name="isActive")
    @Type(type="org.hibernate.type.NumericBooleanType")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "clientId")
    private ClientEntity clientEntity;

    @ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinTable(name="user_role",
            joinColumns={@JoinColumn(name="roleId", referencedColumnName="roleId")},
            inverseJoinColumns={@JoinColumn(name="userId", referencedColumnName="userId")})
    private Set<UserEntity> userEntities;

}
