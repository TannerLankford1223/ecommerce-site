package com.example.ecommercecommon.Utils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Common utility functions for handling JWT tokens in all microservices
 *
 * @author Tanner Lankford
 * @version 1.0
 * @since 2022-27-31
 */
public class ExtractJWT {
    /**
     * Method accepts a token, and an extraction string specifying the information to be extracted from the JWT token.
     * @param token
     * @param extraction
     * @return String OR Null Method returns the requested info extracted from the token, if it exists, otherwise it
     * returns null.
     */
    public String payloadJwtExtraction(String token, String extraction) {
        String replacedToken = token.replace("Bearer", "");

        String[] tokenSections = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(tokenSections[1]));

        String[] entries = payload.split(",");
        Map<String, String> entryMap = new HashMap<>();
        for (String entry : entries) {
            String[] keyValue = entry.split(":");
            if (keyValue[0].equals(extraction)) {
                int remove = 1;
                if (keyValue[1].endsWith("}")) {
                    remove = 2;
                }
                keyValue[1] = keyValue[1].substring(0, keyValue[1].length() - remove);
                keyValue[1] = keyValue[1].substring(1);

                entryMap.put(keyValue[0], keyValue[1]);
            }
        }
        if (entryMap.containsKey(extraction)) {
            return entryMap.get(extraction);
        }
        return null;
    }
}
