package com.example.demoBus.service;

import com.example.demoBus.model.BusRoute;
import com.example.demoBus.repository.BusRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static java.lang.Character.getName;


@Service
public class BusRouteService {
    private  Logger logger = Logger.getLogger( BusRouteService.class.getName() );
    @Autowired
    private BusRouteRepository busRouteRepository;

    public Optional<BusRoute> findById(Long id) {
        return busRouteRepository.findById(id);
    }

    public List<BusRoute> searchRoutes(String from, String to) {
        logger.info("from"+from);
        logger.info("to"+to);
        return busRouteRepository.findByOriginAndDestination(from, to);
    }
}
