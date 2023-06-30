package com.leagueoflegendsintlist.client.dictionaries;

import java.util.Map;

public class RegionDictionary {
    private static final Map<String, String> serversDictionary = Map.of(
            "EUNE", "https://europe.api.riotgames.com/lol/match/v5/matches/",
            "EUW", "https://europe.api.riotgames.com/lol/match/v5/matches/",
            "NA", "https://americas.api.riotgames.com/lol/match/v5/matches/"
    );

    public static String getRegion(String serverName) {
        return serversDictionary.get(serverName);
    }
}