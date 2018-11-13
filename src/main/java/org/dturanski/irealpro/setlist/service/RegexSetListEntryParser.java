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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;
import org.dturanski.irealpro.setlist.domain.CandidateSong;
import org.dturanski.irealpro.song.web.Key;

import org.springframework.util.StringUtils;

/**
 * @author David Turanski
 **/
@Slf4j
public class RegexSetListEntryParser implements SetListEntryParser {

	private static final Pattern containsKeyPattern = Pattern.compile("\\A(.+)\\s*(\\(((\\w+))\\)\\Z)");

	private static final Pattern patternWithKey = Pattern.compile("\\A(\\d+\\s?[-\\.]\\s)?(.+)\\s*(\\(((\\w+))\\)\\Z)");

	private static final Pattern patternWithNoKey = Pattern.compile("\\A(\\d+\\s?[-\\.]\\s)?(.+)\\Z");

	@Override
	public CandidateSong parse(String entry) {

		if (!StringUtils.hasText(entry)) {
			return null;
		}

		entry = entry.trim();

		Matcher containsKeyMatcher = containsKeyPattern.matcher(entry);
		boolean containsKey = containsKeyMatcher.matches();

		Matcher matcher;
		CandidateSong candidateSong = containsKey ? extract(patternWithKey.matcher(entry), 2, 4) :
			extract(patternWithNoKey.matcher(entry), 0, null);
		if (candidateSong == null) {
			log.error("Unable to process '{}'. It is not formatted correctly.", entry);
		}

		return candidateSong;
	}

	private CandidateSong extract(Matcher matcher, Integer titleGroup, Integer keyGroup) {
		CandidateSong candidateSong = null;
		if (matcher.matches()) {
			for (int i = 0; i < matcher.groupCount(); i++) {
				log.trace("{} {}", i, matcher.group(i));
			}
			candidateSong = new CandidateSong();
			candidateSong.setTitle(matcher.group(titleGroup).trim());
			if (keyGroup != null) {
				candidateSong.setKey(Key.of(matcher.group(keyGroup)));
			}
		}
		return candidateSong;
	}

}
