package az.sanan.garibli.redisexample.repository;

import az.sanan.garibli.redisexample.entity.ServiceConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceConfigRepository extends JpaRepository<ServiceConfig, String> {
}