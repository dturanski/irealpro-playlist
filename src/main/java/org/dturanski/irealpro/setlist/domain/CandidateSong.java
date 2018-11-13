package org.dturanski.irealpro.setlist.domain;

import java.util.Set;

import lombok.Data;
import org.dturanski.irealpro.song.web.Key;

/**
 * @author David Turanski
 **/
@Data
public class CandidateSong {
	private String title;
	private Key key;
	private Set<String> uniqueIds;

}
