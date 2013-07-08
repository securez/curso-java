package org.esquivo.weather.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "locations")
@NamedQueries({
        @NamedQuery(name = "Location.getAll", query = "SELECT m FROM Location m"),
        @NamedQuery(name = "Location.getByTownProvinceRegionCountry", query = "SELECT m FROM Location m WHERE m.town = :town " +
        		"AND m.province = :province AND m.region = :region AND m.country = :country") })
@XmlRootElement
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String town;
    private String province;
    private String region;
    private String country;
    private Integer latitude;
    private Integer longitude;
    private List<Forecast> forecasts;

    public Location() {
        super();
    }

    public Location(String town, String province, String region, String country, Integer latitude, Integer longitude) {
        super();
        this.town = town;
        this.province = province;
        this.region = region;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Id
    @SequenceGenerator(name = "LOCATIONS_LOCATIONID_GENERATOR", sequenceName = "LOCATIONS_LOCATION_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOCATIONS_LOCATIONID_GENERATOR")
    @Column(name = "location_id", unique = true, nullable = false)
    @XmlElement(name  = "id")
    public Integer getId() {
        return id;
    }

    protected void setId(Integer id) {
        this.id = id;
    }

    @Column(length = 100, nullable = false)
    @NotNull
    @Size(min = 2, max = 100)
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Column(length = 100, nullable = false)
    @NotNull
    @Size(min = 2, max = 100)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(length = 100)
    @Size(min = 2, max = 100)
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Column(length = 100, nullable = false)
    @NotNull
    @Size(min = 2, max = 100)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Min(0)
    @Max(1295999)
    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    @Min(0)
    @Max(1295999)
    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    // bi-directional many-to-one association to WeatherData
    @XmlTransient
    @OneToMany(mappedBy = "location")
    public List<Forecast> getForecasts() {
        if (this.forecasts == null) {
            this.forecasts = new ArrayList<Forecast>();
        }
        return this.forecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }

    public void addForecast(Forecast data) {
        this.getForecasts().add(data);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, new String[] { "id" });
    }

    @Override
    public boolean equals(Object obj) {
        //EqualsBuilder eq = new EqualsBuilder();

        return EqualsBuilder.reflectionEquals(this, obj, new String[] { "forecasts" });
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
