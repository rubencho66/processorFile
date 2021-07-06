package com.appgate.file.processor.processor.api.controller;


import com.appgate.file.processor.processor.api.response.GeographicInformationResponse;
import com.appgate.file.processor.processor.api.response.UploadFileResponse;
import com.appgate.file.processor.processor.facade.ProcessFileFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/api/file")
public class FileController {

    private final ProcessFileFacade facade;

    public FileController(ProcessFileFacade facade) {
        this.facade = facade;
    }

    @PostMapping("/upload")
    public ResponseEntity<UploadFileResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        return facade.runFileUpload(file);
    }

    @GetMapping("/{ipAddress}/find")
    public ResponseEntity<GeographicInformationResponse> findByIpAddress(@PathVariable("ipAddress") String ipAddress) {
        return facade.findByIpAddress(ipAddress);
    }
}
