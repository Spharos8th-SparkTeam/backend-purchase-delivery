package com.spark.backend_purchase_delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BackendPurchaseDeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendPurchaseDeliveryApplication.class, args);
	}

}
