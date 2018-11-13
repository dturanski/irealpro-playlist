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
