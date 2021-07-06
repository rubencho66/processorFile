package com.appgate.file.processor.processor.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeographicLocationDTO {

    private Long id;

    private String ipFrom;

    private String ipTo;

    private String countryCode;

    private String country;

    private String region;

    private String city;

    private double latitude;

    private double longitude;

    private String timeZone;

    private Date dateCreation;
}
