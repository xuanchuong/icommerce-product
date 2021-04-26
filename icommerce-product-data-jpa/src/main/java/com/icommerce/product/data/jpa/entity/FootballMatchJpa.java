package com.icommerce.product.data.jpa.entity;

import com.icommerce.product.domain.vo.MatchStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "football-match")
public class FootballMatchJpa implements Serializable {
    @Id
    private Long id;

    @NotNull
    @Field("owner_id")
    private String owner;

    @Field(name = "start_date")
    private LocalDateTime startDate;

    @Field(name = "location")
    private String location;

    @Field
    private String title;

    @Field
    private String description;

    @Field(name = "number_of_player")
    private int numberOfPlayers;

    @Field(name = "match_status")
    private MatchStatus matchStatus;

    @Field(name = "match_players")
    private List<String> participants;

    @Field(name = "match_joining_request")
    private List<String> pendingPlayer;
}

