/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.lazertag.users.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import org.springframework.core.style.ToStringCreator;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String team;

    @NotEmpty
    @Digits(fraction = 0, integer = 10)
    private String kills;

    @NotEmpty
    @Digits(fraction = 0, integer = 10)
    private String deaths;

    @NotEmpty
    @Digits(fraction = 0, integer = 10)
    private String gamesWon;
    
    @NotEmpty
    @Digits(fraction = 0, integer = 10)
    private String gamesPlayed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isNew() {
        return this.id == null;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return this.team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getKills() {
        return this.kills;
    }

    public void setKills(String kills) {
        this.kills = kills;
    }
    
    public String getDeaths() {
        return this.deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }
    
    public String getGamesWon() {
        return this.gamesWon;
    }

    public void setGamesWon(String gamesWon) {
        this.gamesWon = gamesWon;
    }
    
    public String getGamesPlayed() {
        return this.gamesPlayed;
    }

    public void setGamesPlayed(String gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }


    @Override
    public String toString() {
        return new ToStringCreator(this)

                .append("id", this.getId()).append("new", this.isNew())
                .append("name", this.getName()).append("team", this.getTeam())
                .append("kills", this.getKills()).append("deaths", this.getDeaths())
                .append("wins", this.getGamesWon()).append("gamesPlayed", this.getGamesPlayed()).toString();
    }
}
