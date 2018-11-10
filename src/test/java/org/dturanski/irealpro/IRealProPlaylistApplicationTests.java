package org.dturanski.irealpro;

import java.io.File;

import org.dturanski.irealpro.song.domain.Song;
import org.dturanski.irealpro.song.repository.SongRepository;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IRealProPlaylistApplicationTests {

	@Autowired
	SongRepository songsRepository;

	@Value("${irealpro.db.file}")
	String dbFile;

	@Test
	public void contextLoads() {
		assertThat(new File(dbFile).exists()).isTrue();
		Iterable<Song> songList = songsRepository.findAll();
		songList.forEach(System.out::println);
	}

}
