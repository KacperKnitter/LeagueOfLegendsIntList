package com.leagueoflegendsintlist.webapi.contract;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MatchHistoryDto {
    public List<PlayerDataDto> playerDataDtos;
}

