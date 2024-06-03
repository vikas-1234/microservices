package com.vikas.user.service.config.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import java.io.IOException;

public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    private final Logger logger = LoggerFactory.getLogger(RestTemplateInterceptor.class);

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public RestTemplateInterceptor(OAuth2AuthorizedClientManager authorizedClientManager) {
        this.authorizedClientManager = authorizedClientManager;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(
                OAuth2AuthorizeRequest.withClientRegistrationId("my-internal-client").principal("internal").build());

        if (authorizedClient == null || authorizedClient.getAccessToken() == null) {
            logger.error("Failed to obtain access token");
            throw new IllegalStateException("Failed to obtain access token");
        }

        String token = authorizedClient.getAccessToken().getTokenValue();

        logger.info("Rest Template interceptor: Token :  {} ", token);

        request.getHeaders().add("Authorization", "Bearer " + token);
        return execution.execute(request, body);
    }
}
