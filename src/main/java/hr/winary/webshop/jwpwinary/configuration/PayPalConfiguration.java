package hr.winary.webshop.jwpwinary.configuration;

import com.paypal.base.rest.APIContext;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayPalConfiguration {

    @Value("${paypal.clientId}")
    private String clientId;

    @Value("${paypal.clientSecret}")
    private String clientSecrete;

    @Value("${paypal.mode}")
    private String mode;


    @Bean
    public PayPalHttpClient getPaypalClient(
            @Value("${paypal.clientId}") String clientId,
            @Value("${paypal.clientSecret}") String clientSecret) {
        return new PayPalHttpClient(new PayPalEnvironment.Sandbox(clientId, clientSecret));
    }

    @Bean
    public APIContext apiContext(){
        return new APIContext(clientId, clientSecrete, mode);
    }
}
