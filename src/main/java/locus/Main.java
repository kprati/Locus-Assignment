package locus;

import java.util.List;

/**
 * Main method
 */
public class Main {

    public static void main(String[] args) {
        String APIKey = args[0];
        String lat = args[1];
        String lon = args[2];

        System.out.println("Fetching pressure for last 3 days at 4 am for lat=" + lat + " " + "lon=" + lon);
        List<OutputPOJO> pressureLastThreeDays = Task1
                .newInstance(APIKey)
                .getPressureLastThreeDays(lat, lon);

        pressureLastThreeDays.forEach(o -> {
            System.out.println(String.format("Pressure for date %1$s is %2$s", o.getDate(), o.getAtmosphericPressure()));
        });

    }
}