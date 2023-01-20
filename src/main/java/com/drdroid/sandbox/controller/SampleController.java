package com.drdroid.sandbox.controller;

import com.drdroid.sandbox.models.Order;
import org.drdroid.api.DrDroidClient;
import org.drdroid.api.models.ClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class SampleController {

    private boolean killTraffic;
    private final List<String> cities;
    private final Map<String, List<String>> pickups;
    private final Map<String, List<String>> drops;
    private final Map<String, List<Integer>> merchantIds;

    private final DrDroidClient drDroidClient;

    @Autowired
    public SampleController() {
        drDroidClient = new DrDroidClient(new ClientConfig(8080, "sampleApplication"));

        cities = Arrays.asList("BLR", "HYD", "NCR");

        pickups = new HashMap<>();
        pickups.put("NCR", Arrays.asList("Kamla Nagar", "Lajpat Nagar"));
        pickups.put("BLR", Arrays.asList("Indira Nagar", "Hebbal"));
        pickups.put("HYD", Arrays.asList("Begumpet", "Jubilee Hill"));

        drops = new HashMap<>();
        drops.put("NCR", Arrays.asList("Chandnig Chowk", "Kalkaji"));
        drops.put("BLR", Arrays.asList("HSR Layout", "Frazer Town"));
        drops.put("HYD", Arrays.asList("Gachibowli", "Kukatpally"));

        merchantIds = new HashMap<>();
        merchantIds.put("NCR", Arrays.asList(94, 1943, 74));
        merchantIds.put("BLR", Arrays.asList(19, 12, 94));
        merchantIds.put("HYD", Arrays.asList(94, 1943, 38));
    }

    @GetMapping("/start")
    public void startTraffic() {
        System.out.println("starting traffic...");
        this.killTraffic = false;
        while (!this.killTraffic) {
            Order order = generateRandomOrder();
            Map<String, Object> kvPairs = new HashMap<>();
            kvPairs.put("order_id", order.getOrderId());
            kvPairs.put("city", order.getCity());
            kvPairs.put("pickup_location", order.getPickup());
            kvPairs.put("drop_location", order.getDrop());
            kvPairs.put("merchant_id", order.getMerchantId());
            kvPairs.put("order_value", order.getOrderValue());
            drDroidClient.send("sample-workflow", "test", kvPairs);
            System.out.println(String.format("Posted dr droid event for: %s", order));
        }
    }

    @GetMapping("/stop")
    public void stopTraffic() {
        this.killTraffic = true;
    }

    private Order generateRandomOrder() {

        Random randCityIndexGenerator = new Random();
        int randomCityIndex = randCityIndexGenerator.nextInt(3);
        String city = this.cities.get(randomCityIndex);

        Random randMerchantIndex = new Random();
        int merchantIndex = randMerchantIndex.nextInt(3);

        Random randPickIndex = new Random();
        int pickUpIndex = randPickIndex.nextInt(2);

        Random randDropIndex = new Random();
        int dropIndex = randDropIndex.nextInt(2);

        return Order.builder()
                .orderId(UUID.randomUUID().toString())
                .city(city)
                .merchantId(this.merchantIds.get(city).get(merchantIndex))
                .pickup(this.pickups.get(city).get(pickUpIndex))
                .drop(this.drops.get(city).get(dropIndex))
                .orderValue(generateRandomDouble())
                .build();

    }

    private double generateRandomDouble() {

        return Math.random() * (10000 - 100 + 1) + 100;

    }

}
