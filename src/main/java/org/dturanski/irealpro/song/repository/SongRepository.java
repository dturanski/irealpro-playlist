package org.dturanski.irealpro.song.repository;

import org.dturanski.irealpro.song.domain.SongEntity;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author David Turanski
 **/
public interface SongRepository extends CrudRepository<SongEntity, Long> {

	@Query("SELECT * FROM ZSONG where ZPLAYLIST is NULL ORDER BY ZTITLE")
	Iterable<SongEntity> findallSongs();

	@Query("SELECT * FROM ZSONG where ZUNIQUEID=:uniqueId")
	SongEntity findByUniqueId(@Param("uniqueId") String uniqueId);

	@Query("SELECT * FROM ZSONG where ZTITLE=:title AND ZPLAYLIST=:playlist")
	SongEntity findSongByTitleAndPlaylist(@Param("title") String title, @Param("playlist") Long playlist);

	@Query("SELECT * FROM ZSONG where ZTITLE=:title AND ZPLAYLIST is NULL")
	SongEntity findByTitle(@Param("title") String title);

}
