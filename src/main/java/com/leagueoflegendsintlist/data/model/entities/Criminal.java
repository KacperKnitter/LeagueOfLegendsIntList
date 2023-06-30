package com.leagueoflegendsintlist.data.model.entities;


import com.leagueoflegendsintlist.client.contract.SummonerDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "criminals")
public class Criminal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long criminalId;
    @Column(name = "profile_icon_id")
    private String profileIconId;
    @Column(name="summoner_name")
    private String SummonerName;
    private String puuid;
    @Column(name = "summoner_level")
    private long summonerLevel;

    public Criminal(SummonerDto summonerDto) {
        this.profileIconId = "https://ddragon.leagueoflegends.com/cdn/12.23.1/img/profileicon/"+summonerDto.profileIconId+".png";
        SummonerName = summonerDto.summonerName;
        this.puuid = summonerDto.puuid;
        this.summonerLevel = summonerDto.summonerLevel;
    }

    @ManyToMany(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    @JoinTable(
            name = "int_list",
            joinColumns = @JoinColumn(name = "crimnal_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        this.users.add(user);
        user.getCriminals().add(this);
    }

    public void removeUser(User user) {
        this.users.remove(user);
        user.getCriminals().remove(this);
    }
}
