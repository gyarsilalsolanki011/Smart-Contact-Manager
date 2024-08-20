package com.gsl.scm.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

    private static final Logger log = LoggerFactory.getLogger(Helper.class);

    public static String getEmailOfLoggedInUser(Authentication authentication) {

        if (authentication instanceof OAuth2AuthenticationToken aOAuth2AuthenticationToken) {
            var clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oAuth2User = (OAuth2User) authentication.getPrincipal();
            String username = "";

            if (clientId.equalsIgnoreCase("google")) {

                log.info("Getting email of google user");
                username = oAuth2User.getAttribute("email").toString();

            } else if (clientId.equalsIgnoreCase("github")) {

                log.info("Getting email of github user");
                username = oAuth2User.getAttribute("login").toString() + "@gmail.com";

            }

            return username;

        } else {
            log.info("Getting data from local database user");
            return authentication.getName();
        }
    }

    public static String getLinkForEmailVerification(String emailToken) {
        return "http://localhost:8080/auth/verify-email?token=" + emailToken;
    }
 }
