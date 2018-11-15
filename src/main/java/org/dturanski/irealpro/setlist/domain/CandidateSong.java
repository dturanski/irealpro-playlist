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

package org.dturanski.irealpro.setlist.domain;

import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.dturanski.irealpro.song.web.Key;
import org.dturanski.irealpro.song.web.SongDTO;

/**
 * @author David Turanski
 **/
@Data
public class CandidateSong {
	private String title;
	@JsonIgnore
	private Key key;
	@JsonIgnore
	private Optional<Key> transpose = Optional.empty();
	private Set<SongDTO> candidates;
	private String selectedUniqueId;

	@JsonProperty("key")
	public String getKeyNotation() {
		if (key != null) {
			return key.notation();
		}
		return "";
	}

	@JsonProperty("transpose")
	public String getTransposeNotation() {
		return transpose.isPresent() ? transpose.get().notation(): "";
	}

}
