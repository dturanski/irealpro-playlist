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

import lombok.Data;
import org.dturanski.irealpro.song.domain.SongEntity;

/**
 * @author David Turanski
 **/
@Data
public class SongDTO {

	private String title;

	private String uniqueId;

	public static SongDTO fromEntity(SongEntity songEntity) {
		SongDTO song = new SongDTO();
		song.setTitle(songEntity.getTitle());
		song.setUniqueId(songEntity.getUniqueId());
		return song;
	}

	public SongDTO() {

	}

}