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

package org.dturanski.irealpro.setlist.web;

import java.util.List;

import org.dturanski.irealpro.setlist.domain.CandidateSong;
import org.dturanski.irealpro.setlist.domain.SetList;
import org.dturanski.irealpro.setlist.service.SetListService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author David Turanski
 **/
@RestController
public class SetListController {

	private final SetListService setListService;
	public SetListController(SetListService setListService) {
		this.setListService = setListService;
	}

	@PostMapping(path = "/setlist/prepare", consumes = "text/plain", produces = "application/json")
	public List<CandidateSong> prepareSetList(@RequestBody String content) {
		return this.setListService.prepare(content);
	}

	@PostMapping(path="setlist/create", consumes = "application/json")
	public void createPlaylist(@RequestBody SetList setList){
		setListService.create(setList);
	}
}
