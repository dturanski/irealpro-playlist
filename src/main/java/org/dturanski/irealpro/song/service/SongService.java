package org.dturanski.irealpro.song.service;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.dturanski.irealpro.song.domain.Song;
import org.dturanski.irealpro.song.repository.SongRepository;

/**
 * @author David Turanski
 **/
public class SongService {

	private final SongRepository repository;

	public SongService(SongRepository repository) {
		this.repository = repository;
	}

	public Iterable<Song> searchSongsByTitle(String title) {
		return StreamSupport.stream(repository.findallSongs().spliterator(), false)
			.filter(song -> FuzzySearch.tokenSetRatio(song.getTitle(), title) > 90)
			.collect(Collectors.toList());
	}
}
