package locus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Custom Object containing API response properties.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class APIResponse {
    private Double lat;
    private Double lon;
    private String timezone;
    private Data current;

    public APIResponse() {
    }

    /**
     * Getters and Setters.
     */

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Data getCurrent() {
        return current;
    }

    public void setCurrent(Data current) {
        this.current = current;
    }

    /**
     * Custom object containing data properties.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        Long dt;
        Double temp;
        Double pressure;
        Double humidity;

        public Data() {
        }

        /**
         * Getter and Setter for Data properties.
         */

        public Long getDt() {
            return dt;
        }

        public void setDt(Long dt) {
            this.dt = dt;
        }

        public Double getTemp() {
            return temp;
        }

        public void setTemp(Double temp) {
            this.temp = temp;
        }


        public Double getPressure() {
            return pressure;
        }

        public void setPressure(Double pressure) {
            this.pressure = pressure;
        }

        public Double getHumidity() {
            return humidity;
        }

        public void setHumidity(Double humidity) {
            this.humidity = humidity;
        }

        /**
         * @return string representation of the object
         */
        @Override
        public String toString() {
            return "Hourly{" +
                    "dt=" + dt +
                    ", temp=" + temp +
                    ", pressure=" + pressure +
                    ", humidity=" + humidity +
                    '}';
        }

    }
}
