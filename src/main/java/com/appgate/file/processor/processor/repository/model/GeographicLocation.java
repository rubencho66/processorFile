package com.appgate.file.processor.processor.repository.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Data
@Entity
@ToString
@Table(name = "geographic_location",
        uniqueConstraints = @UniqueConstraint(columnNames={"ip_from", "ip_to"}))
public class GeographicLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ip_from", nullable = false, unique = true)
    private Long ipFrom;

    @Column(name = "ip_to", nullable = false, unique = true)
    private Long ipTo;

    @Column(name = "country_code", nullable = false)
    private String countryCode;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    @Column(name = "time_zone", nullable = false)
    private String timeZone;

    @Column(name = "date_creation", nullable = false)
    private Date dateCreation;

}
