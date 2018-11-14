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

package org.dturanski.irealpro.playlist;

import java.util.List;

import org.dturanski.irealpro.DataAccessConfiguration;
import org.dturanski.irealpro.playlist.domain.PlaylistEntity;
import org.dturanski.irealpro.playlist.repository.PlaylistRepository;
import org.dturanski.irealpro.song.domain.SongEntity;
import org.dturanski.irealpro.song.service.SongService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author David Turanski
 **/
@SpringBootTest(properties = {"logging.level.org.springframework.data=INFO"})
@RunWith(SpringRunner.class)
public class PlaylistTests {

	@Autowired
	PlaylistRepository playlistRepository;

	@Autowired
	DataAccessConfiguration dataAccessConfiguration;

	@Autowired
	SongService songService;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Test
	@Transactional
	public void test() {

		PlaylistEntity playlist = new PlaylistEntity();
		playlist.setName("NewPlaylist3");
		final PlaylistEntity saved = playlistRepository.save(playlist);

		assertThat(saved.getId()).isNotNull();

		List<SongEntity> songs = songService.searchSongsByTitle("Love");
		for (int i = 0; i < songs.size(); i++) {
			SongEntity s = songs.get(i);
			songService.addSongToPlaylist(s.getUniqueId(), saved.getId(), i + 1, null);
		};

		assertThat(songService.findSongsByPlayList(saved.getId()).size()).isEqualTo(3);
	}
}
