package org.billing.api.datasource.config.util;

import com.sumitchouksey.book.configurations.hibernate.DatasourcePoolConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@ConfigurationProperties(prefix = "dataSources.platform.poolConfiguration")
public class DataSourcePoolConfig extends DatasourcePoolConfiguration {
}
