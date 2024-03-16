package ru.hogwarts.school.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Avatar {

    @Id
    @GeneratedValue
    private Long id;
    private String filePath, mediaType;
    private long fileSize;

    @Lob
    private byte[] data;

    @OneToOne
    private Student student;
}
