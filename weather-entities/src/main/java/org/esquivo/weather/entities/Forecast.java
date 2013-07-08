package org.esquivo.weather.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "forecasts")
@SequenceGenerator(name = "forecasts_forecast_id_seq", sequenceName = "forecasts_forecast_id_seq", allocationSize = 1)
@NamedQueries({ @NamedQuery(name = "Forecast.getAll", query = "SELECT f FROM Forecast f"), })
@XmlRootElement
public class Forecast implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    protected Date startTime;
    protected Location location;
    protected Provider provider;
    protected List<WeatherData> weatherData;

    public Forecast() {

    }

    public Forecast(Date startTime, Location location, Provider provider) {
        this.startTime = startTime;
        this.location = location;
        this.provider = provider;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "forecasts_forecast_id_seq")
    @Column(name = "weather_data_id", unique = true, nullable = false)
    @XmlElement
    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id", nullable = false)
    @NotNull
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "provider_type", nullable = false)
    @NotNull
    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    @OneToMany(mappedBy = "forecast", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @XmlTransient
    public List<WeatherData> getWeatherData() {
        if (this.weatherData == null) {
            this.weatherData = new ArrayList<WeatherData>();
        }

        return weatherData;
    }

    public void setWeatherData(List<WeatherData> weatherData) {
        this.weatherData = weatherData;
    }

    public void addWeatherData(WeatherData weatherData) {
        getWeatherData().add(weatherData);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, new String[] { "id" });
    }

    @Override
    public boolean equals(Object obj) {
        // EqualsBuilder eq = new EqualsBuilder();

        return EqualsBuilder.reflectionEquals(this, obj, new String[] { "weatherData" });
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
