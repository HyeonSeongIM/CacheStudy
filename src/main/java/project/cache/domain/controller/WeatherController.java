package project.cache.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.cache.domain.entity.Weather;
import project.cache.domain.service.CacheInspectionService;
import project.cache.domain.service.WeatherService;

import java.util.List;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;
    private final CacheInspectionService cacheInspectionService;

    @GetMapping
    public String gerWeather(@RequestParam String city) {
        return weatherService.getWeather(city);
    }

    @PostMapping
    public Weather addWeather(@RequestBody Weather weather) {
        return weatherService.addWeather(weather);
    }

    @GetMapping("/all")
    public List<Weather> getAllWeather() {
        return weatherService.getAllWeather();
    }

    @GetMapping("/cacheData")
    public void getCacheData() {
        cacheInspectionService.printCacheContents("weather");
    }

    @PutMapping("/{city}")
    public String updateWeather(@PathVariable String city, @RequestBody String weatherUpdate) {
        return weatherService.updateWeather(city, weatherUpdate);
    }
}
