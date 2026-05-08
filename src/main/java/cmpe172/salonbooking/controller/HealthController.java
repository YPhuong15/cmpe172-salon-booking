package cmpe172.salonbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/health")
    public Map<String, String> healthCheck() {

        Map<String, String> response = new HashMap<>();

        // API health
        response.put("api", "OK");

        // Check database connectivity
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            response.put("database", "UP");
        } catch (Exception e) {
            response.put("database", "DOWN");
        }

        // System status
        response.put("status", "UP");

        return response;
    }
}