/**
 * 
 */
package org.esquivo.weather.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;


/**
 * @author woo
 * 
 */

@Deprecated
@Entity
@Table(name = "meteo_galicia_data")
@NamedQueries({
    @NamedQuery(name = "MeteoGaliciaData.getAll", query  = "SELECT m FROM MeteoGaliciaData m")
})
public class MeteoGaliciaData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private Date date;
    private String town;
    private int maxTemp;
    private int minTemp;

    public MeteoGaliciaData() {

    }

    public MeteoGaliciaData(Date date, String town, int maxTemp, int minTemp) {
        this.date = date;
        this.town = town;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
    }
    
    public MeteoGaliciaData(int id, Date date, String town, int maxTemp, int minTemp) {
        this(date, town, maxTemp, minTemp);
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="meteo_galicia_data_meteo_galicia_data_id_seq")
    @SequenceGenerator(name="meteo_galicia_data_meteo_galicia_data_id_seq", sequenceName="meteo_galicia_data_meteo_galicia_data_id_seq", allocationSize=1)    
    @Column(name = "meteo_galicia_data_id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Column(name = "temp_max")
    @XmlElement(name = "max-temp")
    public int getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    @Column(name = "temp_min")
    public int getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    @Override
    public String toString() {
        return "MeteoGaliciaData [id=" + id + ", date=" + date + ", town=" + town + ", maxTemp=" + maxTemp
                + ", minTemp=" + minTemp + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + id;
        result = prime * result + maxTemp;
        result = prime * result + minTemp;
        result = prime * result + ((town == null) ? 0 : town.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MeteoGaliciaData other = (MeteoGaliciaData) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (id != other.id)
            return false;
        if (maxTemp != other.maxTemp)
            return false;
        if (minTemp != other.minTemp)
            return false;
        if (town == null) {
            if (other.town != null)
                return false;
        } else if (!town.equals(other.town))
            return false;
        return true;
    }

}
