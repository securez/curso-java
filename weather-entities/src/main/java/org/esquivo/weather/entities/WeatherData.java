/**
 * 
 */
package org.esquivo.weather.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author woo
 * 
 */

@Entity
@Table(name = "weather_data")
@SequenceGenerator(name = "weather_data_weather_data_id_seq", sequenceName = "weather_data_weather_data_id_seq", allocationSize = 1)
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({ 
    @NamedQuery(name = "WeatherData.getAll", query = "SELECT w FROM WeatherData w"), 
    @NamedQuery(name = "WeatherData.getByLocation", query = "SELECT w FROM WeatherData w JOIN w.forecast f WHERE f.location = :location"),
    @NamedQuery(name = "WeatherData.getByForecast", query = "SELECT w FROM WeatherData w WHERE w.forecast = :forecast")
})
@XmlRootElement
public class WeatherData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    protected Date time;
    protected Forecast forecast;
    protected Integer maxTemp;
    protected Integer minTemp;
    protected Integer windSpeed;
    protected Integer windDirection;
    protected Integer precipitation;
    protected Integer cloudiness;
    protected Integer visibility;
    protected Integer humidity;

    public WeatherData() {

    }

    public WeatherData(Date time, Forecast forecast, Integer maxTemp, Integer minTemp,
            Integer windSpeed, Integer windDirection, Integer precipitation, Integer cloudiness,
            Integer visibility, Integer humidity) {
        super();
        this.time = time;
        this.forecast = forecast;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.precipitation = precipitation;
        this.cloudiness = cloudiness;
        this.visibility = visibility;
        this.humidity = humidity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weather_data_weather_data_id_seq")
    @Column(name = "weather_data_id", unique = true, nullable = false)
    @XmlElement
    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "weather_time", nullable = false)
    @NotNull
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    // bi-directional many-to-one association to Location
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "forecast_id", nullable = false)
    @NotNull
    public Forecast getForecast() {
        return this.forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }

    @Min(-100)
    @Max(100)
    @Column(nullable = false)
    @NotNull
    public Integer getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(Integer maxTemp) {
        this.maxTemp = maxTemp;
    }

    @Min(-100)
    @Max(100)
    public Integer getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(Integer minTemp) {
        this.minTemp = minTemp;
    }

    // Measured in km/h.
    @Min(0)
    @Max(500)
    public Integer getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Integer windSpeed) {
        this.windSpeed = windSpeed;
    }

    @Min(0)
    @Max(359)
    public Integer getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(Integer windDirection) {
        this.windDirection = windDirection;
    }

    // Measured in mm/h
    @Min(0)
    @Max(1000)
    public Integer getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(Integer precipitation) {
        this.precipitation = precipitation;
    }

    @Min(0)
    @Max(100)
    public Integer getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(Integer cloudiness) {
        this.cloudiness = cloudiness;
    }

    // Measured in meters
    @Min(0)
    @Max(100000)
    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    @Min(0)
    @Max(100)
    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }
    
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, new String[] { "id" });
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj, new String[]{});
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
