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

package org.dturanski.irealpro.playlist.domain;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author David Turanski
 **/
@Table("ZPLAYLIST")
@Data
public class PlaylistEntity {

	@Id
	@Column("Z_PK")
	private Long id;

	@Column("Z_ENT")
	private Long entityId;

	@Column("Z_OPT")
	private Long opt;

	@Column("ZSORTINGINDEX")
	private Long sortingIndex;

	@Column("ZCREATEDDATE")
	private Double createdDate;

	@Column("ZNAME")
	private String name;

	@Column("ZUNIQUEID")
	private String uniqueId;
}
