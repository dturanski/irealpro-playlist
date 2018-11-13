package org.dturanski.irealpro.playlist.repository;

import org.dturanski.irealpro.playlist.domain.PlaylistEntity;

import org.springframework.data.repository.CrudRepository;

/**
 * @author David Turanski
 **/
public interface PlaylistRepository extends CrudRepository<PlaylistEntity, Long> {

}
