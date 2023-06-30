package com.leagueoflegendsintlist.webapi.services;

import com.leagueoflegendsintlist.client.IntListMatchClient;
import com.leagueoflegendsintlist.client.contract.MatchDto;
import com.leagueoflegendsintlist.client.contract.PlayerInfoDto;
import com.leagueoflegendsintlist.webapi.contract.MatchHistoryDto;
import com.leagueoflegendsintlist.webapi.contract.PlayerDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IntListService {
    public static final String DDRAGON_VERSIONS_URL = "https://ddragon.leagueoflegends.com/api/versions.json";
    public static final String EMPTY_ITEM_SLOT_LINK = "https://i.pinimg.com/originals/ac/51/ef/ac51ef08735d2632d7e5b082fde78e83.png";
    public static final String DDRAGON_CDN_URL = "http://ddragon.leagueoflegends.com/cdn/";
    private final IntListMatchClient intListMatchClient;
    private final RestTemplate restTemplate;
    private String ddragonVersion;

    @Autowired
    public IntListService() {
        intListMatchClient = new IntListMatchClient();
        restTemplate = new RestTemplate();
        setLatestDdragonVersion();
    }

    public List<MatchHistoryDto> getMatches(String server, String summonerName) {
        List<MatchDto> matches = intListMatchClient.getMatchesBySummonerName(server, summonerName);
        return mapMatchDtoToMatchHistory(matches);
    }


    private List<MatchHistoryDto> mapMatchDtoToMatchHistory(List<MatchDto> matches) {
        return matches.stream()
                .map(matchDto -> mapPlayerDataDto(matchDto.info.participants))
                .map(MatchHistoryDto::new)
                .collect(Collectors.toList());
    }

    private List<PlayerDataDto> mapPlayerDataDto(List<PlayerInfoDto> playerInfoDtoList) {
        return playerInfoDtoList.stream()
                .map(this::mapPlayerInfoDtoToPlayerDataDto)
                .collect(Collectors.toList());
    }

    private PlayerDataDto mapPlayerInfoDtoToPlayerDataDto(PlayerInfoDto playerInfoDto) {
        PlayerDataDto playerDataDto = new PlayerDataDto();
        playerDataDto.assists = playerInfoDto.assists;
        playerDataDto.deaths = playerInfoDto.deaths;
        playerDataDto.kills = playerInfoDto.kills;
        playerDataDto.puuid = playerInfoDto.puuid;
        playerDataDto.summonerName = playerInfoDto.summonerName;
        playerDataDto.championIconLink = getChampionIconLink(playerInfoDto.championName);
        playerDataDto.item0IconLink = getItemIconLink(playerInfoDto.item0);
        playerDataDto.item1IconLink = getItemIconLink(playerInfoDto.item1);
        playerDataDto.item2IconLink = getItemIconLink(playerInfoDto.item2);
        playerDataDto.item3IconLink = getItemIconLink(playerInfoDto.item3);
        playerDataDto.item4IconLink = getItemIconLink(playerInfoDto.item4);
        playerDataDto.item5IconLink = getItemIconLink(playerInfoDto.item5);
        playerDataDto.item6IconLink = getItemIconLink(playerInfoDto.item6);
        return playerDataDto;
    }

    private String getChampionIconLink(String championName) {
        return DDRAGON_CDN_URL
                + ddragonVersion
                + "/img/champion/"
                + championName
                + ".png";
    }

    private String getItemIconLink(int itemId) {
        if (itemId == 0)
            return EMPTY_ITEM_SLOT_LINK;
        return DDRAGON_CDN_URL
                + ddragonVersion
                + "/img/item/"
                + itemId
                + ".png";
    }
    private void setLatestDdragonVersion() {
        ddragonVersion = getDdragonVersions().get(0);
    }

    private List<String> getDdragonVersions() {
        ResponseEntity<String[]> response = restTemplate.getForEntity(DDRAGON_VERSIONS_URL, String[].class);
        String[] ddragonVersions = response.getBody();
        return new ArrayList<>(List.of(ddragonVersions));
    }
}
