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

import org.dturanski.irealpro.setlist.domain.SetList;
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

	@Test
	@Transactional
	public void prepareNumberedListWithKeys() throws IOException {

		String contents = readResource("numbered-setlist.txt");
		SetList setList = setListService.prepare(contents);

		System.out.println("\nsetlist\n");
		setList.getCandidateSongs().stream().forEach(System.out::println);

		int lines = contents.split("\n").length;
		assertThat(setList.getCandidateSongs().size()).isEqualTo(lines);

		setList.getCandidateSongs().stream().forEach(s -> {
			assertThat(s.getTitle()).isIn("Heartaches", "Dear Old Stockholm", "I Fall In Love Too Easily",
				"One I Love (Belongs To Somebody Else), The");
			switch (s.getTitle()) {
			case "Heartaches":
				assertThat(s.getKey().notation()).isEqualTo("Bb");
				assertThat(s.getTranspose().isPresent()).isTrue();
				assertThat(s.getTranspose().get().notation()).isEqualTo("Bb");
				break;
			case "Dear Old Stockholm":
				assertThat(s.getKey().notation()).isEqualTo("D-");
				assertThat(s.getTranspose().isPresent()).isFalse();
				break;
			case "I Fall In Love Too Easily":
				assertThat(s.getKey().notation()).isEqualTo("Eb");
				assertThat(s.getTranspose().isPresent()).isFalse();
				break;

			case "One I Love (Belongs To Somebody Else), The":
				assertThat(s.getKey().notation()).isEqualTo("C");
				assertThat(s.getTranspose().isPresent()).isTrue();
				assertThat(s.getTranspose().get().notation()).isEqualTo("C");
				break;
			}
		});
	}

	@Test
	@Transactional
	public void prepareSimpleListWithKeys() throws IOException {

		String contents = readResource("setlist-with-keys.txt");
		SetList setList = setListService.prepare(contents);

		System.out.println("\nsetlist\n");
		setList.getCandidateSongs().stream().forEach(System.out::println);

		int lines = contents.split("\n").length;
		assertThat(setList.getCandidateSongs().size()).isEqualTo(lines);

		setList.getCandidateSongs().stream().forEach(s -> {
			assertThat(s.getTitle()).isIn(
				"Gonna Sit Right Down and Write Myself a Letter",
				"Blue Skies (Bb or G)",
				"Green Eyes",
				"Do You Know What It Means to Miss New Orleans?",
				"Taking a Chance on Love");
			switch (s.getTitle()) {
			case "Gonna Sit Right Down and Write Myself a Letter":
				assertThat(s.getKey().notation()).isEqualTo("C");
				assertThat(s.getTranspose().isPresent()).isTrue();
				assertThat(s.getTranspose().get().notation()).isEqualTo("C");
				break;
			case "Blue Skies (Bb or G)":
				assertThat(s.getKey()).isNull();
				assertThat(s.getTranspose().isPresent()).isFalse();
				assertThat(s.getSelectedUniqueId()).isNull();
				assertThat(s.getUniqueIds().size()).isEqualTo(2);
				break;
			case "Green Eyes":
				assertThat(s.getKey().notation()).isEqualTo("Eb");
				assertThat(s.getTranspose().isPresent()).isFalse();
				break;

			case "Do You Know What It Means to Miss New Orleans?":
				assertThat(s.getKey().notation()).isEqualTo("F");
				assertThat(s.getTranspose().isPresent()).isTrue();
				assertThat(s.getTranspose().get().notation()).isEqualTo("F");
				break;
			case "Taking a Chance on Love":
				assertThat(s.getKey().notation()).isEqualTo("C");
				assertThat(s.getTranspose().isPresent()).isTrue();
				assertThat(s.getTranspose().get().notation()).isEqualTo("C");
				break;
			}
		});
	}

	@Test
	@Transactional
	public void prepareSimpleSetListWithNoKeys() throws IOException {

		String contents = readResource("setlist-nokeys.txt");
		SetList setList = setListService.prepare(contents);
		System.out.println("\nsetlist\n");
		setList.getCandidateSongs().stream().forEach(System.out::println);
		setList.getCandidateSongs().stream().forEach(s -> {
			assertThat(s.getTitle()).isIn(
				"Gonna Sit Right Down and Write Myself a Letter",
				"Blue Skies",
				"Green Eyes",
				"Do You Know What It Means to Miss New Orleans?",
				"Taking a Chance on Love");
			switch (s.getTitle()) {
			case "Gonna Sit Right Down and Write Myself a Letter":
				assertThat(s.getKey().notation()).isEqualTo("G");
				assertThat(s.getTranspose().isPresent()).isFalse();
				break;
			case "Blue Skies":
				assertThat(s.getKey().notation()).isEqualTo("C");
				assertThat(s.getTranspose().isPresent()).isFalse();
				break;
			case "Green Eyes":
				assertThat(s.getKey().notation()).isEqualTo("Eb");
				assertThat(s.getTranspose().isPresent()).isFalse();
				break;

			case "Do You Know What It Means to Miss New Orleans?":
				assertThat(s.getKey().notation()).isEqualTo("C");
				assertThat(s.getTranspose().isPresent()).isFalse();
				break;
			case "Taking a Chance on Love":
				assertThat(s.getKey().notation()).isEqualTo("F");
				assertThat(s.getTranspose().isPresent()).isFalse();
				break;
			}
		});
	}

		private String readResource (String resourcePath) throws IOException {
			return convertStreamToString(new ClassPathResource(resourcePath).getInputStream());

		}

		private static String convertStreamToString (java.io.InputStream is){
			java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
			return s.hasNext() ? s.next() : "";
		}

	}
