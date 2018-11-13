package org.dturanski.irealpro.playlist.service;

import org.dturanski.irealpro.playlist.repository.PlaylistRepository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

/**
 * @author David Turanski
 **/
public class PlaylistService {

	private final PlaylistRepository playlistRepository;

	private final JdbcTemplate jdbcTemplate;

	public PlaylistService (PlaylistRepository playlistRepository, JdbcTemplate jdbcTemplate) {
		Assert.notNull(playlistRepository, "'playlistRepository' cannot be null.");
		Assert.notNull(jdbcTemplate, "'jdbcTemplate' cannot be null.");
		this.playlistRepository = playlistRepository;
		this.jdbcTemplate = jdbcTemplate;
	}

}
