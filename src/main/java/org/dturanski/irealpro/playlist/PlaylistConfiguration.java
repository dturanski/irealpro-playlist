package org.dturanski.irealpro.playlist;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.dturanski.irealpro.playlist.domain.PlaylistEntity;
import org.dturanski.irealpro.playlist.repository.PlaylistRepository;
import org.dturanski.irealpro.playlist.service.PlaylistService;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.relational.core.mapping.event.AfterSaveEvent;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author David Turanski
 **/

@Configuration
public class PlaylistConfiguration {

	@Bean
	public PlaylistService playlistService(PlaylistRepository playlistRepository, JdbcTemplate jdbcTemplate) {
		return new PlaylistService(playlistRepository, jdbcTemplate);
	}

	@Bean
	public ApplicationListener<BeforeSaveEvent> beforeSavePlaylist(JdbcTemplate jdbcTemplate) {

		return event -> {

			Object entity = event.getEntity();
			if (entity instanceof PlaylistEntity) {
				PlaylistEntity playlist = (PlaylistEntity) entity;
				if (playlist.getId() != null) {
					return;
				}
				playlist.setUniqueId(DigestUtils.sha1Hex(playlist.toString()));

				Map<String, Long> result = jdbcTemplate.queryForObject(
					"SELECT Z_ENT, Z_MAX from Z_PRIMARYKEY WHERE Z_NAME='Playlist'",
					(rs,i) -> {
						Map<String,Long> values  = new HashMap<>();
						values.put("Z_ENT", rs.getLong("Z_ENT"));
						values.put("Z_MAX", rs.getLong("Z_MAX"));
						return  values;
					} );

				playlist.setEntityId(result.get("Z_ENT"));

				playlist.setId(result.get("Z_MAX") + 1);

				Long count = jdbcTemplate.queryForObject(
					"SELECT COUNT(*) from ZPLAYLIST",
					(rs,i) -> rs.getLong(1));

				playlist.setSortingIndex(count + 1);
				playlist.setCreatedDate(563468947.832476);
				playlist.setOpt(1L);
			}
		};
	}

	@Bean
	public ApplicationListener<AfterSaveEvent> afterSavePlaylist(JdbcTemplate jdbcTemplate) {
		return event -> {
			Object entity = event.getEntity();
			if (entity instanceof PlaylistEntity) {
				Long maxPk = jdbcTemplate.queryForObject(
					"SELECT Z_MAX from Z_PRIMARYKEY WHERE Z_NAME='Playlist'",
					(rs,i) -> rs.getLong("Z_MAX"));
				PlaylistEntity playlist = (PlaylistEntity) entity;
				if (playlist.getId() > maxPk) {
					jdbcTemplate.execute(
						"UPDATE Z_PRIMARYKEY SET Z_MAX =" + playlist.getId() + " WHERE Z_NAME='Playlist'");
				}
			}
		};
	}
}
