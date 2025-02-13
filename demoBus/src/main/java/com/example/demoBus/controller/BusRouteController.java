package com.example.demoBus.controller;

import com.example.demoBus.model.BusRoute;
import com.example.demoBus.repository.BusRouteRepository;
import com.example.demoBus.service.BusRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/busRoutes")
public class BusRouteController {
    private Logger logger = Logger.getLogger( BusRouteController.class.getName() );
    @Autowired
    private BusRouteRepository busRouteRepository;

    // Display the search form for bus routes.
    @GetMapping("/search")
    public String showSearchForm() {
        return "searchRoutes";
    }

    // Process search parameters and display results.
    @GetMapping("")
    public String searchRoutes(@RequestParam("origin") String origin,
                               @RequestParam("destination") String destination,
                               Model model) {
        // Note: Ensure you use the method name exactly as defined in your repository.
        List<BusRoute> routes = busRouteRepository
                .findByOriginAndDestination(origin, destination);
        model.addAttribute("routes", routes);
//        logger.info("from"+origin);
//        logger.info("to"+destination);
        return "busRoutes";
    }
}