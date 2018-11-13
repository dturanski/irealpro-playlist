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
