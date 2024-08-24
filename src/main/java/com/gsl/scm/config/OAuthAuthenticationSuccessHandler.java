package com.gsl.scm.config;

import com.gsl.scm.entities.Provider;
import com.gsl.scm.entities.User;
import com.gsl.scm.helper.AppConstants;
import com.gsl.scm.repositories.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        log.info("OAuth Authentication success handler");

        var authToken = (OAuth2AuthenticationToken) authentication;
        String authorizedClientId = authToken.getAuthorizedClientRegistrationId();

        log.info("Authorized Client ID: {}", authorizedClientId);

        var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
        oauthUser.getAttributes().forEach((key, value) -> {
            log.info(key + " : " + value);
        });

        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setPassword("dummy");

        if (authorizedClientId.equalsIgnoreCase("google")) {

            user.setEmail(oauthUser.getAttribute("email").toString());
            user.setProfilePic(oauthUser.getAttribute("picture").toString());
            user.setName(oauthUser.getAttribute("name").toString());
            user.setProviderUserId(oauthUser.getName());
            user.setProvider(Provider.GOOGLE);
            user.setAbout("This account is created using google.");

        } else if (authorizedClientId.equalsIgnoreCase("linkedin")) {

            log.info("Linked In have to define");

        } else {
            log.info("OAuth Authentication failure: Unknown provider");
        }

        User user2 = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (user2 == null) {
            userRepository.save(user);
            log.info("user saved:{}", user.getEmail());
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }
}
