/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dturanski.irealpro.setlist.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import org.dturanski.irealpro.playlist.domain.PlaylistEntity;
import org.dturanski.irealpro.playlist.service.PlaylistService;
import org.dturanski.irealpro.setlist.domain.CandidateSong;
import org.dturanski.irealpro.setlist.domain.SetList;
import org.dturanski.irealpro.song.domain.SongEntity;
import org.dturanski.irealpro.song.service.SongService;
import org.dturanski.irealpro.song.web.Key;
import org.dturanski.irealpro.song.web.SongDTO;

import org.springframework.transaction.annotation.Transactional;
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

	/**
	 * @param contents
	 * @return
	 */
	public List<CandidateSong> prepare(String contents) {
		String[] lines = contents.split("\\n");

		return Stream.of(lines)
			.map(parser::parse)
			.filter(s -> s != null)
			.map(this::match)
			.collect(Collectors.toList());
	}

	/**
	 * @param setList
	 * @return
	 */
	@Transactional
	public Long create(SetList setList) {
		PlaylistEntity playlist = new PlaylistEntity();
		playlist.setName(setList.getName());
		final PlaylistEntity saved = playlistService.create(playlist);

		final AtomicInteger  i = new AtomicInteger();
		setList.getEntries().forEach(e -> {
			songService.addSongToPlaylist(e.getUniqueId(), saved.getId(), i.incrementAndGet(), e.getTransposeTo());
		});

		return saved.getId();
	}

	private CandidateSong match(final CandidateSong candidateSong) {
		List<SongEntity> songs = songService.searchSongsByTitle(candidateSong.getTitle());
		if (songs.isEmpty()) {
			log.warn("'{}' not found.", candidateSong.getTitle());
			return candidateSong;
		}

		songs.stream().forEach(s -> log.info("'{}' matches '{}'", candidateSong.getTitle(), s.getTitle()));

		candidateSong.setCandidates(songs.stream().map(SongDTO::fromEntity)
			.map(c-> {
				if (candidateSong.getKey() != null) {
					Key key = transpose(Key.of(c.getKey()), candidateSong.getKey());
					if (candidateSong.getKey() != Key.of(c.getKey())) {
						log.info("Transposing {} from {} to {}", c.getTitle(), candidateSong.getKey(), key);
						c.setTranspose(key.notation());
					}
				}
				return c;
			})
			.collect(Collectors.toSet()));


		if (candidateSong.getCandidates().size() == 1) {
			SongEntity songEntity = songs.get(0);
			candidateSong.setSelectedUniqueId(songEntity.getUniqueId());
			if (candidateSong.getKey() == null) {
				candidateSong.setKey(Key.of(songEntity.getKeySignature()));
			}
		}

		return candidateSong;
	}
	private Key transpose(Key from, Key to) {
		if (from == to) {
			return from;
		}
		if (from.isMajor()) {
			return to.isMajor() ? to : to.relative();
		}
		else {
			return to.isMajor() ? to.relative() : to;
		}
	}
}
