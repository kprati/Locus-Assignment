package locus;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import static java.util.Calendar.*;

/**
 * Class to fetch past 3 days weather records.
 */
public class Task1 {

    private final String WEATHER_API_URL;
    private final String API_KEY;
    private final ExecutorService executorService;


    /**
     * Initialize WEATHER_API_URL,API_KEY and executorService
     *
     * @param apiKey unique key to be generated from openweatherapi.org
     */
    private Task1(String apiKey) {
        this.API_KEY = apiKey;
        this.WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/onecall/timemachine?lat=%1$s&lon=%2$s&dt=%3$s&appid=%4$s";
        this.executorService = Executors.newCachedThreadPool();
    }

    /**
     * @return apiKey provided by the user
     */
    public static Task1 newInstance(String apiKey) {
        return new Task1(apiKey);
    }

    /**
     * Fetch last three days dates and pressure
     * and returns list of last 3 days records to main method.
     * based on API response.
     *
     * @param lat latitute of the region to be provided by user
     * @param lon longitude of the region to be provided by user
     * @return list of OutputPojo
     */
    public List<OutputPOJO> getPressureLastThreeDays(String lat, String lon) {
        List<Long> lastThreeDates = getLastThreeDates();
        List<APIResponse> responses = invokeParallel(lat, lon, lastThreeDates);
        List<OutputPOJO> output = getOutput(responses);
        return output;
    }

    /**
     * Invoke weather data concurrently
     * and closes executor service once process
     * gets completed.
     *
     * @param lat   latitute of the region
     * @param lon   longitude of the region
     * @param dates list of past 3 timestamps
     * @return list of APIResponses
     */
    private List<APIResponse> invokeParallel(String lat, String lon, List<Long> dates) {
        List<Callable<APIResponse>> callables = new ArrayList<>();
        List<APIResponse> responses = new ArrayList<>();
        for (Long dt : dates) {
            callables.add(() -> invokeWeatherAPI(lat, lon, dt));
        }
        try {
            List<Future<APIResponse>> futures = executorService.invokeAll(callables);
            for (Future<APIResponse> f : futures) {
                responses.add(f.get());
            }
            executorService.shutdown();
            return responses;
        } catch (Exception e) {
            e.printStackTrace();
            executorService.shutdown();
            throw new RuntimeException("Error while getting data in parallel");
        }
    }

    /**
     * Connect to Weather API url
     * to fetch weather records.
     *
     * @param lat  latitute of the region
     * @param lon  longitude of the region
     * @param date previous date
     * @return records fetched based on APIResponse
     */
    private APIResponse invokeWeatherAPI(String lat, String lon, Long date) {
        try {
            String callUrl = String.format(WEATHER_API_URL, lat, lon, date, API_KEY);
            System.out.println("REST call start: " + callUrl);
            URL url = new URL(callUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            ObjectMapper objectMapper = new ObjectMapper();

            System.out.println("REST call end: " + callUrl);
            return objectMapper.readValue(inputStream, APIResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error calling Weather API");
        }
    }

    /**
     * Fetch last 3 timestamp at 4 am.
     * Dates are being fetched in epoch.
     *
     * @return list of last 3 timestamp
     */
    private List<Long> getLastThreeDates() {
        List<Long> dates = new ArrayList<>();
        Calendar utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        utc.set(HOUR_OF_DAY, 4);
        utc.set(MINUTE, 0);
        utc.set(SECOND, 0);
        utc.set(MILLISECOND, 0);


        IntStream.rangeClosed(1, 3).forEach(i -> {
            utc.set(DAY_OF_YEAR, utc.get(DAY_OF_YEAR) - 1);
            dates.add(utc.getTimeInMillis() / 1000);
        });

        return dates;
    }

    /**
     * method to return dates and pressure as output from
     * list of APIResponse
     */

    private final List<OutputPOJO> getOutput(List<APIResponse> res) {
        List<OutputPOJO> out = new ArrayList<>();

        for (APIResponse response : res) {
            Double pressure = response.getCurrent().getPressure();
            Calendar utc = Calendar.getInstance();
            utc.setTimeInMillis(response.getCurrent().getDt() * 1000);
            out.add(new OutputPOJO(utc.getTime(), pressure));

        }
        return out;
    }
}