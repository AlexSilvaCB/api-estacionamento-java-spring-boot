package silvacb.alex.com.apiestacionamento.config;

import java.util.TimeZone;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringTimeZoneConfig {
	
	public void timeZoneConfig() {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
	}

}
