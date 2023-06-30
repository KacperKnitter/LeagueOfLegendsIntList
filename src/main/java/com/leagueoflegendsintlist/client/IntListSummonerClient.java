package com.leagueoflegendsintlist.client;

import com.leagueoflegendsintlist.client.contract.SummonerDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.leagueoflegendsintlist.client.ClientSettings.getApiKey;
import static com.leagueoflegendsintlist.client.dictionaries.ServerDictionary.getServer;

@Component
public class IntListSummonerClient {

    private final RestTemplate restClient;
    private final String apiKey;

    public IntListSummonerClient() {
        restClient = new RestTemplate();
        apiKey = getApiKey();
    }


    public String getPuuidBySummonerName(String server, String summonerName) {
        SummonerDto summoner = getSummonerBySummonerName(server, summonerName);
        return summoner.puuid;
    }

    public SummonerDto getSummonerBySummonerName(String server, String summonerName) {
        var url = uriBuilderForSearchBySummonerName(server, summonerName);
        return restClient.getForEntity(url, SummonerDto.class).getBody();
    }

    public SummonerDto getSummonerByPuuid(String server, String puuid){
        var url = uriBuilderForSearchByPuuid(server,puuid);
        return restClient.getForEntity(url, SummonerDto.class).getBody();
    }

    private URI uriBuilderForSearchBySummonerName(String server , String summonerName) {
        return UriComponentsBuilder
                .fromHttpUrl(getServer(server))
                .path("by-name/")
                .path(summonerName)
                .query("api_key={apiKey}")
                .buildAndExpand(apiKey)
                .toUri();
    }
    private URI uriBuilderForSearchByPuuid(String server ,String puuid) {
        return UriComponentsBuilder
                .fromHttpUrl(getServer(server))
                .path("by-puuid/")
                .path(puuid)
                .query("api_key={apiKey}")
                .buildAndExpand(apiKey)
                .toUri();
    }
}
