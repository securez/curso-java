package org.esquivo.weather.parser;

import java.io.InputStream;
import java.util.List;

import org.esquivo.weather.entities.Forecast;

public class WindguruParser implements Parser<List<Forecast>> {

    /* (non-Javadoc)
     * @see org.esquivo.weather.Parser#parse(java.io.InputStream)
     */
    @Override
    public List<Forecast> parse(InputStream is) {
        return null;
    }
}
