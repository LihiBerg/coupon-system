package com.lihicouponsystem.entities;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(length = 36, unique = true)
    private UUID uuid;

    @Column
    private String title;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private Category category;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @Column
    private Integer amount;

    @Column
    private BigDecimal price;

    @Column
    private String imageUrl;
}
