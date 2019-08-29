package org.billing.api.entities;

import com.sumitchouksey.book.entities.hibernate.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tokens")
public class TokenVerificationEntity implements BaseEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tokenId")
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "createdOn")
    private Timestamp createdOn;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private UserEntity userEntity;

    @Column(name = "isActive")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isActive;

}
