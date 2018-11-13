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

import org.dturanski.irealpro.setlist.domain.CandidateSong;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author David Turanski
 **/
public class SetListEntryParserTests {

	@Test
	public void regexParser() {
		SetListEntryParser parser = new RegexSetListEntryParser();

		verify(parser.parse("3 - I Fall In Love Too Easily (Eb)"), "I Fall In Love Too Easily", "Eb");
		verify(parser.parse(" 3- I Fall In Love Too Easily (Eb)"), "I Fall In Love Too Easily", "Eb");
		verify(parser.parse("3. I Fall In Love Too Easily (Eb)"), "I Fall In Love Too Easily", "Eb");
		verify(parser.parse("I Fall In Love Too Easily"), "I Fall In Love Too Easily", null);

		verify(parser.parse("4 - One I Love (Belongs To Somebody Else), The (G)"),
			"One I Love (Belongs To Somebody Else), The", "G");
	}

	private void verify(CandidateSong song, String expectedTitle, String expectedKey) {
		assertThat(song).isNotNull();
		assertThat(song.getTitle()).isEqualTo(expectedTitle);
		if (expectedKey == null) {
			assertThat(song.getKey()).isNull();
		}
		else {
			assertThat(song.getKey().notation()).isEqualTo(expectedKey);
		}
	}

}
