package com.appgate.file.processor.processor.facade.impl;

import com.appgate.file.processor.processor.api.dto.GeographicLocationDTO;
import com.appgate.file.processor.processor.api.response.GeographicInformationResponse;
import com.appgate.file.processor.processor.api.response.UploadFileResponse;
import com.appgate.file.processor.processor.facade.ProcessFileFacade;
import com.appgate.file.processor.processor.service.ProcessFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

import static com.appgate.file.processor.processor.api.enums.Operation.*;

@Service
@Slf4j
public class ProcessFileFacadeImpl implements ProcessFileFacade {

    private final ProcessFileService service;

    private static final String TYPE_DOCUMENT = "text/csv";

    public ProcessFileFacadeImpl(ProcessFileService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<UploadFileResponse> runFileUpload(final MultipartFile file) {

        if (!TYPE_DOCUMENT.equals(file.getContentType())) {
            log.error("End process - Bad request: wrong file type");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(UploadFileResponse.builder()
                            .message(WRONG_FILE_TYPE.getDescription())
                            .build());
        }
        try {
            boolean fileWithErrors = service.runProcessFile(file.getInputStream());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(UploadFileResponse.builder()
                            .message(fileWithErrors ? LOADED_WITH_ERROR.getDescription() : SUCCESSFUL.getDescription())
                            .build());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(UploadFileResponse.builder()
                            .message(FAILED.getDescription())
                            .build());
        }

    }

    @Override
    public ResponseEntity<GeographicInformationResponse> findByIpAddress(final String ipAddress) {
        if (isValidIp(ipAddress)) {
            GeographicLocationDTO geographicDto = service.findByIpAddress(convertIpDecimalFormat(ipAddress));
            if (Objects.isNull(geographicDto)) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(GeographicInformationResponse.builder()
                                .ip(ipAddress)
                                .operation(NOT_FOUND.getDescription())
                                .build());
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(GeographicInformationResponse.builder()
                            .ip(ipAddress)
                            .city(geographicDto.getCity())
                            .countryCode(geographicDto.getCountryCode())
                            .region(geographicDto.getRegion())
                            .timeZone(geographicDto.getTimeZone())
                            .operation(SUCCESSFUL.getDescription())
                            .build());

        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(GeographicInformationResponse.builder()
                        .operation(INVALID_IP.getDescription())
                        .build());
    }

    private boolean isValidIp(final String ipAddress) {
        InetAddressValidator validator = InetAddressValidator.getInstance();
        return validator.isValidInet4Address(ipAddress);
    }

    private long convertIpDecimalFormat(final String ip) {
        final String[] segmentedIp = ip.split("\\.");
        double seg = 3;
        long converted = 0;
        for (String segment : segmentedIp) {
            converted += Long.parseLong(segment) * getPow(256, seg);
            seg--;
        }
        return converted;
    }

    private long getPow(double base, double pow) {
        return (long) Math.pow(base, pow);
    }
}
