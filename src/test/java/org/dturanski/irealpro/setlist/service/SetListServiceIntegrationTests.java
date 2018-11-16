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

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.dturanski.irealpro.setlist.domain.CandidateSong;
import org.dturanski.irealpro.setlist.domain.SetList;
import org.dturanski.irealpro.song.domain.SongEntity;
import org.dturanski.irealpro.song.service.SongService;
import org.dturanski.irealpro.song.web.SongDTO;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author David Turanski
 **/
@SpringBootTest(properties = { "logging.level.org.dturanski.irealpro=INFO" })

@RunWith(SpringRunner.class)
public class SetListServiceIntegrationTests {

	@Autowired
	private SetListService setListService;

	@Autowired
	private SongService songService;

	@Test
	public void prepareNumberedListWithKeys() throws IOException {

		String contents = readResource("numbered-setlist.txt");
		List<CandidateSong> candidateSongs = setListService.prepare(contents);

		System.out.println("\nsetlist\n");
		candidateSongs.stream().forEach(System.out::println);

		int lines = contents.split("\n").length;
		assertThat(candidateSongs.size()).isEqualTo(lines);

		candidateSongs.stream().forEach(s -> {
			assertThat(s.getTitle()).isIn("Heartaches", "Dear Old Stockholm", "I Fall In Love Too Easily",
				"One I Love (Belongs To Somebody Else), The");
			SongDTO songDTO = s.getCandidates().iterator().next();
			switch (s.getTitle()) {
			case "Heartaches":
				assertThat(s.getKey().notation()).isEqualTo("Bb");

				assertThat(songDTO.getTranspose()).isEqualTo("Bb");
				assertThat(songDTO.getKey()).isEqualTo("G");
				break;
			case "Dear Old Stockholm":
				assertThat(s.getKey().notation()).isEqualTo("D-");
				break;
			case "I Fall In Love Too Easily":
				assertThat(s.getKey().notation()).isEqualTo("Eb");
				break;

			case "One I Love (Belongs To Somebody Else), The":
				assertThat(s.getKey().notation()).isEqualTo("C");
				songDTO = s.getCandidates().iterator().next();
				assertThat(songDTO.getTranspose()).isEqualTo("C");
				assertThat(songDTO.getKey()).isEqualTo("G");
				break;
			}
		});
	}

	@Test
	public void prepareSimpleListWithKeys() throws IOException {

		String contents = readResource("setlist-with-keys.txt");
		List<CandidateSong> candidateSongs = setListService.prepare(contents);

		System.out.println("\nsetlist\n");
		candidateSongs.stream().forEach(System.out::println);

		int lines = contents.split("\n").length;
		assertThat(candidateSongs.size()).isEqualTo(lines);

		candidateSongs.stream().forEach(s -> {
			assertThat(s.getTitle()).isIn(
				"Gonna Sit Right Down and Write Myself a Letter",
				"Blue Skie",
				"Green Eyes",
				"Do You Know What It Means to Miss New Orleans?",
				"Taking a Chance on Love");
			SongDTO songDTO = s.getCandidates().iterator().next();
			switch (s.getTitle()) {
			case "Gonna Sit Right Down and Write Myself a Letter":
				assertThat(s.getKey().notation()).isEqualTo("C");
				assertThat(songDTO.getTranspose()).isEqualTo("C");
				assertThat(songDTO.getKey()).isEqualTo("G");
				break;
			case "Blue Skie":
				assertThat(s.getKey().notation()).isEqualTo("Bb");
				assertThat(s.getSelectedUniqueId()).isNull();
				assertThat(s.getCandidates().size()).isEqualTo(3);
				/**
				 * Transpose must be Major -> Major or Minor -> Minor
				 */
				s.getCandidates().forEach(c-> {
					if (c.getTitle().equals("Blue Skies 1")) {
						assertThat(c.getTranspose()).isEqualTo("G-");
					} else {
						assertThat(c.getTranspose()).isEqualTo("Bb");
					}
				});
				break;
			case "Green Eyes":
				assertThat(s.getKey().notation()).isEqualTo("Eb");
				break;

			case "Do You Know What It Means to Miss New Orleans?":
				assertThat(s.getKey().notation()).isEqualTo("F");
				assertThat(songDTO.getTranspose()).isEqualTo("F");
				assertThat(songDTO.getKey()).isEqualTo("C");
				break;
			case "Taking a Chance on Love":
				assertThat(s.getKey().notation()).isEqualTo("C");
				assertThat(songDTO.getTranspose()).isEqualTo("C");
				assertThat(songDTO.getKey()).isEqualTo("F");
				break;
			}
		});
	}

	@Test
	public void prepareSimpleSetListWithNoKeys() throws IOException {

		String contents = readResource("setlist-nokeys.txt");
		List<CandidateSong> candidateSongs = setListService.prepare(contents);
		System.out.println("\nsetlist\n");
		candidateSongs.stream().forEach(System.out::println);
		candidateSongs.stream().forEach(s -> {
			assertThat(s.getTitle()).isIn(
				"Gonna Sit Right Down and Write Myself a Letter",
				"Blue Skies",
				"Green Eyes",
				"Do You Know What It Means to Miss New Orleans?",
				"Taking a Chance on Love");
			switch (s.getTitle()) {
			case "Gonna Sit Right Down and Write Myself a Letter":
				assertThat(s.getKey().notation()).isEqualTo("G");
				break;
			case "Blue Skies":
				assertThat(s.getKey().notation()).isEqualTo("C");
				break;
			case "Green Eyes":
				assertThat(s.getKey().notation()).isEqualTo("Eb");
				break;

			case "Do You Know What It Means to Miss New Orleans?":
				assertThat(s.getKey().notation()).isEqualTo("C");
				break;
			case "Taking a Chance on Love":
				assertThat(s.getKey().notation()).isEqualTo("F");
				break;
			}
		});
	}

	@Transactional
	@Test
	public void createPlaylist() {
		List<SongEntity> songs = songService.searchSongsByTitle("Love");

		final SetList setList = new SetList();
		setList.setEntries(songs.stream().map(s -> {
			SetList.SetListEntry setListEntry = new SetList.SetListEntry(s.getUniqueId(), null);
			return setListEntry;
		}).collect(Collectors.toList()));

		Long playlistId = setListService.create(setList);
		assertThat(songService.findSongsByPlayList(playlistId)).size().isEqualTo(3);
	}

	private String readResource(String resourcePath) throws IOException {
		return convertStreamToString(new ClassPathResource(resourcePath).getInputStream());

	}

	private static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

}
