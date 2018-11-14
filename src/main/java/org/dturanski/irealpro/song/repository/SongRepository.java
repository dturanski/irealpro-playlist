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

package org.dturanski.irealpro.song.repository;

import org.dturanski.irealpro.song.domain.SongEntity;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author David Turanski
 **/
public interface SongRepository extends CrudRepository<SongEntity, Long> {

	@Query("SELECT * FROM ZSONG where ZPLAYLIST is NULL ORDER BY ZTITLE")
	Iterable<SongEntity> findallSongs();

	@Query("SELECT * FROM ZSONG where ZUNIQUEID=:uniqueId")
	SongEntity findByUniqueId(@Param("uniqueId") String uniqueId);

	@Query("SELECT * FROM ZSONG where ZTITLE=:title AND ZPLAYLIST=:playlist")
	SongEntity findSongByTitleAndPlaylist(@Param("title") String title, @Param("playlist") Long playlist);

	@Query("SELECT * FROM ZSONG where ZTITLE=:title AND ZPLAYLIST is NULL")
	SongEntity findSongByTitle(@Param("title") String title);

	@Query("SELECT * FROM ZSONG where ZPLAYLIST = :playlist")
	Iterable<SongEntity> findSongsByPlaylist(@Param("playlist") Long playlist);

}
