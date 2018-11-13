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

package org.dturanski.irealpro.song.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import lombok.extern.slf4j.Slf4j;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.dturanski.irealpro.song.domain.SongEntity;
import org.dturanski.irealpro.song.repository.SongRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author David Turanski
 **/
@Slf4j
public class SongService {

	private final SongRepository repository;

	public SongService(SongRepository repository) {

		this.repository = repository;
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
		if (repository.findSongByTitleAndPlaylist(songEntity.getTitle(), playlistId) != null) {
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
