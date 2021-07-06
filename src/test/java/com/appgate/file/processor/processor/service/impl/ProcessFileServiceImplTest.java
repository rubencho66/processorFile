package com.appgate.file.processor.processor.service.impl;

import com.appgate.file.processor.processor.api.dto.GeographicLocationDTO;
import com.appgate.file.processor.processor.mapper.CustomModelMapper;
import com.appgate.file.processor.processor.repository.ErrorGeographicLocationRepository;
import com.appgate.file.processor.processor.repository.GeographicLocationRepository;
import com.appgate.file.processor.processor.repository.model.ErrorGeographicLocation;
import com.appgate.file.processor.processor.repository.model.GeographicLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@RestClientTest({CustomModelMapper.class})
public class ProcessFileServiceImplTest {

    private ProcessFileServiceImpl service;

    @Mock
    private GeographicLocationRepository geographicRepository;

    @Mock
    private ErrorGeographicLocationRepository errorRepository;

    @Autowired
    private CustomModelMapper mapper;

    @BeforeEach
    public void setUp() {
        service =  new ProcessFileServiceImpl(geographicRepository, errorRepository, mapper);
    }

    @Test
    public void runProcessFileSuccessfulTest() throws FileNotFoundException {
        File initialFile = new File("src/test/java/files/test_gate.csv");
        InputStream targetStream = new DataInputStream(new FileInputStream(initialFile));
        final boolean hasErrors = service.runProcessFile(targetStream);
        assertFalse(hasErrors);
        verify(geographicRepository, times(65)).save(any(GeographicLocation.class));

    }

    @Test
    public void runProcessWithErrorsTest() throws FileNotFoundException {
        File initialFile = new File("src/test/java/files/test_gate.csv");
        InputStream targetStream = new DataInputStream(new FileInputStream(initialFile));
        Mockito.when(geographicRepository.save(any(GeographicLocation.class))).thenThrow(new RuntimeException("Duplicate"));
        final boolean hasErrors = service.runProcessFile(targetStream);
        assertTrue(hasErrors);
        verify(errorRepository, times(65)).save(any(ErrorGeographicLocation.class));

    }
}
