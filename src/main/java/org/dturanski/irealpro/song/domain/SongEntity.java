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

package org.dturanski.irealpro.song.domain;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author David Turanski
 **/
@Data
@Table("ZSONG")
public class SongEntity {
	@Id
	@Column("Z_PK")
	Long id;

	@Column("ZTITLE")
	String title;

	@Column("ZPLAYLIST")
	Long playlistId;

	@Column("ZCHORDPROGRESSION")
	String chordProgression;

	@Column("ZTRANSPOSITION")
	String transpose;

	@Column("Z_ENT")
	private Long entityId;

	@Column("Z_OPT")
	private Long opt;

	@Column("ZSORTINGINDEX")
	private Long sortingIndex;

	@Column("ZPLAYLIST")
	private Long playlist;

	@Column("ZCREATEDDATE")
	private Double createdDate;

	@Column("ZUNIQUEID")
	private String uniqueId;

	@Column("ZISTRASHED")
	private Integer isTrashed = 0;

	@Column("ZPLAYERCHORUSES")
	private Integer playerChoruses;

	@Column("ZPLAYERTEMPO")
	private Integer playerTempo;

	@Column("ZMXBASSVOL")
	private Double bassVol;

	@Column("ZMXDRUMSVOL")
	private Double drumsVol;

	@Column("ZMXHARM1VOL")
	private Double harm1Vol;

	@Column("ZMXHARM2VOL")
	private Double harm2Vol;

	@Column("ZCOMPOSER")
	String composer;

	@Column("ZKEYSIGNATURE")
	String keySignature;

	@Column("ZMXBASSINST")
	String bassInst;

	@Column("ZMXDRUMSINST")
	String drumsInst;

	@Column("ZMXHARM1INST")
	String harm1Inst;

	@Column("ZMXHARM2INST")
	String harm2Inst;

	@Column("ZPLAYERSTYLE")
	String playerStyle;

	@Column("ZSTYLE")
	String style;
}
