
import org.springframework.kafka.config.KafkaListenerEndpoint;
import org.springframework.kafka.retrytopic.DestinationTopic.Properties;
import org.springframework.kafka.retrytopic.RetryTopicNamesProviderFactory;
import org.springframework.kafka.retrytopic.SuffixingRetryTopicNamesProviderFactory.SuffixingRetryTopicNamesProvider;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DltAndRetryNameProvider implements RetryTopicNamesProviderFactory {
    private final String dltTopic;

    private final String retryTopic;

    private final String dltGroup;

    private final String retryGroup;

    public DltAndRetryNameProvider(String dltTopic, String retryTopic, String dltGroup, String retryGroup) {
        this.dltTopic = dltTopic;
        this.retryTopic = retryTopic;
        this.retryGroup = retryGroup;
        this.dltGroup = dltGroup;
    }

    @Override
    public RetryTopicNamesProvider createRetryTopicNamesProvider(Properties properties) {
        return new SuffixingRetryTopicNamesProvider(properties) {

            @Override
            public String getGroup(KafkaListenerEndpoint endpoint) {
                if (properties.isMainEndpoint()) {
                    log.info("Configure Main Group {}", endpoint.getGroup());
                    return endpoint.getGroup();
                } else if (properties.isDltTopic()) {
                    log.info("Configure Dlt Group {}", dltGroup);
                    return dltGroup;
                } else if (properties.isRetryTopic()) {
                    log.info("Configure Retry Group {}", retryGroup);
                    return retryGroup;
                }
                return "otsSingleRetryGroup";
            }

            @Override
            public String getGroupId(KafkaListenerEndpoint endpoint) {
                if (properties.isMainEndpoint()) {
                    log.info("Configure Main GroupId {}", endpoint.getGroup());
                    return endpoint.getGroup();
                } else if (properties.isDltTopic()) {
                    log.info("Configure Dlt GroupId {}", dltGroup);
                    return dltGroup;
                } else if (properties.isRetryTopic()) {
                    log.info("Configure Retry GroupId {}", retryGroup);
                    return retryGroup;
                }
                return "otsSingleRetryGroupId";
            }

            @Override
            public String getTopicName(String topic) {
                log.info("Configure topic name {} ", topic);
                if (properties.isMainEndpoint()) {
                    log.info("Configure Main topic {}", topic);
                    return topic;
                } else if (properties.isDltTopic()) {
                    log.info("Configure Dlt topic {}", dltTopic);
                    return dltTopic;
                } else if (properties.isRetryTopic()) {
                    if(properties.delay()>0){
                        String retryTopicBackoff = retryTopic.substring(0, retryTopic.length() - 3) + "-" + properties.delay() + retryTopic.substring(retryTopic.length() - 3);
                        log.info("Configure Retry topic with delay/backoff {}", retryTopicBackoff);
                        return retryTopicBackoff;
                    }
                    log.info("Configure Retry topic {}", retryTopic);
                    return retryTopic;
                }
                return "otsSingleRetryTopic";
            }
        };
    }
}
