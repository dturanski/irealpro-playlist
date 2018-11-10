package org.dturanski.irealpro.song.web;

import java.util.List;

import org.dturanski.irealpro.song.domain.Song;
import org.dturanski.irealpro.song.service.SongService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author David Turanski
 **/
@RestController
public class SongController {

	private final SongService songService;
	public SongController(SongService songService) {
		this.songService = songService;
	}

	@GetMapping("/songs")
	public List<Song> searchByTitle(@RequestParam String title) {
		return (List<Song>) this.songService.searchSongsByTitle(title);

	}

}
