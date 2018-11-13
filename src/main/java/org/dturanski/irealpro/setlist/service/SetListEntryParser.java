package org.dturanski.irealpro.setlist.service;

import org.dturanski.irealpro.setlist.domain.CandidateSong;

/**
 * @author David Turanski
 **/
public interface SetListEntryParser {
	CandidateSong parse(String entry);
}
