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

package org.dturanski.irealpro.playlist.service;

import org.dturanski.irealpro.playlist.domain.PlaylistEntity;
import org.dturanski.irealpro.playlist.repository.PlaylistRepository;

import org.springframework.util.Assert;

/**
 * @author David Turanski
 **/
public class PlaylistService {

	private final PlaylistRepository playlistRepository;

	public PlaylistService(PlaylistRepository playlistRepository) {
		Assert.notNull(playlistRepository, "'playlistRepository' cannot be null.");
		this.playlistRepository = playlistRepository;
	}

	public Long playlistCount() {
		return this.playlistRepository.count();
	}

	public PlaylistEntity create(PlaylistEntity playlistEntity) {
		Assert.notNull(playlistEntity, "'playlistEntity' cannot be null.");
		playlistEntity.setId(null);
		return playlistRepository.save(playlistEntity);
	}
}
