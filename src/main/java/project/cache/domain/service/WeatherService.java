package project.cache.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.cache.domain.entity.Weather;
import project.cache.domain.repository.WeatherRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {

    private final WeatherRepository weatherRepository;

    @Cacheable(value = "weather", key = "#city")
    public String getWeather(String city) {
        log.info("Fetching data from DB for city: {}", city);
        Optional<Weather> weather = weatherRepository.findByCity(city);
        return weather.map(Weather::getForecast).orElse("Weather data not available");
    }

    public Weather addWeather(Weather weather) {
        return weatherRepository.save(weather);
    }

    public List<Weather> getAllWeather() {
        return weatherRepository.findAll();
    }

    @CachePut(value = "weather", key = "#city")
    public String updateWeather(String city, String updateWeather) {
        weatherRepository.findByCity(city).ifPresent(weather -> {
            weather.setForecast(updateWeather);
            weatherRepository.save(weather);
        });

        return updateWeather;
    }

    @Transactional
    @CacheEvict(value = "weather", key = "#city")
    public void deleteWeather(String city) {
        weatherRepository.deleteByCity(city);
    }
}
