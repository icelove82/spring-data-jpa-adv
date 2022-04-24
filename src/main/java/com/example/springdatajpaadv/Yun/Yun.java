package com.example.springdatajpaadv.Yun;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Yun")
@Table(
        name = "yun"
)
public class Yun {

    @Id
    private Long id;

    private String fullName;
    private Integer age;
    private String email;
    private String cardNumber;
    private String bookName;
    private LocalDateTime createdAt;
}
