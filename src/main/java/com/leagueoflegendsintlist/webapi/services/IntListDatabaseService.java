package com.leagueoflegendsintlist.webapi.services;

import com.leagueoflegendsintlist.client.IntListSummonerClient;
import com.leagueoflegendsintlist.client.contract.SummonerDto;
import com.leagueoflegendsintlist.data.model.entities.Criminal;
import com.leagueoflegendsintlist.data.model.entities.User;
import com.leagueoflegendsintlist.data.repositories.CriminalsRepository;
import com.leagueoflegendsintlist.data.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IntListDatabaseService {
    private final CriminalsRepository criminalsRepository;
    private final IntListSummonerClient intListSummonerClient;
    private final UsersRepository usersRepository;

    @Autowired
    public IntListDatabaseService(CriminalsRepository criminalsRepository, IntListSummonerClient intListSummonerClient, UsersRepository usersRepository) {
        this.criminalsRepository = criminalsRepository;
        this.intListSummonerClient = intListSummonerClient;
        this.usersRepository = usersRepository;
    }

    public void addCriminal(String server, String puuid, long userId) {

        SummonerDto summoner = intListSummonerClient.getSummonerByPuuid(server, puuid);
        Criminal criminal = new Criminal(summoner);
        User user = usersRepository.findById(userId).get();
        criminal.addUser(user);
        criminalsRepository.save(criminal);

    }

    public List<Criminal> getCriminals(Long userId) {
        return usersRepository.findById(userId).get().getCriminals();
    }

    public void deleteCriminal(long criminalId, long userId) {
        User user = usersRepository.findById(userId).get();
        Criminal criminal = criminalsRepository.findCriminalByCriminalId(criminalId).get();
        criminal.removeUser(user);
    }
}
