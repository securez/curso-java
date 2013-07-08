package org.esquivo.weather.parser;

import java.io.InputStream;

public interface Parser<T> {

    T parse(InputStream is);

}