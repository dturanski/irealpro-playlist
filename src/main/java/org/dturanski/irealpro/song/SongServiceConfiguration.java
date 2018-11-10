package org.dturanski.irealpro.song;

import org.dturanski.irealpro.song.repository.SongRepository;
import org.dturanski.irealpro.song.service.SongService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author David Turanski
 **/
@Configuration
public class SongServiceConfiguration {

	@Bean
	public SongService songService(SongRepository repository) {
		return new SongService(repository);
	}

}
