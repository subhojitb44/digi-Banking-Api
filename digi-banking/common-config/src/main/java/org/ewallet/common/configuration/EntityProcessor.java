package org.ewallet.common.configuration;

import org.ewallet.common.services.implementations.SequenceGeneratorService;
import org.ewallet.common.type.GeneratedValue;
import org.ewallet.common.type.GenerationType;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

@Component
public class EntityProcessor implements BeanPostProcessor {
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(GeneratedValue.class)) {
                GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
                if (generatedValue.strategy() == GenerationType.SEQUENCE) {
                    String sequenceName = generatedValue.generator();
                    try {
                        field.setAccessible(true);
                        field.set(bean, sequenceGeneratorService.generateSequence(sequenceName));
                    } catch (DataAccessException e) {
                        // Handle database access exceptions
                        e.printStackTrace();
                        throw e;
                    } catch (IllegalAccessException e) {
                        // Handle illegal access exceptions
                        e.printStackTrace();
                        throw new IllegalStateException("Error setting field value: " + e.getMessage());
                    }
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
