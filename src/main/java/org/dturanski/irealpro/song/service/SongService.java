package org.dturanski.irealpro.song.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.apache.commons.codec.digest.DigestUtils;
import org.dturanski.irealpro.song.domain.SongEntity;
import org.dturanski.irealpro.song.repository.SongRepository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author David Turanski
 **/
@Slf4j
public class SongService {

	private final SongRepository repository;

	private final JdbcTemplate jdbcTemplate;

	public SongService(SongRepository repository, JdbcTemplate jdbcTemplate) {

		this.repository = repository;
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<SongEntity> searchSongsByTitle(String title) {
		Assert.hasText(title, "'title' must contain text");
		SongEntity unique = repository.findByTitle(title);
		if (unique != null) {
			return Collections.singletonList(unique);
		}

		List<SongEntity> songEntityList = StreamSupport.stream(repository.findallSongs().spliterator(), false)
			.filter(song -> FuzzySearch.tokenSetRatio(song.getTitle(), title) > 90)
			.collect(Collectors.toList());

		Optional<SongEntity> songEntity =
			songEntityList.stream().filter(song -> title.equalsIgnoreCase(song.getTitle())).findFirst();
		if (songEntity.isPresent()) {
			return Collections.singletonList(songEntity.get());
		}
		return songEntityList;
	}

	@Transactional
	public void addSongToPlaylist(String uniqueid, long playlistId, long sortingIndex, String transpose) {
		Assert.hasText(uniqueid, "'uniqueId' must contain text.");
		SongEntity songEntity = repository.findByUniqueId(uniqueid);
		if (songEntity == null) {
			throw new RuntimeException("Cannot find SongEntity with unique ID = " + uniqueid);
		}
		if (repository.findSongByTitleAndPlaylist(songEntity.getTitle(),playlistId) != null) {
			log.warn(songEntity.getTitle() + " already exists in playlist");
			return;
		}

		songEntity.setId(null);
		songEntity.setPlaylist(playlistId);
		songEntity.setSortingIndex(sortingIndex);
		if (StringUtils.hasText(transpose)) {
			songEntity.setTranspose(transpose);
		}
		repository.save(songEntity);
	}
}
