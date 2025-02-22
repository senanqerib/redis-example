package az.sanan.garibli.redisexample.service;

import az.sanan.garibli.redisexample.entity.ServiceConfig;
import az.sanan.garibli.redisexample.repository.ServiceConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceConfigService {
    
    private final RedisTemplate<String, Object> redisTemplate;
    private final ServiceConfigRepository repository;

    private static final String REDIS_PREFIX = "config:";

    public ServiceConfig getServiceConfig(String initiatorRid) {
        String redisKey = REDIS_PREFIX + initiatorRid;

        // Check Redis Cache
        ServiceConfig config = (ServiceConfig) redisTemplate.opsForValue().get(redisKey);
        if (config != null) {
            return config;
        }

        // Fetch from PostgreSQL if not found in Redis
        Optional<ServiceConfig> dbConfig = repository.findById(initiatorRid);
        if (dbConfig.isPresent()) {
            config = dbConfig.get();
            // Store in Redis for future requests (Cache for 10 mins)
            redisTemplate.opsForValue().set(redisKey, config, Duration.ofMinutes(10));
        }
        return config;
    }

    public ServiceConfig saveServiceConfig(ServiceConfig config) {
        ServiceConfig savedConfig = repository.save(config);
        redisTemplate.opsForValue().set(REDIS_PREFIX + config.getInitiatorRid(), savedConfig, Duration.ofMinutes(10));
        return savedConfig;
    }

    public void deleteServiceConfig(String initiatorRid) {
        repository.deleteById(initiatorRid);
        redisTemplate.delete(REDIS_PREFIX + initiatorRid);
    }
}
