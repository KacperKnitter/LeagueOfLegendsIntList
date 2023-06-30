package com.leagueoflegendsintlist.client;

import com.leagueoflegendsintlist.client.contract.MatchDto;
import com.leagueoflegendsintlist.client.dictionaries.RegionDictionary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class IntListMatchClient {
    private final IntListSummonerClient intLIstSummonerClient;
    private final RestTemplate restClient;
    private final String apiKey;

    public IntListMatchClient() {
        intLIstSummonerClient = new IntListSummonerClient();
        restClient = new RestTemplate();
        apiKey = ClientSettings.getApiKey();
    }

    public List<MatchDto> getMatchesBySummonerName(String server, String summonerName) {
        String puuid = intLIstSummonerClient.getPuuidBySummonerName(server, summonerName);
        return getMatchesByPuuid(server, puuid);
    }

    public List<MatchDto> getMatchesByPuuid(String server, String puuid) {
        List<String> matchesIds = getMatchesIds(server, puuid);
        return getMatchesByMatchesIds(server, matchesIds);
    }

    private List<String> getMatchesIds(String server, String puuid) {
        var url = uriBuilderForMatchesId(server, puuid);
        ResponseEntity<String[]> response = restClient.getForEntity(url, String[].class);
        String[] matchesIds = response.getBody();
        return new ArrayList<>(List.of(matchesIds));
    }

    private URI uriBuilderForMatchesId(String server, String puuid) {
        return UriComponentsBuilder
                .fromHttpUrl(RegionDictionary.getRegion(server))
                .path("by-puuid/")
                .path(puuid)
                .path("/ids")
                .query("start=0")
                .query("count=20")
                .query("api_key={apiKey}")
                .buildAndExpand(apiKey)
                .toUri();
    }

    private List<MatchDto> getMatchesByMatchesIds(String server, List<String> matchesIds) {
        return matchesIds.stream()
                .map(matchId-> getMatchByMatchId(server,matchId))
                .collect(Collectors.toList());
    }

    private MatchDto getMatchByMatchId(String server, String matchId) {
        var url = uriBuilderForMatchSearch(server, matchId);
        return restClient.getForEntity(url, MatchDto.class).getBody();
    }

    private URI uriBuilderForMatchSearch(String server, String matchId) {
        return UriComponentsBuilder
                .fromHttpUrl(RegionDictionary.getRegion(server))
                .path(matchId)
                .query("api_key={apiKey}")
                .buildAndExpand(apiKey)
                .toUri();
    }
}
