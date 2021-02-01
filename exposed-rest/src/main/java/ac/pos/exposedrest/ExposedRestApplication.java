package ac.pos.exposedrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class ExposedRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExposedRestApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public Jaxb2Marshaller jaxb2Marshaller() {
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setPackagesToScan("ac.pos.exposedrest.dtos");

		Map<String, Object> props = new HashMap<String, Object>();
		props.put("jaxb.formatted.output", false);
		jaxb2Marshaller.setMarshallerProperties(props);
		return jaxb2Marshaller;
	}
}
