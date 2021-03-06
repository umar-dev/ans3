package com.evampsaanga.ans.controllers;

import com.evampsaanga.ans.models.Country;
import com.evampsaanga.ans.services.CountryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
@RestController
public class RegionsLanguage {

    @Autowired
    private final CountryClient countryClient;

    public RegionsLanguage(CountryClient countryClient) {
        this.countryClient = countryClient;
    }

    @GetMapping("EnglishSpeakingCountriesOfAsiaAndEurope")
    public List<String> getAllEnglishSpeakingCountriesOfEuropeAndAsia()  throws Throwable {
        CompletableFuture<List<Country>> countriesByLanguage = countryClient.getCountriesByLanguage("en");
        CompletableFuture<List<Country>> countriesByRegionEurope = countryClient.getCountriesByRegion("europe");
        CompletableFuture<List<Country>> countriesByRegionAsia = countryClient.getCountriesByRegion("asia");
        List<String> EnglishSpeakingCountries;
        List<Country> countriesByRegionAsiaAndEurope ;
        try {
            EnglishSpeakingCountries = new ArrayList<>(countriesByLanguage.get().stream().map(Country::getName).collect(Collectors.toList()));
            countriesByRegionAsiaAndEurope = new ArrayList<>();
            countriesByRegionAsiaAndEurope.addAll(countriesByRegionEurope.get());
            countriesByRegionAsiaAndEurope.addAll(countriesByRegionAsia.get());
            EnglishSpeakingCountries.retainAll(countriesByRegionAsiaAndEurope.stream().map(Country::getName).collect(Collectors.toList()));

        } catch (Throwable e) {
            throw e.getCause();
        }

        return EnglishSpeakingCountries ;
    }

}
