package com.appgate.file.processor.processor.service;

import com.appgate.file.processor.processor.api.dto.GeographicLocationDTO;

import java.io.InputStream;

public interface ProcessFileService {

    boolean runProcessFile(InputStream inputStream);

    GeographicLocationDTO findByIpAddress(long ipDecimalFormat);
}
