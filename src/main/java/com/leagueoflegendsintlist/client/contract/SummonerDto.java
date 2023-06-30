package com.leagueoflegendsintlist.client.contract;

import com.fasterxml.jackson.annotation.JsonProperty;



public class SummonerDto {
    public int profileIconId;
    @JsonProperty("name")
    public String summonerName;
    public String puuid;
    public long summonerLevel;

}
