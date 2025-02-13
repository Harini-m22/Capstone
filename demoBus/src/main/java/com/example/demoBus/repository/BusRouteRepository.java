package com.example.demoBus.repository;

import com.example.demoBus.controller.BusRouteController;
import com.example.demoBus.model.BusRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.logging.Logger;

@Repository
public interface BusRouteRepository extends JpaRepository<BusRoute, Long> {
    // Use the correct property names ("origin" and "destination") as defined in BusRoute entity.

//    List<BusRoute> findByOriginContainingIgnoreCaseAndDestinationContainingIgnoreCase(String origin, String destination);


    List<BusRoute> findByOriginAndDestination(String from, String to);
}