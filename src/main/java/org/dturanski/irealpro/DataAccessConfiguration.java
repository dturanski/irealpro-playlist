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

package org.dturanski.irealpro;

import org.apache.commons.codec.digest.DigestUtils;
import org.dturanski.irealpro.playlist.domain.PlaylistEntity;
import org.dturanski.irealpro.playlist.service.PlaylistService;
import org.dturanski.irealpro.setlist.domain.PrimaryKey;
import org.dturanski.irealpro.setlist.repository.PrimaryKeyRepository;
import org.dturanski.irealpro.song.domain.SongEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.relational.core.mapping.event.AfterSaveEvent;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author David Turanski
 **/
@Configuration
public class DataAccessConfiguration {

	//TODO: Figure out the TIMESTAMP conversion in sqlite 3.
	public static final double DUMMY_CREATED_DATE = 563468947.832476;

	public static final long DONT_KNOW_WHAT_THIS_IS = 1L;

	@Autowired
	private PlaylistService playlistService;

	@Autowired
	private PrimaryKeyRepository primaryKeyRepository;

	@Bean
	public PrimaryKeyRepository primaryKeyRepository(JdbcTemplate jdbcTemplate) {
		return new PrimaryKeyRepository(jdbcTemplate);
	}

	@Bean
	public ApplicationListener<BeforeSaveEvent> beforeSave() {

		return event -> {
			Long id = getId(event.getEntity());
			if (id != null) {
				return;
			}

			if (event.getEntity() instanceof PlaylistEntity) {
				PlaylistEntity playlist = (PlaylistEntity) event.getEntity();

				playlist.setUniqueId(DigestUtils.sha1Hex(playlist.toString()));

				PrimaryKey playlistPrimaryKey = primaryKeyRepository.playlistPrimaryKey();

				playlist.setEntityId(playlistPrimaryKey.getId());
				playlist.setId(playlistPrimaryKey.getMax() + 1);

				Long count = playlistService.playlistCount();

				playlist.setSortingIndex(count + 1);
				playlist.setCreatedDate(DUMMY_CREATED_DATE);
				playlist.setOpt(DONT_KNOW_WHAT_THIS_IS);
			}

			else if (event.getEntity() instanceof SongEntity) {
				SongEntity song = (SongEntity) event.getEntity();

				song.setUniqueId(DigestUtils.sha1Hex(song.toString()));

				PrimaryKey songPrimaryKey = primaryKeyRepository.songPrimaryKey();

				song.setId(songPrimaryKey.getMax() + 1);
				song.setCreatedDate(DUMMY_CREATED_DATE);
			}
		};
	}

	@Bean
	public ApplicationListener<AfterSaveEvent> afterSave() {
		return event -> {
			Object entity = event.getEntity();
			PrimaryKey primaryKey = null;
			if (entity instanceof PlaylistEntity) {
				primaryKey = primaryKeyRepository.playlistPrimaryKey();

			}
			else if (entity instanceof SongEntity) {
				primaryKey = primaryKeyRepository.songPrimaryKey();
			}

			primaryKeyRepository.increment(primaryKey);
		};
	}

	private Long getId(Object entity) {
		if (entity instanceof PlaylistEntity) {
			return ((PlaylistEntity) entity).getId();
		}
		else if (entity instanceof SongEntity) {
			return ((SongEntity) entity).getId();
		}
		return null;
	}
}
