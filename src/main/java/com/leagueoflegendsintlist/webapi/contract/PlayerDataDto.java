package com.leagueoflegendsintlist.webapi.contract;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDataDto {

    public String championIconLink;
    public String item0IconLink;
    public String item1IconLink;
    public String item2IconLink;
    public String item3IconLink;
    public String item4IconLink;
    public String item5IconLink;
    public String item6IconLink;
    public String puuid;
    public String summonerName;
    public int kills;
    public int deaths;
    public int assists;

}
