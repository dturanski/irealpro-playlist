package org.dturanski.irealpro.playlist;

import java.util.List;

import org.dturanski.irealpro.playlist.domain.PlaylistEntity;
import org.dturanski.irealpro.playlist.repository.PlaylistRepository;
import org.dturanski.irealpro.song.domain.SongEntity;
import org.dturanski.irealpro.song.service.SongService;
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
	SongService songService;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Test
	public void test() {

		PlaylistEntity playlist = new PlaylistEntity();
		playlist.setName("NewPlaylist3");
		final PlaylistEntity saved = playlistRepository.save(playlist);

		List<SongEntity> songs = songService.searchSongsByTitle("Never");
		for (int i = 0; i < songs.size(); i++) {
			SongEntity s = songs.get(i);
			songService.addSongToPlaylist(s.getUniqueId(), saved.getId(), i + 1, null);
		};
	}
}
