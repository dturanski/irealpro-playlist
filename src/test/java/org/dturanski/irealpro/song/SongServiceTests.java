package org.dturanski.irealpro.song;

import org.dturanski.irealpro.song.service.SongService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author David Turanski
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class SongServiceTests {

	@Autowired
	private SongService songService;

	@Test
	public void basicSearch() {
		songService.searchSongsByTitle("you").forEach(s-> System.out.println(s.getTitle()));
		songService.searchSongsByTitle("I Can't Believe That You're In Love with Me").forEach(s-> System.out.println(s.getTitle()));
		songService.searchSongsByTitle("The One I Love Belongs To Somebody Else").forEach(s-> System.out.println(s.getTitle()));

	}
}
