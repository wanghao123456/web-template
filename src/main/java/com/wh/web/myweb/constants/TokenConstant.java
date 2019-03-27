package com.wh.web.myweb.constants;

public class TokenConstant {
    /**
     * Token过期时间
     */
    public static final long EXPIRATION_TIME = 86_400_000;
    /**
     * Token加解密SecretKey
     */
    public static final String SECRET_KEY = "SECRET_KEY";
    /**
     * Token串前缀
     */
    public static final String TOKEN_PREFIX = "Bearer";
    /**
     * Token串存放在Http头中的Key
     */
    public static final String HEADER_KEY = "Authorization";
    /**
     * Token签发者
     */
    public static final String CLAIMS_ISSUER = "system";
    /**
     * Token主题
     */
    public static final String CLAIMS_SUBJECT = "web-template";
}
