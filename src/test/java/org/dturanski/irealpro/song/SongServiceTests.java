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
import java.util.function.BiPredicate;

import org.apache.commons.text.similarity.LevenshteinDetailedDistance;
import org.apache.commons.text.similarity.LevenshteinResults;
import org.dturanski.irealpro.song.domain.SongEntity;
import org.dturanski.irealpro.song.repository.SongRepository;
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

	@Autowired
	private SongRepository songRepository;

	@Test
	public void basicSearch() {
		List<SongEntity> songs = songService.searchSongsByTitle("Love");

		songs.forEach(s -> System.out.println(s.getTitle()));

		assertThat(songs.size()).isEqualTo(3);

		songs = songService.searchSongsByTitle("The One I Love Belongs To Somebody Else");
		assertThat(songs.size()).isOne();
	}

	@Test
	public void sandbox() {

		Iterable<SongEntity> songs = songRepository.findallOriginalSongs();

		matchTitle(songs, "Love");

		matchTitle(songs, "The One I Love Belongs To Somebody Else");

		matchTitle(songs, "Three O'Clock In the Morning");

		matchTitle(songs, "Heartaches");

		matchTitle(songs, "Blue Skies");

		matchTitle(songs, "Green Eyes");

		matchTitle(songs, "Green");

		matchTitle(songs, "Do You Know What It Means To Miss New Orleans");

		matchTitle(songs, "I'm Gonna Sit Right Down And Taking A Chance");
	}

	private void matchTitle(Iterable<SongEntity> songs, String title) {
		songs.forEach(s -> {
			if (match.test(s.getTitle(), title)) {
				System.out.println("\n ******** possible Match!!!!\n");
			}
		});
	}

	BiPredicate<String, String> match = (s1, s2) -> {
		int deltaLen = Math.abs(s1.length() - s2.length());
		int maxLen = Math.max(s1.length(), s2.length());
		LevenshteinResults results = LevenshteinDetailedDistance.getDefaultInstance().apply(s1, s2);
		int dist = results.getDistance();
		int subs = results.getSubstituteCount();
		double ratio = dist * 1.0 / maxLen;
		System.out.println(
			String.format("%s->%s delta_len: %d  dist: %d subs: %d ratio %f", s2, s1, deltaLen, dist, subs,
				ratio));
		return (subs == 0 || dist <= deltaLen || ratio < .5);
	};
}
