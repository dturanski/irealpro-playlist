package org.dturanski.irealpro.song;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.dturanski.irealpro.song.domain.SongEntity;
import org.dturanski.irealpro.song.repository.SongRepository;
import org.dturanski.irealpro.song.service.SongService;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author David Turanski
 **/
@Configuration
public class SongConfiguration {

	@Bean
	public SongService songService(SongRepository repository, JdbcTemplate jdbcTemplate) {
		return new SongService(repository, jdbcTemplate);
	}

	@Bean
	public ApplicationListener<BeforeSaveEvent> beforeSaveSong(JdbcTemplate jdbcTemplate) {

		return event -> {

			Object entity = event.getEntity();
			if (entity instanceof SongEntity) {
				SongEntity song = (SongEntity) entity;
				if ( song.getId() != null) {
					return;
				}
				song.setUniqueId(DigestUtils.sha1Hex(song.toString()));

				Map<String, Long> result = jdbcTemplate.queryForObject(
					"SELECT Z_ENT, Z_MAX from Z_PRIMARYKEY WHERE Z_NAME='Song'",
					(rs,i) -> {
						Map<String,Long> values  = new HashMap<>();
						values.put("Z_ENT", rs.getLong("Z_ENT"));
						values.put("Z_MAX", rs.getLong("Z_MAX"));
						return  values;
					} );
			}
		};
	}

}
