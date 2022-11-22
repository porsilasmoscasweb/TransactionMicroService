package reloadly.ms_02.init;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonConfiguration {
	@Bean
	public IRule ribbonRule() {
		// Allows you to apply other balancing rules
		// return new RetryRule(new WeightedResponseTimeRule(),500);
		return new RandomRule();
	}
}
