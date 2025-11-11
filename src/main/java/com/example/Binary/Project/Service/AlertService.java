package com.example.Binary.Project.Service;

import com.example.Binary.Project.DTO.LowStockAlertDTO;
import com.example.Binary.Project.Repository.LowStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AlertService {
    private final LowStockRepository repository;

    public Map<String, Object> getLowStockAlerts(Long companyId) {
        Instant lookback = Instant.now().minus(30, ChronoUnit.DAYS);
        List<LowStockAlertDTO> alerts = repository.findLowStockAlerts(companyId, lookback);

        // Compute days_until_stockout (approx)
        for (LowStockAlertDTO dto : alerts) {
            double avgDailySales = dto.getQuantitySoldLast30Days() / 30.0;
            Integer daysUntilStockout = (avgDailySales <= 0)
                    ? null
                    : (int) Math.ceil(dto.getCurrentStock() / avgDailySales);
            // Optionally store in map if needed
        }

        return Map.of("alerts", alerts, "total_alerts", alerts.size());
    }
}

