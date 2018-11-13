package org.dturanski.irealpro.song.web;

import java.util.List;

import org.dturanski.irealpro.song.service.SongService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static java.util.stream.Collectors.toList;

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
	public List<SongDTO> searchByTitle(@RequestParam String title) {
		return this.songService.searchSongsByTitle(title).stream().map(SongDTO::fromEntity).collect(toList());
	}

}
