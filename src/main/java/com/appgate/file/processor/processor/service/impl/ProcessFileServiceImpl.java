package com.appgate.file.processor.processor.service.impl;

import com.appgate.file.processor.processor.api.dto.GeographicLocationDTO;
import com.appgate.file.processor.processor.mapper.CustomModelMapper;
import com.appgate.file.processor.processor.repository.ErrorGeographicLocationRepository;
import com.appgate.file.processor.processor.repository.GeographicLocationRepository;
import com.appgate.file.processor.processor.repository.model.ErrorGeographicLocation;
import com.appgate.file.processor.processor.repository.model.GeographicLocation;
import com.appgate.file.processor.processor.service.ProcessFileService;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ProcessFileServiceImpl implements ProcessFileService {

    private final GeographicLocationRepository geographicRepository;

    private final ErrorGeographicLocationRepository errorRepository;

    private final CustomModelMapper mapper;

    private boolean hasError = false;

    public ProcessFileServiceImpl(GeographicLocationRepository geographicRepository, ErrorGeographicLocationRepository errorRepository, CustomModelMapper mapper) {
        this.geographicRepository = geographicRepository;
        this.errorRepository = errorRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean runProcessFile(final InputStream inputStream) {
        log.info("Start process to load csv file");
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            //positions {IP_from - 0, IP_to- 1, Country_code- 2, Country- 3, Region- 4, City- 5, Latitude- 6, Longitude- 7, TimeZone- 8}
            csvParser.getRecords().stream().parallel().forEach(csvRecord -> {
                if (csvRecord.size() == 9) {
                    GeographicLocationDTO geographic = GeographicLocationDTO.builder()
                            .ipFrom(csvRecord.get(0))
                            .ipTo(csvRecord.get(1))
                            .countryCode(csvRecord.get(2))
                            .country(csvRecord.get(3))
                            .region(csvRecord.get(4))
                            .city(csvRecord.get(5))
                            .latitude(Double.parseDouble(StringUtils.isBlank(csvRecord.get(6)) ? "0": csvRecord.get(6)))
                            .longitude(Double.parseDouble(StringUtils.isBlank(csvRecord.get(7)) ? "0": csvRecord.get(7)))
                            .timeZone(csvRecord.get(8))
                            .dateCreation(new Date())
                            .build();
                    log.info("Processing record with ip from:  " + csvRecord.get(0) + " and ip to: " + csvRecord.get(1));
                    saveInBatch(geographic);
                }
                log.error("The record does not meet the conditions of having all the required fields. IP_FROM: " + csvRecord.get(0) + " IP_TO: " + csvRecord.get(1));
            });
            log.info("Finish process to load csv file.");
            return hasError;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("Fail to parse CSV file: " + e.getMessage());
        }
    }

    @Override
    public GeographicLocationDTO findByIpAddress(long ipDecimalFormat) {
        final List<GeographicLocation> geographicFrom = geographicRepository.findByIpFrom(ipDecimalFormat);
        if (!CollectionUtils.isEmpty(geographicFrom)) {
            return convert(geographicFrom.stream().findFirst().get(), GeographicLocationDTO.class);
        }
        final List<GeographicLocation> geographicTo = geographicRepository.findByIpTo(ipDecimalFormat);
        if (!CollectionUtils.isEmpty(geographicFrom)) {
            return convert(geographicTo.stream().findFirst().get(), GeographicLocationDTO.class);
        }
        return null;
    }

    private void saveInBatch(final GeographicLocationDTO geographic) {
        final GeographicLocation geographicLocation = convert(geographic, GeographicLocation.class);
        try {
            geographicRepository.save(geographicLocation);
        } catch (Exception ex) {
            hasError = true;
            errorRepository.save(ErrorGeographicLocation.builder()
                    .ipFrom(geographicLocation.getIpFrom())
                    .ipTo(geographicLocation.getIpTo())
                    .dateCreation(new Date())
                    .errorDescription(ex.getMessage())
                    .build());
        }
    }

    private <T> T convert(Object obj, Class<?> type) {
        return mapper.map(obj, (Type) type);
    }

}
