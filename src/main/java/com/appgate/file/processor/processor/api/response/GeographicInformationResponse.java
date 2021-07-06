package com.appgate.file.processor.processor.api.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeographicInformationResponse {

    private String operation;

    private String ip;

    private String countryCode;

    private String region;

    private String city;

    private String timeZone;

}
