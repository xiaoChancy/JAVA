package com.jyhd.black.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 设置session失效时间
 */
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 30*60)
public class SessionConfig {
}
