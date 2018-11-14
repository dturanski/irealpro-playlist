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

package org.dturanski.irealpro.setlist.repository;

import org.dturanski.irealpro.setlist.domain.PrimaryKey;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author David Turanski
 **/
public class PrimaryKeyRepository {

	private final JdbcTemplate jdbcTemplate;

	public PrimaryKeyRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Query("SELECT * from Z_PRIMARYKEY where Z_NAME='Playlist'")
	public PrimaryKey playlistPrimaryKey() {
		return findPrimaryKeyByName("Playlist");

	}

	public PrimaryKey songPrimaryKey() {
		return findPrimaryKeyByName("Song");
	}

	public PrimaryKey findPrimaryKeyByName(String name) {
		return jdbcTemplate.queryForObject("SELECT * from Z_PRIMARYKEY where Z_NAME=?",primaryKeyRowMapper, name);
	}

	public PrimaryKey increment(PrimaryKey primaryKey) {
		jdbcTemplate.update("UPDATE Z_PRIMARYKEY SET Z_MAX=? where Z_ENT=?", primaryKey.getMax() + 1,
			primaryKey.getId());
		return findPrimaryKeyByName(primaryKey.getName());
	}

	private RowMapper<PrimaryKey> primaryKeyRowMapper = (rs, rowNum) -> {
		PrimaryKey primaryKey = new PrimaryKey();
		primaryKey.setId(rs.getLong("Z_ENT"));
		primaryKey.setName(rs.getString("Z_NAME"));
		primaryKey.setMax(rs.getLong("Z_MAX"));
		return primaryKey;
	};

}
