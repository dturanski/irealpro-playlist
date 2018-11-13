/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
