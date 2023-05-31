package bg.uni.plovdiv.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Company {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 45, unique = true)
    private String bulstat;

    @Column(length = 35)
    private String name;

    @Embedded
    @Column
    private Address address;

    @Column(nullable = false, length = 20)
    private String vatNumber;

    @Column(length = 15)
    private String phoneNumber;

    @Column(length = 35)
    private String email;
}
