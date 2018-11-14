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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.dturanski.irealpro.playlist.domain.PlaylistEntity;
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

	@Autowired
	private JdbcTemplate jdbcTemplate;

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

				Map<String, Long> result = jdbcTemplate.queryForObject(
					"SELECT Z_ENT, Z_MAX from Z_PRIMARYKEY WHERE Z_NAME='Playlist'",
					(rs, i) -> {
						Map<String, Long> values = new HashMap<>();
						values.put("Z_ENT", rs.getLong("Z_ENT"));
						values.put("Z_MAX", rs.getLong("Z_MAX"));
						return values;
					});

				playlist.setEntityId(result.get("Z_ENT"));
				playlist.setId(result.get("Z_MAX") + 1);

				Long count = jdbcTemplate.queryForObject(
					"SELECT COUNT(*) from ZPLAYLIST",
					(rs, i) -> rs.getLong(1));

				playlist.setSortingIndex(count + 1);
				playlist.setCreatedDate(563468947.832476);
				playlist.setOpt(1L);
			}

			else if (event.getEntity() instanceof SongEntity) {
				SongEntity song = (SongEntity) event.getEntity();

				song.setUniqueId(DigestUtils.sha1Hex(song.toString()));

				Long result = jdbcTemplate.queryForObject(
					"SELECT Z_ENT, Z_MAX from Z_PRIMARYKEY WHERE Z_NAME='Song'",
					(rs, i) -> {
						return rs.getLong("Z_MAX");
					});
				song.setId(result + 1);
				song.setCreatedDate(563468947.832476);
			}
		};
	}

	@Bean
	public ApplicationListener<AfterSaveEvent> afterSave() {
		return event -> {
			Object entity = event.getEntity();
			if (entity instanceof PlaylistEntity) {
				Long maxPk = jdbcTemplate.queryForObject(
					"SELECT Z_MAX from Z_PRIMARYKEY WHERE Z_NAME='Playlist'",
					(rs, i) -> rs.getLong("Z_MAX"));
				PlaylistEntity playlist = (PlaylistEntity) entity;
				if (playlist.getId() > maxPk) {
					jdbcTemplate.execute(
						"UPDATE Z_PRIMARYKEY SET Z_MAX =" + playlist.getId() + " WHERE Z_NAME='Playlist'");
				}
			} else if (entity instanceof SongEntity) {
				Long maxPk = jdbcTemplate.queryForObject(
					"SELECT Z_MAX from Z_PRIMARYKEY WHERE Z_NAME='Song'",
					(rs, i) -> rs.getLong("Z_MAX"));
				SongEntity songEntity = (SongEntity) entity;
				if (songEntity.getId() > maxPk) {
					jdbcTemplate.execute(
						"UPDATE Z_PRIMARYKEY SET Z_MAX =" + songEntity.getId() + " WHERE Z_NAME='Song'");
				}
			}
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
