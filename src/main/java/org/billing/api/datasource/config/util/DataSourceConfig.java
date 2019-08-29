package org.billing.api.datasource.config.util;

import com.sumitchouksey.book.configurations.hibernate.DataSourceConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "dataSources.platform")
@RefreshScope
public class DataSourceConfig extends DataSourceConfiguration {
}
