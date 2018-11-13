package org.dturanski.irealpro.setlist.domain;

import java.util.List;

import lombok.Data;

/**
 * @author David Turanski
 **/
@Data
public class SetList {

	String name;


	List<CandidateSong> candidateSongs;
}
