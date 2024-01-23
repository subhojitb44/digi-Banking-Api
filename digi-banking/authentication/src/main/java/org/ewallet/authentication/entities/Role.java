package org.ewallet.authentication.entities;

import jakarta.persistence.*;
import lombok.*;
import org.ewallet.authentication.enums.RoleEnum;


@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name = "_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    @SequenceGenerator(name = "role_seq", allocationSize = 1)
    @Column(nullable = false)
    private Integer id;

    @Setter(AccessLevel.NONE)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleEnum name;

    public Role(RoleEnum name) {
        this.name = name;
    }
}