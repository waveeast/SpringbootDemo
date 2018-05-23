package com.xxx.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by fu.dongbo on 2018/5/23.
 * Description: 读取配置信息
 */

@ConfigurationProperties(prefix = "databus")
@Component
public class AbxConfig {
}
