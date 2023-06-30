package com.leagueoflegendsintlist.client.dictionaries;

import java.util.Map;

public class ServerDictionary {
    private static final Map<String, String> serversDictionary = Map.of(
            "EUNE", "https://eun1.api.riotgames.com/lol/summoner/v4/summoners/",
            "EUW", "https://euw1.api.riotgames.com/lol/summoner/v4/summoners/",
            "NA", "https://na1.api.riotgames.com/lol/summoner/v4/summoners/"
    );

    public static String getServer(String serverName) {
        return serversDictionary.get(serverName);
    }
}
