package org.dturanski.irealpro.setlist.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import org.dturanski.irealpro.playlist.service.PlaylistService;
import org.dturanski.irealpro.setlist.domain.CandidateSong;
import org.dturanski.irealpro.setlist.domain.SetList;
import org.dturanski.irealpro.song.domain.SongEntity;
import org.dturanski.irealpro.song.service.SongService;
import org.dturanski.irealpro.song.web.Key;

import org.springframework.util.Assert;

/**
 * @author David Turanski
 **/
@Slf4j
public class SetListService {



	private final PlaylistService playlistService;

	private final SongService songService;

	private final SetListEntryParser parser;

	public SetListService(PlaylistService playlistService, SongService songService, SetListEntryParser parser) {
		Assert.notNull(playlistService, "'playlistService' cannot be null'");
		Assert.notNull(songService, "'songService' cannot be null'");
		this.playlistService = playlistService;
		this.songService = songService;
		this.parser = parser;
	}

	public SetList prepare(String contents) {
		String[] lines = contents.split("\\n");
		SetList setList = new SetList();
		setList.setCandidateSongs(Stream.of(lines)
			.map(parser::parse)
			.filter(s -> s != null)
			.map(this::match)
			.filter(s -> s.getUniqueIds() != null)
			.collect(Collectors.toList()));
		return setList;
	}


	private CandidateSong match(final CandidateSong candidateSong) {
		List<SongEntity> songs = songService.searchSongsByTitle(candidateSong.getTitle());
		if (songs.isEmpty()) {
			log.warn("'{}' not found.", candidateSong.getTitle());
			return candidateSong;
		}

		songs.stream().forEach(s -> log.info("'{}' matches '{}'", candidateSong.getTitle(), s.getTitle()));

		candidateSong.setUniqueIds(songs.stream().map(SongEntity::getUniqueId).collect(Collectors.toSet()));

		if (candidateSong.getUniqueIds().size() == 1) {
			SongEntity songEntity = songs.get(0);
			candidateSong.setSelectedUniqueId(songEntity.getUniqueId());
			if (candidateSong.getKey() == null) {
				candidateSong.setKey(Key.of(songEntity.getKeySignature()));
			}
			else if (candidateSong.getKey() != Key.of(songEntity.getKeySignature())) {

				Key transpose = transpose(Key.of(songEntity.getKeySignature()),
					candidateSong.getKey());

				if (transpose != Key.of(songEntity.getKeySignature())) {
					candidateSong.setTranspose(Optional.of(transpose));
					log.info("Transposing {} from {} to {}", candidateSong.getTitle(),
						Key.of(songEntity.getKeySignature()),
						transpose);
				}
			}
		}

		return candidateSong;
	}
	private Key transpose(Key from, Key to) {
		if (from.isMajor()) {
			return to.isMajor() ? to : to.relative();
		}
		else {
			return to.isMajor() ? to.relative() : to;
		}
	}
}
