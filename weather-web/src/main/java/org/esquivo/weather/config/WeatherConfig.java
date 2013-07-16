package org.esquivo.weather.config;

import org.apache.deltaspike.core.api.config.PropertyFileConfig;

public class WeatherConfig implements PropertyFileConfig {
    private static final long serialVersionUID = 1L;

    @Override
    public String getPropertyFileName() {
        return "weather.properties";
    }
}