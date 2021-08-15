package locus;

import java.util.Date;

/**
 * A custom object containing required result
 */
public class OutputPOJO {
    private Date date;
    private Double atmosphericPressure;

    /**
     * Initialize date and atmosphericPressure
     *
     * @param date                timstamp
     * @param atmosphericPressure pressure from weather api
     */
    public OutputPOJO(Date date, Double atmosphericPressure) {
        this.date = date;
        this.atmosphericPressure = atmosphericPressure;
    }

    @Override
    public String toString() {
        return "date=" + date + ", atmosphericPressure=" + atmosphericPressure;
    }

    /**
     * Getter for date
     *
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Getter for AtmosphericPressure
     *
     * @return AtmosphericPressure
     */
    public Double getAtmosphericPressure() {
        return atmosphericPressure;
    }
}