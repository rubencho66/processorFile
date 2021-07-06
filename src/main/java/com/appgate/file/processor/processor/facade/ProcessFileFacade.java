package com.appgate.file.processor.processor.facade;

import com.appgate.file.processor.processor.api.response.GeographicInformationResponse;
import com.appgate.file.processor.processor.api.response.UploadFileResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ProcessFileFacade {

    ResponseEntity<UploadFileResponse> runFileUpload(MultipartFile file);

    ResponseEntity<GeographicInformationResponse> findByIpAddress(String ipAddress);
}
