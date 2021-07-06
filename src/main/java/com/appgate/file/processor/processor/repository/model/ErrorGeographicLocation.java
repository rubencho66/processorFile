package com.appgate.file.processor.processor.repository.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "error_geographic_location")
public class ErrorGeographicLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ip_from", nullable = false)
    private Long ipFrom;

    @Column(name = "ip_to", nullable = false)
    private Long ipTo;

    @Column(name = "error_description", nullable = false)
    private String errorDescription;

    @Column(name = "date_creation", nullable = false)
    private Date dateCreation;
}
