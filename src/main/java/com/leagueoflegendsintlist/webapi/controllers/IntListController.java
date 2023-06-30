package com.leagueoflegendsintlist.webapi.controllers;


import com.leagueoflegendsintlist.data.model.entities.Criminal;
import com.leagueoflegendsintlist.webapi.contract.CrimnalReportDto;
import com.leagueoflegendsintlist.webapi.contract.MatchHistoryDto;
import com.leagueoflegendsintlist.webapi.services.IntListDatabaseService;
import com.leagueoflegendsintlist.webapi.services.IntListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RequiredArgsConstructor
@RestController("/leagueOfLegendsIntList")
public class IntListController {

    private final IntListService intListService;
    private final IntListDatabaseService intListDatabaseService;
    @PutMapping("/addCriminal")
    public ResponseEntity<String> addCriminal(@RequestBody CrimnalReportDto crimnalReportDto, @RequestBody long userId) {
       intListDatabaseService.addCriminal(crimnalReportDto.server, crimnalReportDto.puuid,userId);
       return ResponseEntity.ok("Criminal added successfully");
    }
    @DeleteMapping("/deleteCriminal")
    public ResponseEntity<String> deleteCriminal(@RequestBody long criminalId, @RequestBody long userId){
        intListDatabaseService.deleteCriminal(criminalId,userId);
        return ResponseEntity.ok("criminal deleted");
    }

    @GetMapping("/search/{server}/{summonerName}")
    public ResponseEntity<List<MatchHistoryDto>> getSearchSite(@PathVariable String server, @PathVariable String summonerName){
        List<MatchHistoryDto> matches =  intListService.getMatches(server,summonerName);
        return ResponseEntity.ok(matches);
    }
    @PostMapping("/criminals")
    public ResponseEntity<List<Criminal>> getCriminalList(@RequestBody long userId){
        List<Criminal> criminalsList = intListDatabaseService.getCriminals(userId);
        return ResponseEntity.ok(criminalsList);
    }
}