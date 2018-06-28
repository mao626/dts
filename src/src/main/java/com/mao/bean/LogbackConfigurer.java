package com.mao.bean;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.FileNotFoundException;
import java.io.IOException;

/**logback的配置<br>
 * logback.xml放在src根目录下可以默认直接加载<br>
 * 1.logback首先会试着查找logback.groovy文件;<br>
 * 2.当没有找到时，继续试着查找logback-test.xml文件;<br>
 * 3.当没有找到时，继续试着查找logback.xml文件;<br>
 * 4.如果仍然没有找到，则使用默认配置（打印到控制台）
 */
public class LogbackConfigurer {
    public static void initLogging(String location)
        throws FileNotFoundException {
        LoggerContext context = (LoggerContext) LoggerFactory
            .getILoggerFactory();
        try {
            ClassPathResource resource = new ClassPathResource(location);
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(context);
            context.reset();
            configurator.doConfigure(resource.getFile());
        } catch (JoranException | IOException je) {
            je.printStackTrace();
        }
    }
}
