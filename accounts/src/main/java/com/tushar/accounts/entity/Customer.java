package com.tushar.accounts.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer extends BaseEntity{

    /*
    native is the name of the generator,
    we use to tell the Data Jpa that use the native database strategy like MySQL/H2
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    @Column(name="customer_id")
    private long customerId;
    private String name;
    private String email;

    @Column(name = "mobile_number")
    private String mobileNumber;

}
