package org.dturanski.irealpro.song.repository;

import org.dturanski.irealpro.song.domain.Song;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author David Turanski
 **/
public interface SongRepository extends CrudRepository<Song, Long> {

	@Query("SELECT * FROM ZSONG where ZPLAYLIST is NULL ORDER BY ZTITLE")
	Iterable<Song> findallSongs();

}
