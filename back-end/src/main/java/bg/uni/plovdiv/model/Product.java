package bg.uni.plovdiv.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 35, unique = true)
    private String barcode;

    @Column(length = 35)
    private String brand;

    @Column(length = 35)
    private String model;

    @Column(length = 35)
    private String category;

    @Column
    private int quantity;

    @Column(precision = 10)
    private double price;

    @Column
    private LocalDate manufactureDate;

    @Lob
    private byte[] photo;

    @Column
    private boolean isAvailable;
}
