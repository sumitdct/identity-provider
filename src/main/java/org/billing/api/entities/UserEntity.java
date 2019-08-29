package org.billing.api.entities;

import com.sumitchouksey.book.entities.hibernate.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


@Setter
@AllArgsConstructor
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class UserEntity implements BaseEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="userId")
    private Long id;

    @Column(name="firstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="contactNo")
    private String contactNo;

    @Column(name="password")
    private String password;

    @Column(name="language")
    private String language;

    @Column(name="currency")
    private String currency;

    @Column(name="address")
    private String address;

    @Column(name="city")
    private String city;

    @Column(name="state")
    private String state;

    @Column(name="country")
    private String country;

    @Column(name="postalCode")
    private int postalCode;

    @Column(name="createdOn")
    private Timestamp createdOn;

    @Column(name="modifiedOn")
    private Timestamp modifiedOn;

    @Column(name= "passwordCreatedOn")
    private Timestamp passwordCreatedOn;

    @Column(name="isActive")
    @Type(type="org.hibernate.type.NumericBooleanType")
    private Boolean isActive;

    @Column(name = "accountVerified")
    @Type(type="org.hibernate.type.NumericBooleanType")
    private Boolean  accountVerified;

    @Column(name = "attempts")
    private int attempts;

    @ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinTable(name="user_role",
            joinColumns={@JoinColumn(name="userId", referencedColumnName="userId")},
            inverseJoinColumns={@JoinColumn(name="roleId", referencedColumnName="roleId")})
    private Set<RolesEntity> rolesEntity;

}
