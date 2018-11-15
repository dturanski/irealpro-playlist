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

package org.dturanski.irealpro.song;

import java.util.List;

import org.dturanski.irealpro.song.domain.SongEntity;
import org.dturanski.irealpro.song.service.SongService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author David Turanski
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class SongServiceTests {

	@Autowired
	private SongService songService;

	@Test
	public void basicSearch() {
		List<SongEntity> songs = songService.searchSongsByTitle("Love");

		songs.forEach(s -> System.out.println(s.getTitle()));

		assertThat(songs.size()).isEqualTo(3);

		songs = songService.searchSongsByTitle("The One I Love Belongs To Somebody Else");
		assertThat(songs.size()).isOne();
	}
}
