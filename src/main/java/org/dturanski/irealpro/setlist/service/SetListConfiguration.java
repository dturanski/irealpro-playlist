package org.dturanski.irealpro.setlist.service;

import org.dturanski.irealpro.playlist.service.PlaylistService;
import org.dturanski.irealpro.song.service.SongService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author David Turanski
 **/
@Configuration
public class SetListConfiguration {

	@Bean
	public SetListService setListService(PlaylistService playlistService, SongService songService) {
		return new SetListService(playlistService, songService, new RegexSetListEntryParser());
	}


}
