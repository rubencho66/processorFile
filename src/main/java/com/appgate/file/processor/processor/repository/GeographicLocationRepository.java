package com.appgate.file.processor.processor.repository;

import com.appgate.file.processor.processor.repository.model.GeographicLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GeographicLocationRepository extends JpaRepository<GeographicLocation, Long> {

    List<GeographicLocation> findByIpFrom(Long ipAddress);

    List<GeographicLocation> findByIpTo(Long ipAddress);
}
