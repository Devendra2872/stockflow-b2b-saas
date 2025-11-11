package com.example.Binary.Project.Controller;

import com.example.Binary.Project.Service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/companies/{companyId}/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping("/low-stock")
    public ResponseEntity<?> getLowStockAlerts(@PathVariable Long companyId) {
        Map<String, Object> result = alertService.getLowStockAlerts(companyId);
        return ResponseEntity.ok(result);
    }
}

