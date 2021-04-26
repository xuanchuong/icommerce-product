package com.icommerce.product.domain.entity;

import com.icommerce.product.domain.vo.MatchStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder(toBuilder = true)
@Getter
public class FootballMatch {

	private final Long id;
	private final String ownerId;
	private final List<String> pendingPlayer;
	private final LocalDateTime startDate;
	private final String location;
	private final String title;
	private final String description;
	private final int numberOfPlayers;
	@Builder.Default
	private final MatchStatus matchStatus = MatchStatus.READY;
	private final List<String> participants;
}
