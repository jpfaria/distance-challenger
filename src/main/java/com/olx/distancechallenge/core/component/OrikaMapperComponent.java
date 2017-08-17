package com.olx.distancechallenge.core.component;

import com.olx.distancechallenge.core.setting.OrikaSetting;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.Converter;
import ma.glasnost.orika.Mapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.Map;

@Component("mapper")
@Slf4j
public class OrikaMapperComponent extends ConfigurableMapper implements ApplicationContextAware {

    private static final String ADD_IMMUTABLE_DEBUG_MESSAGE = "Immutable class [{}] registered";

    private MapperFactory factory;
    private ApplicationContext applicationContext;

    @Autowired
    private OrikaSetting setting;

    public OrikaMapperComponent() {
        super(false);
    }

    @Override
    protected void configureFactoryBuilder(final DefaultMapperFactory.Builder factoryBuilder) {
    }

    @Override
    protected void configure(final MapperFactory factory) {
        this.factory = factory;
        addAllSpringBeans();
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.init();
    }

    private void addAllSpringBeans() {

        addPassThroughConverter();

        final Map<String, Converter> converters = applicationContext.getBeansOfType(Converter.class);
        for (final Converter converter : converters.values()) {
            addConverter(converter);
        }

        final Map<String, Mapper> mappers = applicationContext.getBeansOfType(Mapper.class);
        for (final Mapper mapper : mappers.values()) {
            addMapper(mapper);
        }
    }

    private void addConverter(final Converter<?, ?> converter) {
        factory.getConverterFactory().registerConverter(converter);
    }

    private void addPassThroughConverter() {

        String immutablesClasses = setting.getImmutables();

        if (immutablesClasses != null) {
            String[] immutables = setting.getImmutables().split(",");
            for (String immutable : immutables) {
                try {
                    Type type = Class.forName(immutable);
                    factory.getConverterFactory().registerConverter(new PassThroughConverter(type));
                    log.info(ADD_IMMUTABLE_DEBUG_MESSAGE, immutable);
                } catch (ClassNotFoundException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }

    }

    private void addMapper(final Mapper<?, ?> mapper) {
        factory.classMap(mapper.getAType(), mapper.getBType())
                .byDefault()
                .customize((Mapper) mapper)
                .register();
    }
}
