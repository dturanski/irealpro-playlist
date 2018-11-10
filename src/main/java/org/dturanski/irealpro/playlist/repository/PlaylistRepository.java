package org.dturanski.irealpro.playlist.repository;

import org.dturanski.irealpro.playlist.domain.Playlist;

import org.springframework.data.repository.CrudRepository;

/**
 * @author David Turanski
 **/
public interface PlaylistRepository extends CrudRepository<Playlist, Long> {

}
