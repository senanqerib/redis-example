package az.sanan.garibli.redisexample.controller;

import az.sanan.garibli.redisexample.entity.ServiceConfig;
import az.sanan.garibli.redisexample.service.ServiceConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/service-config")
@RequiredArgsConstructor
public class ServiceConfigController {

    private final ServiceConfigService serviceConfigService;

    @GetMapping("/{initiatorRid}")
    public ResponseEntity<ServiceConfig> getConfig(@PathVariable String initiatorRid) {
        ServiceConfig config = serviceConfigService.getServiceConfig(initiatorRid);
        return config != null ? ResponseEntity.ok(config) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ServiceConfig> saveConfig(@RequestBody ServiceConfig config) {
        return ResponseEntity.ok(serviceConfigService.saveServiceConfig(config));
    }

    @DeleteMapping("/{initiatorRid}")
    public ResponseEntity<Void> deleteConfig(@PathVariable String initiatorRid) {
        serviceConfigService.deleteServiceConfig(initiatorRid);
        return ResponseEntity.noContent().build();
    }
}
