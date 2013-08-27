package org.esquivo.weather.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.esquivo.weather.entities.Forecast;
import org.esquivo.weather.entities.Provider;
import org.esquivo.weather.entities.WeatherData;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class WindguruParser.
 */
public class WindguruParser implements Parser<List<Forecast>> {

    /** The Constant pat. */
    private static final Pattern pat = Pattern.compile("\\s*var (\\S+) = (\\{.*\\});");

    /** The Constant KEY_DATA_JSON. */
    private static final String KEY_DATA_JSON = "wg_fcst_tab_data_1";

    /*
     * (non-Javadoc)
     * 
     * @see org.esquivo.weather.Parser#parse(java.io.InputStream)
     */
    @Override
    public List<Forecast> parse(InputStream is) {
        List<Forecast> forecasts = new ArrayList<Forecast>();
        InputStreamReader reader = null;

        try {
            reader = new InputStreamReader(is);
            Map<String, String> data = getWindguruData(reader);

            String json = data.get(KEY_DATA_JSON);
            if (json == null) {
                throw new RuntimeException("Can't get JSON forecast data");
            }

            // Parse the windguru JSON
            Forecast forecast = getForecastData(json);
            forecasts.add(forecast);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // Ignore close exception
                }
            }
        }

        return forecasts;
    }

    /**
     * Gets the forecast data from WindGuru JSON.
     * 
     * @param json
     *            the json
     * @return the forecast data
     */
    @SuppressWarnings("unchecked")
    private Forecast getForecastData(String json) {
        Forecast forecast = new Forecast();
        forecast.setProvider(Provider.WINDGURU);

        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        try {
            JsonNode rootNode = mapper.readTree(json);
            JsonNode fcstNode = rootNode.path("fcst").path("3");

            forecast.setTime(new Date(fcstNode.path("initstamp").asInt() * 1000L));
            JsonNode tempNode = fcstNode.path("TMPE");
            JsonNode rhNode = fcstNode.path("RH");
            JsonNode tcdcNode = fcstNode.path("TCDC");
            JsonNode apcpNode = fcstNode.path("APCP");
            JsonNode wspdNode = fcstNode.path("WINDSPD");
            JsonNode wdirNode = fcstNode.path("WINDDIR");

            int num = tempNode.size();

            List<WeatherData> wdata = new ArrayList<WeatherData>(num);

            for (List<JsonNode> nodes : new ParallelIterable<JsonNode>(tempNode.iterator(), rhNode.iterator(),
                    tcdcNode.iterator(), apcpNode.iterator(), wspdNode.iterator(), wdirNode.iterator())) {
                WeatherData wd = new WeatherData();
                wd.setMaxTemp(nodes.get(0).asInt());
                // TODO : Min = Max?
                wd.setMinTemp(wd.getMaxTemp());
                wd.setHumidity(nodes.get(1).asInt());
                wd.setCloudiness(nodes.get(2).asInt());
                wd.setPrecipitation(nodes.get(3).asInt());
                wd.setWindSpeed(nodes.get(4).asInt());
                wd.setWindDirection(nodes.get(5).asInt());

                wd.setForecast(forecast);
                wdata.add(wd);
            }
            forecast.setWeatherData(wdata);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing data : " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("Input / Output error : " + e.getMessage(), e);
        }

        return forecast;
    }

    /**
     * Gets the windguru data in a Map, with key as var name, valuea as JSON
     * data.
     * 
     * @param reader
     *            the reader
     * @return the windguru data
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private static Map<String, String> getWindguruData(Reader reader) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(reader);

            Map<String, String> data = new HashMap<String, String>();

            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("var ")) {
                    Matcher matcher = pat.matcher(line);
                    if (matcher.matches()) {
                        data.put(matcher.group(1), matcher.group(2));
                    }
                }
            }

            return data;
        } finally {
            // Close the input stream
            if (br != null) {
                br.close();
            }
        }
    }

    // TODO : Review and possibly move to utils library
    /**
     * The Class ParallelIterable<T> create a Iterable<List<T>> that is a
     * compound of multiple Iterator<T> that will be iterated in parallel.
     * 
     * @param <T>
     *            the generic type
     */
    public static class ParallelIterable<T> implements Iterable<List<T>> {

        /** The iterables. */
        private final List<Iterator<T>> iterators;

        /**
         * Instantiates a new parallel iterable.
         * 
         * @param iterators
         *            the iterators
         */
        public ParallelIterable(Iterator<T>... iterators) {
            this.iterators = new ArrayList<Iterator<T>>(iterators.length);
            this.iterators.addAll(Arrays.asList(iterators));
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Iterable#iterator()
         */
        public Iterator<List<T>> iterator() {
            return new Iterator<List<T>>() {

                public boolean hasNext() {
                    boolean hasNext = false;
                    for (Iterator<T> list : iterators) {
                        hasNext |= list.hasNext();
                    }
                    return hasNext;
                }

                public List<T> next() {
                    List<T> vals = new ArrayList<T>(iterators.size());
                    for (int i = 0; i < iterators.size(); i++) {
                        vals.add(iterators.get(i).hasNext() ? iterators.get(i).next() : null);
                    }
                    return vals;
                }

                public void remove() {
                    throw new UnsupportedOperationException("Remove operation not implemented");
                }
            };
        }
    }
}
