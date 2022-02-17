package blps.lab1.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity(name = "review")
@NoArgsConstructor
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotEmpty
    private String authorName;

    @NotEmpty
    private String carModel;

    @NotEmpty
    private String modification;

    @NotNull
    @DateTimeFormat
    private Date manufactureYear;

    @NotEmpty
    private String reviewText;

    @NotEmpty
    private String pros;

    @NotEmpty
    private String cons;

    @NotEmpty
    private String advice;

    @NotNull
    @DateTimeFormat
    private Date ownershipDate;

    @NotNull
    @Min(value = 0)
    private Integer mileage;
}
