package org.esquivo.weather.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "wind_guru_data")
@PrimaryKeyJoinColumn(name = "wind_guru_data_id", referencedColumnName = "weather_data_id")
public class WindGuruData extends WeatherData implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer cloudCoverHigh;
    private Integer cloudCoverMid;
    private Integer cloudCoverLow;    
    
    public WindGuruData() {
 
    }

    public WindGuruData(Date startTime, Date endTime, Forecast forecast, Integer maxTemp, Integer minTemp,
            Integer windSpeed, Integer windDirection, Integer precipitation, Integer cloudiness,
            Integer visibility, Integer humidity, Integer cloudCoverHigh, Integer cloudCoverMid, Integer cloudCoverLow) {
        super();
        this.startTime = startTime;
        this.endTime = endTime;
        this.forecast = forecast;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.precipitation = precipitation;
        this.cloudiness = cloudiness;
        this.visibility = visibility;
        this.humidity = humidity;
        this.cloudCoverHigh = cloudCoverHigh;
        this.cloudCoverMid = cloudCoverMid;
        this.cloudCoverLow = cloudCoverLow;
    }

    @Min(0)
    @Max(100)
    public Integer getCloudCoverHigh() {
        return cloudCoverHigh;
    }

    public void setCloudCoverHigh(Integer cloudCoverHigh) {
        this.cloudCoverHigh = cloudCoverHigh;
    }
    
    @Min(0)
    @Max(100)
    public Integer getCloudCoverMid() {
        return cloudCoverMid;
    }
    
    public void setCloudCoverMid(Integer cloudCoverMid) {
        this.cloudCoverMid = cloudCoverMid;
    }

    @Min(0)
    @Max(100)
    public Integer getCloudCoverLow() {
        return cloudCoverLow;
    }

    public void setCloudCoverLow(Integer cloudCoverLow) {
        this.cloudCoverLow = cloudCoverLow;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, new String[] { "id" });
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
