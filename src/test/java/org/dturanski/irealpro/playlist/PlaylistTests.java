package org.dturanski.irealpro.playlist;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.dturanski.irealpro.playlist.domain.Playlist;
import org.dturanski.irealpro.playlist.repository.PlaylistRepository;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author David Turanski
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class PlaylistTests {

	@Autowired
	PlaylistRepository playlistRepository;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Test
	public void test() {

		System.out.println(playlistRepository.findAll());





		Long maxSortingIndex = jdbcTemplate.queryForObject("SELECT MAX(ZSORTINGINDEX) as max from ZPLAYLIST",(rs,i) -> {
			return rs.getLong("max");
			});


		Playlist playlist = new Playlist();
		playlist.setName("MyNewPlaylist");
		playlist.setCreatedDate(0.0);
		playlist.setUniqueId(UUID.randomUUID().toString());
		playlist.setOpt(0L);
		playlist.setSortingIndex(maxSortingIndex + 1);


		//DON'T DO THIS. USE IMPORT instead. playlistRepository.save(playlist);



	}


}
