package org.dturanski.irealpro.song;

import org.dturanski.irealpro.song.web.Key;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author David Turanski
 **/
public class KeyTests {

	@Test
	public void allOfTheseShouldBeValid() {
		assertThat(Key.of("A")).isEqualTo(Key.A_MAJOR);
		assertThat(Key.of("A").isMajor()).isTrue();

		assertThat(Key.of("Ab")).isEqualTo(Key.A_FLAT_MAJOR);
		assertThat(Key.of("AbM")).isEqualTo(Key.A_FLAT_MAJOR);
		assertThat(Key.of("AbMaj")).isEqualTo(Key.A_FLAT_MAJOR);
		assertThat(Key.of("Ab-")).isEqualTo(Key.A_FLAT_MINOR);
		assertThat(Key.of("Abm")).isEqualTo(Key.A_FLAT_MINOR);
		assertThat(Key.of("AbMin")).isEqualTo(Key.A_FLAT_MINOR);
	}


	@Test
	public void relativeMinors() {
		assertThat(Key.of("A").relative()).isEqualTo(Key.F_SHARP_MINOR);
		assertThat(Key.of("Bb").relative()).isEqualTo(Key.G_MINOR);
		assertThat(Key.of("B").relative()).isEqualTo(Key.A_FLAT_MINOR);
		assertThat(Key.of("C").relative()).isEqualTo(Key.A_MINOR);
		assertThat(Key.of("Db").relative()).isEqualTo(Key.B_FLAT_MINOR);
		assertThat(Key.of("D").relative()).isEqualTo(Key.B_MINOR);
		assertThat(Key.of("Eb").relative()).isEqualTo(Key.C_MINOR);
		assertThat(Key.of("F").relative()).isEqualTo(Key.D_MINOR);
		assertThat(Key.of("F#").relative()).isEqualTo(Key.E_FLAT_MINOR);
		assertThat(Key.of("G").relative()).isEqualTo(Key.E_MINOR);
		assertThat(Key.of("Ab").relative()).isEqualTo(Key.F_MINOR);
	}

	@Test
	public void relativeMajors() {
		assertThat(Key.of("A-").relative()).isEqualTo(Key.C_MAJOR);
		assertThat(Key.of("Bb-").relative()).isEqualTo(Key.D_FLAT_MAJOR);
		assertThat(Key.of("B-").relative()).isEqualTo(Key.D_MAJOR);
		assertThat(Key.of("C-").relative()).isEqualTo(Key.E_FLAT_MAJOR);
		assertThat(Key.of("Db-").relative()).isEqualTo(Key.E_MAJOR);
		assertThat(Key.of("D-").relative()).isEqualTo(Key.F_MAJOR);
		assertThat(Key.of("Eb-").relative()).isEqualTo(Key.G_FLAT_MAJOR);
		assertThat(Key.of("F-").relative()).isEqualTo(Key.A_FLAT_MAJOR);
		assertThat(Key.of("F#-").relative()).isEqualTo(Key.A_MAJOR);
		assertThat(Key.of("G-").relative()).isEqualTo(Key.B_FLAT_MAJOR);
		assertThat(Key.of("Ab-").relative()).isEqualTo(Key.B_MAJOR);
	};


}
