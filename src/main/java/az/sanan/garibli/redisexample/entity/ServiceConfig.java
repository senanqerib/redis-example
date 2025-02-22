package az.sanan.garibli.redisexample.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "service_config")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ServiceConfig {
    
    @Id
    private String initiatorRid;

    private String url;

    private Integer readTimeout;
}
