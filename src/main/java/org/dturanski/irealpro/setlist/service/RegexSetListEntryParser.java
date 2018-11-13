package org.dturanski.irealpro.setlist.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;
import org.dturanski.irealpro.setlist.domain.CandidateSong;
import org.dturanski.irealpro.song.web.Key;

/**
 * @author David Turanski
 **/
@Slf4j
public class RegexSetListEntryParser implements SetListEntryParser {

	private static final Pattern pattern = Pattern.compile("(\\d+\\s-\\s)?([^\\(^\\)]+)(\\s+\\(((\\w+-?))\\)$)?");

	@Override
	public CandidateSong parse(String entry) {
		if (entry.trim().isEmpty()) {
			return null;
		}
		CandidateSong candidateSong = null;
		Matcher matcher = pattern.matcher(entry);
		if (matcher.matches()) {
			log.debug("{} matched!", entry);
			for (int i = 0; i < matcher.groupCount(); i++) {
				log.debug("{} {}", i, matcher.group(i));
			}
			candidateSong = new CandidateSong();
			candidateSong.setTitle(matcher.group(2));
			candidateSong.setKey(Key.of(matcher.group(4)));

		}
		else {
			log.error("unable to process '{}'. It is not formatted correctly.", entry);
		}
		return candidateSong;
	}



}
