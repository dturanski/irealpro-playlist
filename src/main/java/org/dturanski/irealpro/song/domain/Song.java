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
public class Song {
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
}
